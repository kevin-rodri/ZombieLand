package Screens;

import Engine.*;

import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;
import java.io.IOException;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected SpriteFont coopOption;
    protected SpriteFont recordTime;
    protected Map background;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
	protected Player coOp;


    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        try {
            CreateFile.checkTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playGame = new SpriteFont("SINGLE PLAYER", 200, 150, "z", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(5);
        credits = new SpriteFont("CREDITS", 200, 350, "z", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(5);
        coopOption = new SpriteFont("CO-OP", 200, 250, "z", 30, Color.white);
        coopOption.setOutlineColor(Color.black);
        coopOption.setOutlineThickness(5);
        recordTime = new SpriteFont("Record Time: " + CreateFile.recordValue + " Minutes " + CreateFile.recordMinStr + " seconds ", 200, 450, "z", 30, Color.white);
        recordTime.setOutlineColor(Color.black);
        recordTime.setOutlineThickness(5);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyTimer.setWaitTime(200);
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // update background map (to play tile animations
    	background.update(coOp);


        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 2) 
        {
            currentMenuItemHovered = 0;
        } 
        else if (currentMenuItemHovered < 0) 
        {
            currentMenuItemHovered = 2;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
        	coopOption.setColor(Color.WHITE);
        	credits.setColor(Color.WHITE);
            playGame.setColor(Color.RED);
            recordTime.setColor(Color.RED);
            pointerLocationX = 170;
            pointerLocationY = 130;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(Color.WHITE);
            credits.setColor(Color.white);
            coopOption.setColor(Color.RED);
            recordTime.setColor(Color.RED);
            pointerLocationX = 170;
            pointerLocationY = 230;
        } else if (currentMenuItemHovered == 2){
        	 coopOption.setColor(Color.WHITE);
        	 playGame.setColor(Color.white);
        	 credits.setColor(Color.RED);
            recordTime.setColor(Color.RED);
             pointerLocationX = 170;
             pointerLocationY = 330;
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) 
            {
                screenCoordinator.setGameState(GameState.LEVEL);
            } 
            else if(menuItemSelected == 1)
            {
            	screenCoordinator.setGameState(GameState.COOP);
            }
            else if (menuItemSelected == 2) {
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        coopOption.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        recordTime.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }
}