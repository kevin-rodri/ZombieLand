package Scripts.TestMap;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import GameObject.Frame;
import Level.*;
import Utils.Direction;
import Utils.Point;

// script for talking to dino npc
// the script is segmented -- it has multiple setups, cleanups, and executions based on its current action
public class DinoScript extends Script<NPC> {

    private int sequence = 0;
    private int amountMoved = 0;

    @Override
    protected void setup() {
        lockPlayer();

        if (!isFlagSet("hasTalkedToWalrus")) {
            showTextbox();
            addTextToTextboxQueue("Isn't my garden so lovely?");
        }
        else if (isFlagSet("hasTalkedToWalrus") && !isFlagSet("hasTalkedToDinosaur")) {
            if (sequence == 0) {
                showTextbox();
                addTextToTextboxQueue("Isn't my garden so lovely?");
            }
            else if (sequence == 1) {
                setWaitTime(1000);
            }
            else if (sequence == 2) {
                entity.facePlayer(player);
                showTextbox();
                addTextToTextboxQueue("Oh, you're still here...");
                addTextToTextboxQueue("...You heard from Walrus that he saw me with your\nball?");
                addTextToTextboxQueue("Well, I saw him playing with it and was worried it would\nroll into my garden.");
                addTextToTextboxQueue("So I kicked it as far as I could into the forest to the left.");
                addTextToTextboxQueue("Now, if you'll excuse me, I have to go.");
            }
            else if (sequence == 3) {
                entity.stand(Direction.RIGHT);
                amountMoved = 0;
            }
            else if (sequence == 4) {
                amountMoved = 0;
            }
            else if (sequence == 5) {
                entity.stand(Direction.LEFT);

                // change door to the open door map tile
                Frame openDoorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 4), 0)
                        .withScale(map.getTileset().getTileScale())
                        .build();
                Point location = map.getMapTile(17, 4).getLocation();

                MapTile mapTile = new MapTileBuilder(openDoorFrame)
                        .build(location.x, location.y);

                setMapTile(17, 4, mapTile);

                amountMoved = 0;
            }
        }
    }

    @Override
    protected void cleanup() {
        if (!isFlagSet("hasTalkedToWalrus")) {
            unlockPlayer();
            hideTextbox();
        }
        else if (isFlagSet("hasTalkedToWalrus") && !isFlagSet("hasTalkedToDinosaur")) {
            if (sequence == 0) {
                hideTextbox();
                sequence++;
            }
            else if (sequence == 1) {
                sequence++;
            }
            else if (sequence == 2) {
                hideTextbox();
                sequence++;
            }
            else if (sequence == 3) {
                sequence++;
            }
            else if (sequence == 4) {
                sequence++;
            }
            else if (sequence == 5) {
                sequence++;

                // change door back to the closed door map tile
                Frame doorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 3), 0)
                        .withScale(map.getTileset().getTileScale())
                        .build();
                Point location = map.getMapTile(17, 4).getLocation();

                MapTile mapTile = new MapTileBuilder(doorFrame)
                        .withTileType(TileType.NOT_PASSABLE)
                        .build(location.x, location.y);

                setMapTile(17, 4, mapTile);

                entity.setIsHidden(true);
                setFlag("hasTalkedToDinosaur");
                unlockPlayer();
            }
        }
    }

    @Override
    public ScriptState execute() {
        if (!isFlagSet("hasTalkedToWalrus")) {
            start();
            if (!isTextboxQueueEmpty()) {
                return ScriptState.RUNNING;
            }
            end();
            return ScriptState.COMPLETED;
        }
        else if (!isFlagSet("hasTalkedToDinosaur")) {
            // talks
            if (sequence == 0) {
                start();
                if (isTextboxQueueEmpty()) {
                    end();
                }
            }
            // pauses
            else if (sequence == 1) {
                start();
                if (isWaitTimeUp()) {
                    end();
                }
            }
            // talks more
            else if (sequence == 2) {
                start();
                if (isTextboxQueueEmpty()) {
                    end();
                }
            }
            // walk downwards
            else if (sequence == 3) {
                start();
                entity.walk(Direction.DOWN, 2);
                amountMoved += 2;
                if (amountMoved == 36) {
                    end();
                }
            }
            // walk right
            else if (sequence == 4) {
                start();
                entity.walk(Direction.RIGHT, 2);
                amountMoved += 2;
                if (amountMoved == 196) {
                    end();
                }
            }
            // walk up
            else if (sequence == 5) {
                start();
                entity.walk(Direction.UP, 2);
                amountMoved += 2;
                if (amountMoved == 50) {
                    end();
                }
            }
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    }
}

