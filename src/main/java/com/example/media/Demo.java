package com.example.media;

public class Demo {
    public static void main(String[] args) {
        // Setup renderers
        Renderer hardware = new HardwareRenderer();
        Renderer software = new SoftwareRenderer();
        MediaPlayer player = new MediaPlayer(hardware);

        // Setup sources
        MediaSource local = new LocalFileSource("movie.mp4");
        MediaSource hls = new HlsSource("https://example.com/live.m3u8");
        MediaSource remote = new RemoteApiSource("https://api.example.com/media/12");
        MediaSource cached = new RemoteProxySource(remote);

        // Legacy + adapter
        LegacyMonolithicSource old = new LegacyMonolithicSource("old-track-1");
        MediaSource adapted = new LegacySourceAdapter(old, "track1");

        // Build playlist
        CompositePlaylist root = new CompositePlaylist("Main Playlist");
        root.add(new MediaFile("Local Movie", local));

        CompositePlaylist sub = new CompositePlaylist("Stream Playlist");
        sub.add(new MediaFile("HLS Live", hls));
        sub.add(new MediaFile("Cached Remote", cached));
        root.add(sub);
        root.add(new MediaFile("Legacy Audio", adapted));

        System.out.println("\n=== 1. Play with Hardware Renderer ===");
        root.play(player);

        System.out.println("\n=== 2. Apply Plugins (Watermark + Equalizer) ===");
        Renderer withWatermark = new WatermarkDecorator(hardware, "SAMPLE-WM");
        Renderer withEq = new EqualizerDecorator(withWatermark, "BassBoost");
        player.applyPlugins(withEq);
        player.playSource(cached); // triggers proxy caching

        System.out.println("\n=== 3. Play Cached Again (Cache Hit) ===");
        player.playSource(cached);

        System.out.println("\n=== 4. Switch Renderer to Software ===");
        player.setRenderer(software);
        player.playSource(local);

        System.out.println("\n=== Demo Complete ===");
    }
}
