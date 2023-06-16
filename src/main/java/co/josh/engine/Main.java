package co.josh.engine;

import co.josh.engine.render.Camera;
import co.josh.engine.render.RenderDispatcher;
import co.josh.engine.util.annotations.hooks.Startup;
import co.josh.engine.util.exceptions.WindowCreateFailure;
import co.josh.engine.util.input.KeyboardHandler;
import co.josh.engine.util.annotations.hooks.Exit;
import co.josh.engine.util.annotations.hooks.Gameloop;
import co.josh.engine.util.input.MouseHandler;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryStack;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    public static int width;
    public static int height;

    public static Camera camera;

    public static int currentWidth;
    public static int currentHeight;

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static long window;

    public static KeyboardHandler keyboard;
    public static MouseHandler mouse;

    public static String dir = System.getProperty("user.dir");

    public static ArrayList<Integer> shaderPrograms = new ArrayList<>();

    public int fps;
    public int fpsCount;
    public static int tps = 20;
    public static int tpsCount;
    public float clockSubtract = 0;

    public static RenderDispatcher renderSystem;

    public void run() {
        try{
        init();
        } catch (WindowCreateFailure e){
            e.printStackTrace();
            return;
        }
        loop();

        // Clean up shaders
        GL33.glUseProgram(0); // unbind
        for (int x : shaderPrograms){ // delete all
            if (x != 0) {
                GL33.glDeleteProgram(x);
            }
        }

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

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
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will not stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE); //could not be me
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE); // attempt at fix
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE); // debug

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
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback();

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        camera = new Camera(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f));

        //System.out.println(glGetString(GL_EXTENSIONS));

        renderSystem = new RenderDispatcher();
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

        long tickLastUpdateTime = System.nanoTime();
        long frameLastUpdateTime = System.nanoTime();


        try{
            runAllAnnotatedWith(Startup.class);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        Set<Method> gameloopRunnables = getAllAnnotatedWith(Gameloop.class);
        while (!glfwWindowShouldClose(window) ) {
            if (glfwGetTime()-clockSubtract > 1f) {
                fps = fpsCount;
                fpsCount = 0;

                tps = tpsCount;
                tpsCount = 0;

                clockSubtract += 1f;
                System.out.println("FPS:" + fps + " TPS: " + tps);
            }

            long now = System.nanoTime();
            long tickElapsedTime = now - tickLastUpdateTime;
            long frameElapsedTime = now - frameLastUpdateTime;

            if (tickElapsedTime >= 33333333) { //30 TPS ish because it looks smoother with 60FPS
                //THIS IS WHERE EVERYTHING IMPORTANT HAPPENS
                keyboard.update();
                mouse.update();

                for (GameObject gameObject : gameObjects){
                    gameObject.setLastPosition(gameObject.getPosition());
                    gameObject.getComponents().forEach(Component::tickValues);
                }
                try{
                    run(gameloopRunnables, null);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                tpsCount++;
                tickLastUpdateTime = now;
            }

            if (frameElapsedTime  >= 16666666){ //60 fps, this is (1/60)*10^9 because nanoseconds
                renderSystem.render(window);
                fpsCount++; //Rendered a frame so yeah
                frameLastUpdateTime = now;
            }
        }
    }
}
