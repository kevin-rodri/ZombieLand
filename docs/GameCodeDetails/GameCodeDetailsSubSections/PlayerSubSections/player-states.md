---
layout: default
title: Player States
nav_order: 2
parent: Player
grand_parent: Game Code Details
permalink: /GameCodeDetails/Player/PlayerStates
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Player States

The `Player` has several different states it can be in based on the value of its `playerState` instance variable.
Each state can lead to another state based on the player's actions each cycle of the game loop. The states also dictate
which animation/frames the player should switch to (e.g. player will switch to the walking animation when in the `WALKING` state)
The `PlayerState` enum in the `Level` package define the following states that the player can be in:
- **STANDING** -- player is standing still
- **WALKING** -- player is walking
- **WALKING** -- player is walking
- **INTERACTING** -- player is currently interacting with an entity

## Player Standing State

![player-standing.PNG](../../../assets/images/player-standing.PNG)

If the player is on the ground and no keys are pressed, the player will enter its `STANDING` state, where it does nothing
and just waits for another key to be pressed which will activate another state. The `playerStanding` method contains
the simple logic for the `STANDING` state, which looks like this:

```java
// player STANDING state logic
protected void playerStanding() {
    if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
        keyLocker.lockKey(INTERACT_KEY);
        map.entityInteract(this);
    }

    // if a walk key is pressed, player enters WALKING state
    if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
        playerState = PlayerState.WALKING;
    }
}
```

It really just checks for arrow key presses to determine if the player should enter the `WALKING` state, 
and also checks if the interact key is pressed in order to tell the map that the player has attempted to interact with an entity.

While standing, the player uses either the `STAND_RIGHT` or `STAND_LEFT` animation.

### Player Walking State

![player-walking.gif](../../../assets/images/player-walking.gif)

If the player is on the ground and either, right, left, up, or down arrow key is pressed, the player will enter its `WALKING` state,
where it will move in a direction by a set number of pixels based on which key was pressed. If no keys are pressed while in this state, the player will stop moving and go
back into its `STANDING` state. How fast the player walks is determined by the `walkSpeed` instance variable. The `playerWalking` method
contains the logic for the `WALKING` state. The player will change the direction it's facing if the left or right arrow key is pressed.

While walking, the player uses either the `WALK_RIGHT` or `WALK_LEFT` animation.

### Player Interacting State

![player-standing.PNG](../../../assets/images/player-standing.PNG)

If the player is in `STANDING` or `WALKING` state and hits the interact key (space), it will reach out to a method in the `map` class's `entityInteract` method
to check if the player has successfully interacted with an entity that has an interact script. If the player successfully interacted with an entity,
the entity's interact script will be executed. Often times, the entity's interact script will want the player to be "frozen" in place while the script is executing,
so that the player can't just walk away before the event has completed. The `INTERACTING` state is for this purpose -- there is no logic in the `playerInteracting` method,
meaning the player will be unable to do anything -- can't move, can't interact with other entities, etc.

It is a script's job to lock the player by setting its player state to `INTERACTING`. It is also a script's job to unlock the player when it is ready (typically after the script has completed) by setting the player's state back to something else (like `STANDING`).
The `Script` class's methods `lockPlayer` and `unlockPlayer` will handle doing this locking and unlocking of the player.
Read more about scripts [here](./scripts.md).