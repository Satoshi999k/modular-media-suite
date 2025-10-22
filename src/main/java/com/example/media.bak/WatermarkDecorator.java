public class WatermarkDecorator extends RendererDecorator {
    private final String text;
    public WatermarkDecorator(Renderer wrapped, String text){
        super(wrapped); this.text = text;
    }
    @Override public void render(String streamToken){
        System.out.println("[WatermarkDecorator] Adding watermark: " + text);
        super.render(streamToken);
    }
}
