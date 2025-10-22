# Design Rationale

## Goal
Refactor a messy, monolithic media player into a modular streaming suite that supports:
- multiple media sources (local, HLS, remote API, legacy),
- runtime renderer switching (hardware vs software),
- on-the-fly plugins (watermark, equalizer, subtitles),
- composite playlists,
- and remote-proxy caching.

The implementation focuses on separation of concerns, testability, and minimal changes to existing code while enabling future extension.

## Patterns used and why

**Adapter (LegacySourceAdapter)**  
Problem: legacy source classes expose incompatible APIs.  
Solution: wrap the legacy class in an adapter that implements the `MediaSource` interface.  
Benefit: the rest of the system treats legacy sources exactly like new ones. This isolates legacy code and avoids invasive rewrites.

**Proxy (RemoteProxySource)**  
Problem: remote streams are expensive and unreliable; repeated fetches reduce performance.  
Solution: a proxy sits in front of a remote source, caches results (stream token or cached file), and serves cached content when available.  
Benefit: transparent performance gains and centralized cache behavior. The client still calls `openStream()` on a `MediaSource` and doesn't care about caching details.

**Composite (CompositePlaylist and MediaFile)**  
Problem: playlists can contain single files and nested playlists.  
Solution: both `MediaFile` and `CompositePlaylist` implement `PlaylistItem`. The player recursively traverses playlist items.  
Benefit: uniform traversal and simplified orchestration. Adding or removing nested playlists requires no changes to the player.

**Decorator (RendererDecorator, Watermark, Equalizer)**  
Problem: features (watermark, equalizer, subtitles) should be togglable and composable without creating many renderer subclasses.  
Solution: decorators wrap a `Renderer` and augment `render()` behavior before delegating.  
Benefit: avoid combinatorial explosion of classes (e.g., Hardware+Watermark+EQ). Plugins can be stacked or reordered at runtime.

**Strategy (Renderer switching)**  
Problem: choose rendering implementation (hardware vs software) at runtime.  
Solution: `Renderer` interface with concrete implementations; `MediaPlayer` holds a reference and can swap it.  
Benefit: runtime flexibility and separation of rendering concerns; the player remains renderer-agnostic.

## Trade-offs and limitations
- **Simulation vs production:** The demo prints actions rather than performing real media I/O. Integrating real playback libraries (JavaFX, VLCJ, FFmpeg) adds complexity (UI threads, buffering, licensing) but is straightforward within this architecture.
- **Decorator ordering:** Effects depend on ordering (e.g., EQ then watermark vs watermark then EQ may matter). Production systems should manage plugin ordering and priorities via a plugin manager.
- **Cache policy simplicity:** The proxy uses an in-memory cache for demo purposes. Real systems need eviction policies, persistence, validation, partial caching, and bandwidth-aware prefetching.
- **Concurrency:** Real streaming requires asynchronous fetching, buffer management, back-pressure control, and robust error handling. The current design shows where to plug in async layers without changing high-level orchestration.
- **Observability & lifecycle:** There is no plugin lifecycle or monitoring in the demo. Production systems should include plugin enable/disable APIs, metrics, and health checks.

## Alternatives considered
- **Event-driven plugin bus:** instead of decorators, a plugin event bus could broadcast playback lifecycle events (onStart, onFrame, onStop). This is more flexible for loosely coupled features (e.g., analytics) but makes ordered transformations of the media stream harder to guarantee.
- **Chain of Responsibility:** for source preprocessing (auth, logging) a CoR could handle modular pre-processing steps before delegating to the source. The proxy and adapter combine to cover many CoR use cases here.
- **Factory/Registry for plugins:** a plugin registry would help manage configuration and ordering centrally; the demo uses manual composition for simplicity.

## Extensions & next steps
- Replace simulated renderers with a real playback engine (JavaFX MediaPlayer or VLCJ). Implement buffering and threading inside each `Renderer`.
- Implement a plugin manager that supports configuration, priority, enable/disable, and persistence of settings.
- Upgrade `RemoteProxySource` to a disk-backed cache with LRU eviction and TTLs.
- Add unit and integration tests: verify cache miss/hit behavior, playlist traversal, and decorator order effects.

## Conclusion
The chosen structural patterns — Adapter, Proxy, Composite, Decorator, and Strategy — keep the orchestration simple and robust while making the system highly extensible. The player remains focused on coordinating playback; new sources, rendering strategies, or plugins can be added with minimal disruption to existing code.
