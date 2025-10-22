package com.example.media;

public class SoftwareRenderer implements Renderer {
    @Override public void render(String streamToken){
        System.out.println("[SoftwareRenderer] Rendering (CPU): " + streamToken);
    }
}
