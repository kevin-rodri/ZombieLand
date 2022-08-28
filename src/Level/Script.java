package Level;
import GameObject.Rectangle;
import Utils.Direction;
import Utils.Stopwatch;

// This class is a base class for all scripts in the game -- all scripts should extend from it
// Scripts can be used to interact with map entities
// Each script defines a set of instructions that will be carried out by the game when it is set to active
// Some examples include interact scripts (such as talking to an NPC) and trigger scripts (scripts that activate when the player walks on them)
public abstract class Script<T extends MapEntity> {
    // this is set to true if script is currently being executed
    protected boolean isActive = false;

    // if true, script should perform "setup" logic
    protected boolean start = true;

    // references to the map entity the script is attached to
    // use generic type if you need to use this reference
    protected T entity;

    // reference to the map instance which can be used in any script
    protected Map map;
    // reference to the player instance which can be used in any script
    protected Player player;

    protected Stopwatch stopwatch = new Stopwatch();

    public Map getMap() { return map; }
    public void setMap(Map map) { this.map = map; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public T getEntity() { return entity; }
    public void setMapEntity(T entity) {
        this.entity = entity;
    }

    // "setup" logic for a script to prepare for execution update cycle
    protected abstract void setup();

    // "cleanup" logic for a script to be carried out after execution update cycle ends
    protected abstract void cleanup();

    // the "meat" of the script, it's the logic to be carried out when the script becomes active
    // when script is finished, it should return the COMPLETED Script State
    // if script is still running, it should return the RUNNING Script State
    protected abstract ScriptState execute();

    public void update() {
        // Runs an execute cycle of the Script
        ScriptState scriptState = execute();

        // If Script is completed, set it to inactive to allow game to carry on
        if (scriptState == ScriptState.COMPLETED) {
            this.isActive = false;
            map.setActiveInteractScript(null);
        }
    }

    // call setup logic once on script start
    protected void start() {
        if (start) {
            setup();
            start = false;
        }
    }

    // call cleanup logic
    // reset start in case more setup logic is to be carried out in the case of multistep scripts
    protected void end() {
        cleanup();
        start = true;
    }

    // if is active is true, game will execute script
    public boolean isActive() { return isActive; }

    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    // prevents player from being able to do anything (such as move) while script is being executed
    // useful to prevent player from moving away while interacting with something, etc
    protected void lockPlayer() {
        player.setPlayerState(PlayerState.INTERACTING);
        player.setCurrentAnimationName(player.getFacingDirection() == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT");
    }

    // allow player to go back to its usual game state (being able to move, talk to things, etc)
    // typically used right before script is finished to give control back to the player
    protected void unlockPlayer() {
        player.setPlayerState(PlayerState.STANDING);
    }

    // textbox is shown on screen
    protected void showTextbox() {
        map.getTextbox().setIsActive(true);
    }

    // adds text to be shown in textbox
    protected void addTextToTextboxQueue(String text) {
        map.getTextbox().addText(text);
    }

    // adds a series of text to be shown in textbox
    protected void addTextToTextboxQueue(String[] text) {
        map.getTextbox().addText(text);
    }

    // checks if textbox has already shown all text in its queue
    protected boolean isTextboxQueueEmpty() {
        return map.getTextbox().isTextQueueEmpty();
    }

    // remove textbox from screen
    protected void hideTextbox() {
        map.getTextbox().setIsActive(false);
    }

    // gets an npc instance by its id value
    protected NPC getNPC(int npcId) {
        for (NPC npc : map.getNPCs()) {
            if (npc.getId() == npcId) {
                return npc;
            }
        }
        return null;
    }

    // force an npc instance to face the player
    // npc chosen based on its id value
    protected void npcFacePlayer(int npcId) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.facePlayer(player);
        }
    }

    // force an npc to walk in a specified direction at a specified speed
    // npc chosen based on its id value
    protected void npcWalk(int npcId, Direction direction, float speed) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.walk(direction, speed);
        }
    }

    // force an npc to enter a specified animation
    // npc chosen based on its id value
    protected void npcSetAnimation(int npcId, String animationName) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimationName(animationName);
        }
    }

    // force an npc to enter a specified frame of their current animation
    // npc chosen based on its id value
    protected void npcSetAnimationFrameIndex(int npcId, int frameIndex) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimationFrameIndex(frameIndex);
        }
    }

    // checks if a certain flag has been set or not
    protected boolean isFlagSet(String flagName) {
        return map.getFlagManager().isFlagSet(flagName);
    }

    // sets a flag to true
    protected void setFlag(String flagName) {
        map.getFlagManager().setFlag(flagName);
    }

    // sets a flag to falase
    protected void unsetFlag(String flagName) {
        map.getFlagManager().unsetFlag(flagName);
    }

    // sets time to wait
    protected void setWaitTime(int milliseconds) {
        stopwatch.setWaitTime(milliseconds);
    }

    // checks if wait time is completed (use in conjunction with setWaitTime)
    protected boolean isWaitTimeUp() {
        return stopwatch.isTimeUp();
    }

    // gets a specified map tile instance by index from the map
    protected MapTile getMapTile(int x, int y) {
        return map.getMapTile(x, y);
    }

    // changes a specified map tile instance by index from the map to the provided map tile
    protected void setMapTile(int x, int y, MapTile mapTile) {
        mapTile.setMap(map);
        map.setMapTile(x, y, mapTile);
    }

    // checks if player is currently below the entity attached to this script
    protected boolean isPlayerBelowEntity() {
        Rectangle entityBounds = entity.getCalibratedBounds();
        return player.getBounds().getY1() >= entityBounds.getY2();
    }
}
