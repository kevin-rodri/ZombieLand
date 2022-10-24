package Engine;

import java.awt.GridLayout;

import javax.swing.*;

/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	private JFrame gameWindow;
	private GamePanel gamePanel;
	//private GamePanel gamePanel2;
	private GridLayout layout;

	public GameWindow() {
		gameWindow = new JFrame("Game");
		gamePanel = new GamePanel();
		gameWindow.setLayout(new GridLayout(0,1));
	//	gamePanel2 = new GamePanel();
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(false);
		gameWindow.setSize(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
		gamePanel.setupGame();
	}

	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
	}
	 /*public ScreenManager getScreenManager2() {
		return gamePanel2.getScreenManager();
	 }*/
// returns the grid layout	
	public GridLayout getGridLayout()
	{
		return layout;
	}
// sets the grid layout	
	public void setGridLayout()
	{		
		JButton button = new JButton();
		gameWindow.setLayout(new GridLayout(0,2));
		gameWindow.add(button,0);
		gamePanel.setFocusable(true);
		gameWindow.setResizable(false);
		gameWindow.setSize(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
	}
}