package main;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    // usando OpenGL 3.3.6
    private static long window;

    private static final String vertexShaderSourceCode = """
            #version 330
            layout (location = 0) in vec3 posicaoDosTriangulos;
            void main(){
                gl_Position = vec4(posicaoDosTriangulos.x, posicaoDosTriangulos.y, posicaoDosTriangulos.z, 1.0);
            }
            """;

    private static final String fragmentSourceColorShader = """
            #version 330
            out vec4 CorzonaManeira;
            void main(){
                CorzonaManeira = vec4(0.1, 1.0, 1.0, 1.0);
            }
            """;

    public static void main(String[] args) {
        init();
        loop();
    }

    private static void loop() {
        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        // Criando vertex shader object && fragment shader object
        int vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderObject, vertexShaderSourceCode);
        glCompileShader(vertexShaderObject);

        // Checando por erros de compilacao do meu shader
        int sucessVertexShader = glGetShaderi(vertexShaderObject, GL_COMPILE_STATUS);
        if (sucessVertexShader != GL_TRUE) {
            String infoLog = glGetShaderInfoLog(vertexShaderObject);
            System.err.println("FAILED VERTEX SHADER CODE\n" + infoLog);
        }


        int fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderObject, fragmentSourceColorShader);
        glCompileShader(fragmentShaderObject);

        int successShaderFragment = glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS);
        if (successShaderFragment == GL_FALSE) {
            String infoLog = glGetShaderInfoLog(fragmentShaderObject);
            System.err.println("FAILED FRAGMENT SHADER CODE\n" + infoLog);
        }

        // Criando shader program e usando ele
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShaderObject);
        glAttachShader(shaderProgram, fragmentShaderObject);

        glLinkProgram(shaderProgram);

        glDeleteShader(vertexShaderObject);
        glDeleteShader(fragmentShaderObject);


        // Gerando um Vertex Array Object /  (Vertex Buffer Object - VBO) pro OpenGL usar
        int vertexArrayObject = glGenVertexArrays();
        int vertexBufferObject = glGenBuffers();

        glBindVertexArray(vertexArrayObject);

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // especificando para o OpenGL copmo ele deve interpretar o vertex data que vamos criar
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glUseProgram(shaderProgram);
            glBindVertexArray(vertexArrayObject);
            glDrawArrays(GL_TRIANGLES, 0, 3);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glDeleteVertexArrays(vertexArrayObject);
        glDeleteBuffers(vertexBufferObject);
        glDeleteProgram(shaderProgram);

        glfwTerminate();
    }

    private static void init() {
        if (!glfwInit()) {
            System.out.println("Failed to initialize GLFW");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Learning OpenGL", NULL, NULL);
        if (window == NULL) {
            System.out.println("I failed to create a window");
            glfwTerminate();
        }

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();
    }


}
