package XXLChess;

// import org.reflections.Reflections;
// import org.reflections.scanners.Scanners;
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

/*
 * App.java begins
 */

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

    /*
     * Board configuration
     */
    public static final int FPS = 60;

    public String configPath;
    public String layout;
    public String playerColour;
    public double pieceMovementSpeed;
    public int maxMovementTime;
    public int playerSeconds;
    public int playerIncrement;
    public int cpuSeconds;
    public int cpuIncrement;

    /*
     * Timer initialization
     */
    public int playerTimeLeft;
    public int AITimeLeft;
    public Boolean isInPlayerRound;
    public int framesLapsed;

    /*
     * Checkerboard, chess pieces, and tilesarrayObj
     */
    protected TilesArray tilesarrayObj;
    protected AI AIobj;

    /*
     * Load `Config.json`
     */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void readJson() {
        JSONObject config = loadJSONObject("config.json");

        layout = config.getString("layout");
        playerColour = config.getString("player_colour");
        pieceMovementSpeed = config.getDouble("piece_movement_speed");
        maxMovementTime = config.getInt("max_movement_time");

        JSONObject playerTimeControls =
                config.getJSONObject("time_controls").getJSONObject("player");
        playerSeconds = playerTimeControls.getInt("seconds");
        playerIncrement = playerTimeControls.getInt("increment");

        JSONObject cpuTimeControls = config.getJSONObject("time_controls").getJSONObject("cpu");
        cpuSeconds = cpuTimeControls.getInt("seconds");
        cpuIncrement = cpuTimeControls.getInt("increment");

        System.out.println("json Config loaded:");
        System.out.println("Layout: " + layout);
        System.out.println("Player colour: " + playerColour);
        System.out.println("Piece movement speed: " + pieceMovementSpeed);
        System.out.println("Max movement time: " + maxMovementTime);
        System.out.println("Player time controls - seconds: " + playerSeconds + ", increment: "
                + playerIncrement);
        System.out.println(
                "CPU time controls - seconds: " + cpuSeconds + ", increment: " + cpuIncrement);
        System.out.println("---\n");


        if (!((this.playerColour).equals("white") || (this.playerColour).equals("black"))) {
            System.out.println("Error: Invalid player colour");
            // stop the program and print stacktraces
            return;
        }
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and
     * map elements.
     */
    public void setup() {
        this.framesLapsed = 0;
        this.frameRate(FPS);

        // load config
        readJson();

        // playerTimeLeft
        this.playerTimeLeft = this.playerSeconds;
        this.AITimeLeft = this.cpuSeconds;
        if (this.playerColour.equals("white")) {
            this.isInPlayerRound = true;
        } else if (this.playerColour.equals("black")) {
            this.isInPlayerRound = false;
        } else {
            System.out.println("Error: playerColour is neither white nor black");
            return;
        }

        // print out isInPlayerRound
        System.out.println("\n---isInPlayerRound: " + this.isInPlayerRound + "---\n");

        // Load images during setup
        tilesarrayObj = new TilesArray(this);

        // Initialize the AI
        AIobj = new AI(this);

        // Initialize timers drawing
        /*
         * Human is guaranteed to be at the bottom of the board
         */
        Sidebar.drawTimers(this, AITimeLeft, playerTimeLeft);
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {

    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {}

    public Tile getTile(int x, int y) {
        int[] ans = new int[2];
        int x_idx = Math.floorDiv(x, App.CELLSIZE);
        int y_idx = Math.floorDiv(y, App.CELLSIZE);
        ans[0] = x_idx;
        ans[1] = y_idx;

        return this.tilesarrayObj.tile2DArray[y_idx][x_idx];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the coordinates
        // in relativity with, in the JavaDoc, the components
        int x = e.getX();
        int y = e.getY();
        // System.out.println(x + ", " + y);

        /*
         * - Sidebar (outside the boundary): force reset
         * 
         * - Within the boundary
         * 
         * - Is ableToMove green:
         * 
         * move()
         * 
         * - Not selected blue:
         * 
         * - force reset
         * 
         * - Is chessPiece
         * 
         * - click again
         * 
         * - Not is chessPiece
         * 
         * - Do nothing
         */

        if ((x < App.BOARD_WIDTH * App.CELLSIZE) && (y < App.BOARD_WIDTH * App.CELLSIZE)) {
            // Get the tile
            Tile activeTile = this.getTile(x, y);

            // If the tile is able to move blue
            if (activeTile.isAbleMoveBlue) {
                this.tilesarrayObj.move(this.tilesarrayObj.visitedTile, activeTile);

                // this.tilesarrayObj.tilearrayForceReset();

                // if (this.isInPlayerRound)
                //     this.AIobj.AIAction();

                if (this.isInPlayerRound)
                    this.playerTimeLeft += this.playerIncrement;
                else
                    this.AITimeLeft += this.cpuIncrement;

                this.isInPlayerRound = !this.isInPlayerRound;
                /***********************************/
            } else {

                // Force reset
                this.tilesarrayObj.tilearrayForceReset(); // Including resetting visitedTileArray

                // If the tile is a chessPiece
                if (activeTile instanceof ChessPiece) {

                    ChessPiece activeTileChessPiece = (ChessPiece) activeTile;

                    if ((this.isInPlayerRound && activeTileChessPiece.isHuman)
                            || (!this.isInPlayerRound) && (!activeTileChessPiece.isHuman)) {
                        this.tilesarrayObj.tilearraySelected(activeTileChessPiece, this);
                    }
                }
            }
        } else {
            this.tilesarrayObj.tilearrayForceReset(); // Including resetting visitedTileArray
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        // Tick the timer
        if (this.framesLapsed % FPS == 0) {
            if (this.isInPlayerRound)
                this.playerTimeLeft -= 1;
            else
                this.AITimeLeft -= 1;
        }

        Background.refreshFrame(this);

        this.tilesarrayObj.drawCheckerboard(this);

        // Sidebar timer
        Sidebar.drawTimers(this, AITimeLeft, playerTimeLeft);
        this.framesLapsed += 1;
    }

    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

    public TilesArray getTilesArrayObj() {
        return this.tilesarrayObj;
    }
}
