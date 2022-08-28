---
layout: default
title: Map Entities
nav_order: 5
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapEntities
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Entities

## What is a map entity?

A map entity, represented by the `MapEntity` class in the `Level` package, is any game object that is a part of a map.
This includes [map tiles](./map-tiles-and-tilesets.md) (`MapTile` class), [NPCs](./npcs.md) (`NPC` class), 
[enhanced map tiles](./enhanced-map-tiles.md) (`EnhancedMapTiles` class), and [Triggers](./triggers.md) (`Trigger` class).

## What is the purpose of this class?

The main purpose of the `MapEntity` class, which extends from `GameObject`, is that the `MapEntity` class adds a couple of instance variables
made for map entities other than the player.

## Map Entity Fields

### Map Entity Features

All map entities have an instance variable `mapEntityStatus` which the map's `Camera` uses to determine if the entity is
"active" or not. An active entity means it should be included in the level's `update`/`draw` cycle for a current frame. Entities
that are too far offscreen for example will be removed from the `Camera's` `update`/`draw` cycle until they are back on screen,
as the game does not want to waste resources on entities that at that current frame have no affect on the level or the player.

The `MapEntityStatus` enum in the `Level` package defines three different possible statuses: `ACTIVE`, `INACTIVE`, and `REMOVED`.
An entity generally doesn't have to mess with this value as the `Camera` handles the logic for checking active vs inactive entities,
however an entity may set its own status to `REMOVED` to have it permanently removed from the level with no ability to respawn.

### Hidden

Setting a map entity's `isHidden` attribute to true will prevent its update and draw logic from being run, essentially "hiding" it from the map.
At any time, the entity can be "brought back" by setting its `isHidden` attribute back to false. It's an alternative to setting an entity's status to `REMOVED`
that keeps the entity as a part of the map, so that it can be brought back whenever needed.

### Existence Flag

A map entity can optionally define an `existenceFlag`. A flag is a boolean value, and the game keeps track of multiple flags
in order to track where the player's progress is at, determine which events should play out, etc. More on flags can be read about [here](./scripts.md#Flags)

If an entity's `existenceFlag` becomes set during the game, the entity will no longer "exist", and the game will not run its update and draw logic anymore.
If the entity's `existenceFlag` becomes unset, it will "exist" again and the game will run its logic like normal.
This feature is useful for telling certain entities like NPCs and Triggers to disappear after a certain event occurs.
An entity with no `existenceFlag` set is always considered to "exist".

In the game currently, the `TestMap` sets the dinosaur NPC's `existenceFlag` to `"hasTalkedToDinosaur"`.
Once that flag is set (which happens after talking to the dinosaur during a specific part of the game),
the dinosaur no longer "exists" and is no longer shown on the map.

```java
Dinosaur dinosaur = new Dinosaur(2, getMapTile(13, 4).getLocation());

// existence flag is set here
dinosaur.setExistenceFlag("hasTalkedToDinosaur");

dinosaur.setInteractScript(new DinoScript());
npcs.add(dinosaur);
```

[Triggers](./triggers.md) also often use this feature to ensure they only are able to be triggered one time.

### Interact Script

Any entity in the game can define its own interact script, which is a `Script` that executes when the player interacts with the entity.
The player interacts with an entity by walking up to it in a close enough proximity and pressing the space key.

Read more about scripts [here](./scripts.md). This game is built around interact and trigger scripts, and is one of the most important pieces of the game to understand.

In the game currently, `TestMap` adds interact scripts to the walrus NPC, dinosaur NPC, all three sign tiles, and the bottom tree trunk tile (where the player finds their ball at the end of the game).
The below code in `TestMap` shows an example of setting an interact script on specific map tiles (like the sign tiles):

```java
@Override
public void loadScripts() {
    // set interact script on sign to say "Cat's house" when talked to
    getMapTile(21, 19).setInteractScript(new SimpleTextInteractScript("Cat's house"));
    
    // ...
}
```