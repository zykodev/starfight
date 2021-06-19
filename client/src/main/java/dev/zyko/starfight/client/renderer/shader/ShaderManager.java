package dev.zyko.starfight.client.renderer.shader;

import dev.zyko.starfight.client.renderer.texture.Texture;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class ShaderManager {

    private HashMap<String, Shader> shaderMap = new HashMap<>();

    public ShaderManager() {
    }

    public void loadShaders() throws Exception {
        this.loadShader("basic");
    }

    private void loadShader(String name) throws Exception {
        InputStream vertex = ClassLoader.getSystemResourceAsStream("assets/shaders/" + name + ".vertex");
        InputStream fragment = ClassLoader.getSystemResourceAsStream("assets/shaders/" + name + ".fragment");
        if(vertex != null && fragment != null) {
            Shader shader = new Shader(this.toString(vertex), this.toString(fragment));
            this.shaderMap.put(name.toLowerCase(Locale.ROOT), shader);
        } else throw new Exception("Failed to load shader \"" + name + "\". Resource missing.");
    }

    public Shader getShader(String alias) {
        return this.shaderMap.get(alias.toLowerCase(Locale.ROOT));
    }

    private String toString(InputStream inputStream) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String read = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while((read = bufferedReader.readLine()) != null) {
            stringBuilder.append(read).append("\n");
        }
        return stringBuilder.toString();
    }

}
