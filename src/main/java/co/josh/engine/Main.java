package co.josh.engine;

import co.josh.engine.objects.GameObject;
import co.josh.engine.render.Camera;
import co.josh.engine.render.RenderDispatcher;
import co.josh.engine.render.joshshade.JoshShaderLoader;
import co.josh.engine.util.annotations.hooks.*;
import co.josh.engine.util.exceptions.WindowCreateFailure;
import co.josh.engine.util.input.KeyboardHandler;
import co.josh.engine.util.input.MouseHandler;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import co.josh.engine.components.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    public static int width;
    public static int height;

    public static float targetFps = 60f;

    public static float frameWait = (int)((1f/targetFps)*1000);
    public static float tickDeltaTime = 0f;
    public static float deltaTime = 0f;


    public static void recalcFrameWait(){
        frameWait = (int)((1f/targetFps)*1000);
    }

    public static float targetTps = 30f;

    public static float tickWait = (int)((1f/targetTps)*1000);

    public static float actualTickWait = tickWait;

    public static void recalcTickWait(){
        tickWait = (int)((1f/targetTps)*1000);
    }

    public static Camera camera;

    public static int currentWidth;
    public static int currentHeight;

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static long window;

    public static KeyboardHandler keyboard;
    public static MouseHandler mouse;

    public static String dir = System.getProperty("user.dir");

    public int fps;
    public int fpsCount;
    public static int tps = 20;
    public static int tpsCount;
    public float clockSubtract = 0;

    public static long tickElapsedTime = 0;
    public static long frameElapsedTime = 0;

    public static RenderDispatcher renderSystem;

    public void run() {
        try{
        init();
        } catch (WindowCreateFailure e){
            e.printStackTrace();
            return;
        }
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();

        try{
            runAllAnnotatedWith(Exit.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Set<Method> getAllAnnotatedWith(Class<? extends Annotation> annotation){
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(annotation);
    }

    public static void runAllAnnotatedWith(Class<? extends Annotation> annotation)
            throws Exception {
        run(getAllAnnotatedWith(annotation), null);
    }
    public static void run(Set<Method> methods, Object[] parameters) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methods) {
            // for simplicity, invokes methods as static without parameters
            m.invoke(m.getDeclaringClass(), parameters);
        }
    }

    private void init() throws WindowCreateFailure {
        System.out.println("Starting JoshEngine with LWJGL " + Version.getVersion());
        try {
            if (Files.exists(Path.of(dir + "/josh/"))){
                System.out.println("Engine dir exists and was found.");
            }else{
                Files.createDirectory(Path.of(dir + "/josh/"));
                System.out.println("Created engine dir succesfully.");
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find engine directory or create it! Textures will not load!");
        }
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VERSION_MAJOR, 1);
        glfwWindowHint(GLFW_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will not stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE); //could not be me
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);

        width = 854;
        height = 480;
        currentWidth = width;
        currentHeight = height;

        // Create the window
        window = glfwCreateWindow(width, height, "JoshEngine", NULL, NULL);

        if ( window == NULL )
            throw new WindowCreateFailure();


        //keybord
        keyboard = new KeyboardHandler(window);
        //moose
        mouse = new MouseHandler(window);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        renderSystem = new RenderDispatcher();

        camera = new Camera(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f));

        JoshShaderLoader.init();

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        System.out.println("Starting game loop");
        glfwSetTime(0);
        // don delete shit here
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0f, 0f, 0f, 0.0f);

        GLFW.glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int _width, int _height) {
                int width = _width/2;
                int height = _height/2;

                if ((float)width/height>(float)Main.width/Main.height){
                    GLFW.glfwSetWindowSize(window, width, (int)(width*((float)Main.height/Main.width)));
                    currentWidth = width;
                    currentHeight = (int)(width*((float)Main.height/Main.width));
                } else if ((float)width/height<(float)Main.width/Main.height) {
                    GLFW.glfwSetWindowSize(window, (int)(height*((float)Main.width/Main.height)), height);
                    currentWidth = (int)(height*((float)Main.width/Main.height));
                    currentHeight = height;
                }
            }
        });

        long tickLastUpdateTime = System.currentTimeMillis();
        long frameLastUpdateTime = System.currentTimeMillis();


        try{
            runAllAnnotatedWith(Startup.class);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        Set<Method> preRender = getAllAnnotatedWith(PreRender.class);
        Set<Method> postRender = getAllAnnotatedWith(PostRender.class);

        Set<Method> preTick = getAllAnnotatedWith(PreTick.class);
        Set<Method> postTick = getAllAnnotatedWith(PostTick.class);
        while (!glfwWindowShouldClose(window) ) {
            if (glfwGetTime()-clockSubtract > 1f) {
                fps = fpsCount;
                fpsCount = 0;

                tps = tpsCount;
                tpsCount = 0;

                clockSubtract += 1f;
                System.out.println("FPS:" + fps + " TPS: " + tps);
            }

            long now = System.currentTimeMillis();
            tickElapsedTime = now - tickLastUpdateTime;
            frameElapsedTime = now - frameLastUpdateTime;

            if (frameElapsedTime  >= frameWait){
                deltaTime = frameElapsedTime/1000f;
                try{
                    run(preRender, null);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                renderSystem.render(window);
                try{
                    run(postRender, null);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                fpsCount++; //Rendered a frame so yeah
                frameLastUpdateTime = now;
            }

            if (tickElapsedTime >= tickWait) {
                tickDeltaTime = (Main.tickElapsedTime/1000f);
                keyboard.update();
                mouse.update();

                try{
                    run(preTick, null);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                for (GameObject gameObject : gameObjects){
                    gameObject.setLastTransform(gameObject.getTransform());
                    gameObject.getComponents().forEach(Component::onTick);
                }
                try{
                    run(postTick, null);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                tpsCount++;
                tickLastUpdateTime = now;
            }
        }
    }
}
