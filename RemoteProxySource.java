import java.util.HashMap;
import java.util.Map;

public class RemoteProxySource implements MediaSource {
    private final MediaSource remote;
    private static final Map<String,String> cache = new HashMap<>();

    public RemoteProxySource(MediaSource remote){ this.remote = remote; }

    @Override public String getId(){ return "proxy:" + remote.getId(); }

    @Override
    public String openStream(){
        String id = remote.getId();
        if (cache.containsKey(id)){
            System.out.println("[RemoteProxySource] Cache HIT for " + id);
            return cache.get(id);
        } else {
            System.out.println("[RemoteProxySource] Cache MISS for " + id);
            String token = remote.openStream();
            String cached = token + ":cached";
            cache.put(id, cached);
            return cached;
        }
    }
}
