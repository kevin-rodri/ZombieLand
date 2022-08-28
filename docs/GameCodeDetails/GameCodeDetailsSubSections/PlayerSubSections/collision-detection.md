---
layout: default
title: Collision Detection
nav_order: 3
parent: Player
grand_parent: Game Code Details
permalink: /GameCodeDetails/Player/CollisionDetection
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Collision Detection

## What is collision detection?

Collision detection is the ability for entities in the game, such as the player, to be able to detect "collisions" with other map entities,
such as map tiles, NPCs, etc. This is generally done through checking if two entities "intersect" each other, at which point a collision
has occurred. Games are built around collision detection -- think of any of your favorite video games and tell me if the game would still work
if entities could not detect and react to colliding with other entities, and I guarantee you it cannot.

The hard part about collision detection is there are A LOT of different "scenarios" where a collision can occur between two entities,
and each entity needs to react accordingly to the collision. All map entities in this game engine are rectangles (x, y, width, height) under the hood,
which thankfully lessens a lot of the complexity that other shapes would introduce. Rectangles are the easiest shape to work with by far due to
"rectangle math" being relatively simple and it works out very nicely in a 2D space where only the x and y axis exist.

Also, collision detection is always the hardest part about creating a game, and anyone will tell you that writing your own collision detection is the WORST when it comes to platformers.
This is especially true when it comes to 3D games -- it is difficult, complex math can be involved, and tiny errors randomly creep up just when you finally think you've gotten everything down pact.
Luckily, with 2D games, things are a lot simpler, but still can be a real painpoint.
In this game's case, getting proper decimal movement caused me a whole lot of trouble -- and I've even written code like this before for another game!
It's just hard stuff, but I believe this game's collision detection is in a good state right now (it better be after all the time I put into it).
I know it doesn't sound difficult, but trust me...there's a lot of problems that rear their ugly heads when you work on implementing it.

## Player collision detection with Map Tiles

**tl;dr**: This is probably the most complicated part of this game. Each frame the player moves by a specified amount unless it collides
with a solid map tile, in which case it is told to stop moving. Use the `moveXHandleCollision` and `moveYHandleCollision` methods when moving an entity that needs to
detect collision. An entity's generic `moveX` and `moveY` methods do not account for collision.

The main reason for needing extremely precise collision detecting in a platformer game is for the player to be able to traverse the map
without being able to fall through the floor or run through obstacles.

The `GameObject` class contains two special move methods named `moveXHandleCollisions` and `moveYHandleCollision`. These methods
will move the player by a specified amount, and the `Player` calls these methods each frame to move it by a set amount based on which
actions the player took (such as walking forward). Unlike the plain `moveX` and `moveY` methods which simply move a player by a specified amount with no questions asked,
the `moveXHandleCollision` and `moveYHandleCollision` methods will stop the player from moving if it collides with a "solid" entity,
such as a `NOT_PASSABLE` ("solid") map tile. Each `MapTile` has an assigned [tile type](../MapSubSections/map-tiles-and-tilesets.md#tile-types), and the move handle collisions
methods will take these into account when moving the player to determine if a player is allowed to move to the new location or not.

Decimal movement makes this a bit more complex. While a player can be at a decimal location in game logic, in draw logic it cannot
since a graphic cannot be physically drawn on half of a monitor pixel. For this reason, while decimal precision is still possible to implement,
it just won't always apply a graphics update if the decimal movement is small enough to not change the round up or down to the nearest whole number.
For example, a decimal amount of `1.49` will round down to `1`, but `1.5` will round up to `2`.

So, how exactly do the `moveXHandleCollision` and `moveYHandleCollision` methods work? First thing they do is identify the amount of pixels the player is about to move,
the direction to move in, and lastly calculate decimal values to figure out if a potential round up or round down change will occur. From there,
the methods will move the player one pixel at a time over and over again until it reaches the total amount the player is supposed to move by.
During each one pixel move, collisions are checked against the map tiles, and if a collision is detected the movement is stopped and all leftover
movement is thrown away. Lastly, decimal values are recalculated and figured out -- if a round up occurs, one more pixel is moved and collision detection
is checked one last time.

The `Player` calls these two methods from the `GameObject` class each frame in its `update` logic:

```java
// move player with respect to map collisions based on how much player needs to move this frame
lastAmountMovedY = super.moveYHandleCollision(moveAmountY);
lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
```

The `MapCollisionHandler` class is used by the move handle collisions methods to determine if a collision occurred, and if a collision
did occur the player's position is adjusted to be right in front of the solid map tile it collided with.

The `MapCollisionHandler` contains three methods: `getAdjustedPositionAfterCollisionCheckX`, `getAdjustedPositionAfterCollisionCheckY`,
and `hasCollidedWithMapEntity`. 

The first two methods `getAdjustedPositionAfterCollisionCheckX` and `getAdjustedPositionAfterCollisionCheckY` do some "rectangle math" to determine which tiles in the map need to be checked for collisions.
A common mistake newer game developers make is writing collision checking code that checks against every single tile in the map every single frame -- this is a huge
waste of resources and can severely impact the game's FPS as the game logic step can end up taking too long. Instead, this code will determine the direction the player is moving, and based on that only check the immediate tile(s) in the vicinity. Just like with sorting algorithms, lowering the amount of comparisons
is key to have a smoothly running game! After determining which tiles could possibly be collied with, the third method `hasCollidedWithMapEntity` is called on each map tile that to see if a collision has occurred based on the map tile's tile type.

The `hasCollidedWithMapEntity` method is very simple compared to the monstrous other two adjust position methods. It determines
if the tile a player intersects with is considered a "collision" or not. This is 100% based on the tile's tile type. A tile type of `NOT_PASSABLE`
would return true to there being a collision if the player intersects with it. `PASSABLE` tiles cannot be collided with and would always return false.

```java
// based on tile type, perform logic to determine if a collision did occur with an intersecting tile or not
private static boolean hasCollidedWithMapEntity(GameObject gameObject, MapEntity mapEntity, Direction direction) {
    if (mapEntity instanceof MapTile) {
        MapTile mapTile = (MapTile)mapEntity;
        switch (mapTile.getTileType()) {
            case PASSABLE:
                return false;
            case NOT_PASSABLE:
                return gameObject.intersects(mapTile);
            default:
                return false;
        }
    }
    else {
        return mapEntity.intersects(gameObject);
    }
}
```

More tile types can easily be added to this method to determine if a collision occurred or not. The other two methods don't really have to be
modified at all, as new tiles types added to this `hasCollidedWithMapEntity` method will work just as the existing three tile types do.

## Player collision detection with Enhanced Map Tiles

Additionally, these methods check against all active [enhanced map tiles](../MapSubSections/enhanced-map-tiles.md) in the map, as they
are treated like regular map tiles in terms of collision detection. So for example,
the game's green horizontal moving platform would also be checked against for possible collisions during this area of the code.

Although standard collision detection on enhanced map tiles is done here, `EnhancedMapTile` classes can run their own
`update` logic to do other actions when intersecting with a player. For example, the `Rock` class carries out
its own logic upon determining that the player walked into it, so it can move itself to make it appear like it is being pushed.

![pushing-rock.gif](../../../assets/images/pushing-rock.gif)

## Player collision detection with NPCs

Each NPC has its own defined bounds, which acts as their "hurtbox".
This makes collision detection very easy -- the player is not allowed to walk through an NPC's bounds.

## Player collision detection with Triggers

If a player walks on top of a trigger, the player will be stopped in its tracks and the trigger script will be set to active
and begin executing.

## Player class reacting to a collision

The `GameObject` method provides two methods that are intended to be overridden by a subclass: `onEndCollisionCheckX` and `onEndCollisionCheckY`.
After a collision check has occurred, the collision methods will let the `Player` class (or any other `GameObject` subclass like the `NPC` class that can move
while checking for collisions) know if a collision occurred and what direction the collision happened from (left, right, up, or down).

At the moment, these aren't being used in the `Player` class (or any other entity), but they can be really useful for other future features.. 
Here's how they would be used

```java
@Override
public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
    // if player collides with something
    if (hasCollided) {
        // if player collided with something below it
        if (direction == Direction.DOWN) {
            // ... do something here
        }
    }
}
```

The `entityCollidedWith` parameter can be checked against using the `instanceof` keyword like in the below example:

```java
if (hasCollided && entityCollidedWith instanceof MapTile) {
    MapTile tileCollidedWith = (MapTile)entityCollidedWith;
    // do whatever you want from here
}
```

The above also works with enhanced map tiles, NPCs, etc.:

```java
if (hasCollided && entityCollidedWith instanceof EnhancedMapTile) {
    EnhancedMapTile tileCollidedWith = (EnhancedMapTile)entityCollidedWith;
    // do whatever you want from here
}
```