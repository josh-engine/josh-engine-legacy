package co.josh.engine;

import co.josh.engine.objects.o2d.GameObject;
import co.josh.engine.render.Camera;
import co.josh.engine.render.RenderDispatcher;
import co.josh.engine.util.KeyboardHandler;
import co.josh.engine.util.annotations.hooks.exit;
import co.josh.engine.util.annotations.hooks.gameloop;
import co.josh.engine.util.annotations.hooks.startup;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
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
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    public static int width;
    public static int height;

    public static Camera camera;

    public static int curX;
    public static int curY;

    public static float relativeCurX;
    public static float relativeCurY;

    public static int currentWidth;
    public static int currentHeight;

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static long window;

    public static KeyboardHandler keyboard;

    public static String dir = System.getProperty("user.dir");

    public void run() {
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

        try{
            runAllAnnotatedWith(exit.class);
        } catch (Exception e){
            e.printStackTrace();
            return;
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
        run(getAllAnnotatedWith(annotation));
    }
    public static void run(Set<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methods) {
            // for simplicity, invokes methods as static without parameters
            m.invoke(null);
        }
    }

    private void init() {
        System.out.println("Starting WugEngine with LWJGL " + Version.getVersion());
        try {
            if (Files.exists(Path.of(dir + "/wug"))){
                System.out.println("Engine dir exists and was found.");
            }else{
                Files.createDirectory(Path.of(dir + "/wug"));
                System.out.println("Created engine dir succesfully.");
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find engine directory or create it! Textures and outside scripts will not load!");
        }
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will not stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE); //could not be me

        width = 854;
        height = 480;
        currentWidth = width;
        currentHeight = height;

        // Create the window
        window = glfwCreateWindow(width, height, "WugEngine", NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");


        //keybord
        keyboard = new KeyboardHandler(window);

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
        // Enable v-sync
        glfwSwapInterval(1);

        renderSystem = new RenderDispatcher();

        camera = new Camera(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f));

        // Make the window visible
        glfwShowWindow(window);
    }

    public float timeClock;
    public int fps;
    public int fpsCount;
    public static int tps;
    public static int tpsCount;
    public float clockSubtract = 0;

    public static RenderDispatcher renderSystem;

    public double[] getCursorPos(){
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xBuffer, yBuffer);
        return new double[]{ xBuffer.get(0), yBuffer.get(0)};
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
            public void invoke(long window, int width, int height) {
                width = width/2;
                height = height/2;
                if ((float)width/height>(float)Main.width/Main.height){
                    GLFW.glfwSetWindowSize(window, width, (int)(width*((float)Main.height/Main.width)));
                } else if ((float)width/height<(float)Main.width/Main.height) {
                    GLFW.glfwSetWindowSize(window, (int)(height*((float)Main.width/Main.height)), height);
                }
            }
        });

        long lastUpdateTime = System.nanoTime();

        try{
            runAllAnnotatedWith(startup.class);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        Set<Method> gameloopRunnables = getAllAnnotatedWith(gameloop.class);
        double[] cur;
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
            long elapsedTime = now - lastUpdateTime;

            if (elapsedTime >= 40000000) { //20TPS ish
                //THIS IS WHERE EVERYTHING IMPORTANT HAPPENS
                keyboard.update();
                cur = getCursorPos();
                curX = (int) cur[0];
                curY = (int) cur[1];
                relativeCurX = (currentWidth/(float)width)*curX;
                relativeCurY = (currentHeight/(float)height)*curY;

                for (GameObject gameObject : gameObjects){
                    gameObject.getPosition().set(gameObject.getNextPosition());
                    gameObject.getComponents().forEach(Component::tickValues);
                }
                try{
                    run(gameloopRunnables);
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                tpsCount++;
                lastUpdateTime = now;
            }

            renderSystem.render(window);
            fpsCount++; //Rendered a frame so yeah
        }
    }
}
