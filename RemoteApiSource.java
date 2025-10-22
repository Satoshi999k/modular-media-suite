public class RemoteApiSource implements MediaSource {
    private final String apiEndpoint;
    public RemoteApiSource(String api){ this.apiEndpoint = api; }
    @Override public String getId() { return "api:" + apiEndpoint; }
    @Override
    public String openStream(){
        System.out.println("[RemoteApiSource] Requesting remote stream: " + apiEndpoint);
        return "stream(api:" + apiEndpoint + ")";
    }
}
