package Engine;

import Utils.Colors;

import java.awt.*;

/*
 * This class holds some constants like window width/height and resource folder locations
 * Tweak these as needed, they shouldn't break anything (keyword shouldn't).
 */

public class Config {
	public static Toolkit t = Toolkit.getDefaultToolkit();
	public static Dimension d = t.getScreenSize();
	
    public static final int FPS = 200;
    public static final String RESOURCES_PATH = "Resources/";
    public static final String MAP_FILES_PATH = "MapFiles/";
    public static final int GAME_WINDOW_WIDTH = d.width;
    public static final int GAME_WINDOW_HEIGHT = d.height;
    public static final Color TRANSPARENT_COLOR = Colors.MAGENTA;

    // prevents Config from being instantiated -- it's my way of making a "static" class like C# has
    private Config() { }
}
