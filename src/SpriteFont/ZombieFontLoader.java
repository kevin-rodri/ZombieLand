package SpriteFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class ZombieFontLoader{
    SpriteFont sf;
    public Font zombieFont;


public void zombieFontLoader(SpriteFont sf){
    this.sf = sf;
        InputStream is = getClass().getResourceAsStream("/Font/ZombieMessage-9Y3Wy.ttf");	
        try {
            zombieFont = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
}

}
