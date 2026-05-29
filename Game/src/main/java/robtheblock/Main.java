package robtheblock;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Rob the Block");
        config.setWindowedMode(960, 960); 
        config.setResizable(true);
        new Lwjgl3Application(new Game(), config);
    }
}

