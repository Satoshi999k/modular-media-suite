# Modular Media Suite — Reference Implementation

This repository contains a small reference Java project that demonstrates structural design patterns for a modular media streaming suite.  
It is educational: it simulates playback and demonstrates Adapter, Composite, Decorator, Proxy, and Strategy patterns.

## Repo layout

Modular Media Streaming Suite (Java) — demo project
--------------------------------------------------

Requirements
- Java 17+
- Maven 3.6+

Build & Run
1. Build:
   mvn -q -DskipTests package

2. Run demo:
   mvn exec:java

What the Demo does
- Constructs multiple sources (local, HLS, remote API), wraps remote with a caching proxy.
- Adapts a legacy source via Adapter.
- Builds a Composite playlist containing files and playlists.
- Plays the playlist with a hardware renderer.
- Applies a decorator stack (Watermark + Equalizer) and replays a remote cached source (shows cache hit/miss).
- Switches renderer at runtime to software renderer and plays a local file.

Commands to include in your video
- Show directory structure.
- Open `docs/` and display the `class_diagram.puml` and `sequence_play.puml` (PlantUML can render them).
- Run `mvn exec:java` and record:
  - Assemble and play playlist (30-60s)
  - Apply watermark decorator + equalizer (20-40s)
  - Switch renderer (hardware -> software) and play local (20-40s)
- Close with a short reflection (60-90s) using content from `docs/design_rationale.md`.

Suggested short tests
- Validate that proxy caches by calling `player.playSource(cachedRemote)` twice and confirming "Cache HIT" on second call.
