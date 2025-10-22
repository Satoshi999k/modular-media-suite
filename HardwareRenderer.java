public class HardwareRenderer implements Renderer {
    @Override public void render(String streamToken){
        System.out.println("[HardwareRenderer] Rendering (GPU): " + streamToken);
    }
}
