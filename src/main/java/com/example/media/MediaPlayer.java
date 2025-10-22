package com.example.media;

public class MediaPlayer {
    private Renderer renderer;

    public MediaPlayer(Renderer renderer){ this.renderer = renderer; }

    public void setRenderer(Renderer renderer){
        System.out.println("[MediaPlayer] Switched to: " + renderer.getClass().getSimpleName());
        this.renderer = renderer;
    }

    public void applyPlugins(Renderer decoratorStack){
        this.renderer = decoratorStack;
        System.out.println("[MediaPlayer] Applied plugins: " + decoratorStack.getClass().getSimpleName());
    }

    public void playSource(MediaSource source){
        System.out.println("[MediaPlayer] Playing: " + source.getId());
        String token = source.openStream();
        renderer.render(token);
        System.out.println("[MediaPlayer] Done playing " + source.getId());
    }
}
