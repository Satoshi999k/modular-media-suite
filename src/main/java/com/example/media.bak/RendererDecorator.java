public abstract class RendererDecorator implements Renderer {
    protected final Renderer wrapped;
    public RendererDecorator(Renderer wrapped){ this.wrapped = wrapped; }
    @Override public void render(String streamToken){
        wrapped.render(streamToken);
    }
}
