package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.io.*;
import java.util.*;

import XXLChess.Background;
import XXLChess.Sidebar;

public class App extends PApplet {

    /*
     * Board visual specification
     */
    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120; // `SideBar` for Time keeping
    public static final int BOARD_WIDTH = 14; // How many pieces are there in a horonzontal line

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE; // Also applies to the `SideBar`

    public static final int FPS = 60;

    public String configPath;

    /*
     * Timer
     */
    // Add timer variables for each player
    private int TIMER_START = 3600;
    private int player1Time = TIMER_START;
    private int player2Time = TIMER_START;
    private int lastUpdateTime;

    private int time = 0;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the
     * player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);

        // Load images during setup

        // PImage spr = loadImage("src/main/resources/XXLChess/"+...);

        // load config
        JSONObject conf = loadJSONObject(new File(this.configPath));

        // Background
        Background.drawBackground(this);
        // Timer are update in `draw()` only when necessary
        Sidebar.drawTimers(this, player1Time, player2Time);

        // Initialize the last update time
        lastUpdateTime = millis();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {

    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        this.fill(189, 186, 190);
        this.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        Background.drawBackground(this);
        // TODO: dynamic drawing
        // This should be a dynamic class because we're passing in stuff

        // Update and draw timers
        if (time % FPS == 0) {
            player1Time -= 1;
            player2Time -= 1;

        }

        Sidebar.drawTimers(this, player1Time, player2Time);
        time += 1;
    }

    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
