---
layout: default
title: Player Interacting Logic
nav_order: 4
parent: Player
grand_parent: Game Code Details
permalink: /GameCodeDetails/Player/PlayerInteractingLogic
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Player Interacting Logic

When the player hits the interact key (space) when in close enough proximity to a map entity that has an interact script,
the interact script will be executed. The logic behind determining whether the player successfully interacted with an entity
or not (be it a MapTile, NPC, etc.) takes place in the `Map` class's `entityInteract` method.

## Deciding which entity is interacted with

When the player hits the interact key and calls out to the `Map` class's `entityInteract` method, the method will being
by getting all potential entities the player could possibly be interacting with. This includes all surrounding map tiles,
and all currently active NPCs and enchanced map tiles. Then, it will go through each entity and determine if the player's interaction range bounds are currently
intersecting it. The `Player` class's interaction range is its current bounds plus a modifier (the `interactionRange` instance variable) to increase the range by a bit.

After going through all of the entities that the player's interaction range bounds intersect, more logic is then run
to ensure that the situation is "right" for the player to be able to interact with the entity. If after doing the above steps only one entity
was found to meet criteria, that entity is run through a series of validity checks involving its position relative to the player.
The player also had to have last been moving towards that entity in order for it to be valid.

If after going through all of the entities that the player's interaction range bounds intersect, and multiple entities meet criteria,
in addition to the extra validity checks mentioned in the above paragraph, there is also a series of comparisons done to see which entity is "closest" to the player.
This is done by checking which entity player intersection has the greatest surface area.

Once narrowed down to a single entity, the entity's interact script is turned active and begins execution.
If the call results in no entities being found to meet interaction criteria, nothing will happen.

Typically after a successful interaction, the entity's interaction script will set the player's state to `INTERACTING` while it is executing to prevent the player from being able to do anything.
More details on how the player's `INTERACTING` state works can be found [here][player states](./player-states.md#Player Interacting State).

