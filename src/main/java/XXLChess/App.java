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
        // Update and draw timers
        int currentTime = millis();
        if (currentTime - lastUpdateTime >= 1000) { // Check if 1 second has passed
            // Update `lastUpdateTime` first, because `drawBackground()` and `drawTimers()` 
            // takes time and may incur inaccuracy
            lastUpdateTime = currentTime;

            
            background(205); // Clear the screen so that the timer doesn't overlap
            Background.drawBackground(this); // Redraw the `background` after it's cleared

            player1Time--; // Decrement player1's timer
            player2Time--; // Decrement player2's timer
            Sidebar.drawTimers(this, player1Time, player2Time);
        }
        /*
         * Another way to track change in time: use an iterator and increment when `draw()` is called
         * Discussion:
         * 1. Advantage: 
         * kick of the timer at exactly the moment when `draw()` is called
         * 2. Disadvantage: subject to the power of the computer; 
         * if the computer runs too slow, the framerate is decreased,
         * thus it can be inaccurate
         */
    }

    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
