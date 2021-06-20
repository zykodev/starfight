package dev.zyko.starfight.client.renderer.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.InputStream;

public class Shader {

    private int programId, fragmentShaderId, vertexShaderId;

    public void runTick() {}

    public Shader(String vertexShader, String fragmentShader) throws Exception {
        this.programId = GL20.glCreateProgram();
        this.vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(this.vertexShaderId, vertexShader);
        GL20.glCompileShader(this.vertexShaderId);
        if(GL20.glGetShaderi(this.vertexShaderId, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
            System.out.println(GL20.glGetShaderInfoLog(this.vertexShaderId));
            throw new IllegalStateException("Failed to compile vertex shader. Application stuck in illegal state.");
        }
        this.fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(this.fragmentShaderId, fragmentShader);
        GL20.glCompileShader(this.fragmentShaderId);
        if(GL20.glGetShaderi(this.fragmentShaderId, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
            System.out.println(GL20.glGetShaderInfoLog(this.fragmentShaderId));
            throw new IllegalStateException("Failed to compile fragment shader. Application stuck in illegal state.");
        }
        GL20.glAttachShader(this.programId, this.vertexShaderId);
        GL20.glAttachShader(this.programId, this.fragmentShaderId);

        GL20.glBindAttribLocation(this.programId, 0, "vertices");
        GL20.glBindAttribLocation(this.programId, 1, "textures");

        GL20.glLinkProgram(this.programId);
        if(GL20.glGetProgrami(this.programId, GL20.GL_LINK_STATUS) != GL11.GL_TRUE) {
            System.out.println(GL20.glGetProgramInfoLog(this.programId));
            throw new IllegalStateException("Failed to link shader program. Application stuck in illegal state.");
        }
        GL20.glValidateProgram(this.programId);
        if(GL20.glGetProgrami(this.programId, GL20.GL_VALIDATE_STATUS) != GL11.GL_TRUE) {
            System.out.println(GL20.glGetProgramInfoLog(this.programId));
            throw new IllegalStateException("Failed to validate shader program. Application stuck in illegal state.");
        }
    }

    public void setUniform(String paramName, int value) {
        int location = GL20.glGetUniformLocation(this.programId, paramName);
        if(location != -1) {
            GL20.glUniform1i(location, value);
        } else {
            System.out.println("Unable to find parameter \"" + paramName + "\".");
        }
    }

    public void setUniform(String paramName, float value) {
        int location = GL20.glGetUniformLocation(this.programId, paramName);
        if(location != -1) {
            GL20.glUniform1f(location, value);
        } else {
            System.out.println("Unable to find parameter \"" + paramName + "\".");
        }
    }

    public void setUniform(String paramName, float x, float y) {
        int location = GL20.glGetUniformLocation(this.programId, paramName);
        if(location != -1) {
            GL20.glUniform2f(location, x, y);
        } else {
            System.out.println("Unable to find parameter \"" + paramName + "\".");
        }
    }

    public void bindShader() {
        GL20.glUseProgram(this.programId);
    }

    public void unbindShader() {
        GL20.glUseProgram(0);
    }

}
