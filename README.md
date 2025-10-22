# Modular Media Streaming Suite

## Overview
The **Modular Media Streaming Suite** is a Java-based simulation of a flexible and extensible media playback system.  
It demonstrates the application of **structural design patterns** to build a modular, scalable, and maintainable architecture.  
The system focuses on conceptual design rather than actual media rendering, using console output to simulate functionality.

---

## System Architecture
The suite is organized into modular packages, each representing a distinct subsystem:

- **`media.source`** — Handles different media sources such as local files, HLS streams, and remote APIs.  
- **`media.proxy`** — Implements a caching proxy to simulate remote data retrieval and reuse.  
- **`media.plugin`** — Provides decorators for extending playback behavior (e.g., watermarking, audio equalization).  
- **`media.playlist`** — Demonstrates the Composite pattern by combining single files and sub-playlists.  
- **`media.player`** — Manages playback and rendering strategies (hardware or software).  

Each package interacts through well-defined interfaces, ensuring low coupling and high modularity.

---

## Design Patterns Used
| Pattern | Description | Example Use |
|----------|--------------|-------------|
| **Adapter** | Converts incompatible interfaces into a usable form. | Adapting legacy sources into the `MediaSource` interface. |
| **Composite** | Represents hierarchical structures as tree components. | Organizing nested playlists. |
| **Decorator** | Dynamically adds extra features to objects. | Applying watermark or equalizer during playback. |
| **Proxy** | Controls access and adds caching to remote resources. | Caching remote media streams. |
| **Strategy** | Defines interchangeable algorithms or behaviors. | Switching between hardware and software renderers. |

These patterns collectively enable flexibility, scalability, and reusability across system modules.

---

## Process Flow
1. Initialize multiple media sources (local, remote, HLS).  
2. Wrap remote sources with a proxy to simulate caching.  
3. Construct composite playlists containing both media files and nested playlists.  
4. Apply decorators such as `WatermarkDecorator` and `EqualizerDecorator`.  
5. Switch between rendering strategies at runtime.  
6. Simulated playback and cache behaviors are displayed through console messages.

---

## Example Console Output
Playing from local file: myvideo.mp4
Applying WatermarkDecorator...
Applying EqualizerDecorator...
Switching to Software Renderer...
Playing from remote (Cache MISS)
Playing from remote (Cache HIT)
