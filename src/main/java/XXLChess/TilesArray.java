package XXLChess;

import java.io.*;
import java.util.*;

/*
 * Design: - TilesArray(App app) constructor - drawCheckerBoard(App app) draw method
 */
public class TilesArray {

    protected Tile[][] tile2DArray; // [y_idx][x_idx]
    protected ArrayList<ChessPiece> HumanChessPiecesList;
    protected ArrayList<ChessPiece> AIChessPiecesList;

    protected static HashMap<Character, String[]> pieceMap = new HashMap<>();
    protected App app;

    protected Tile visitedTile;

    private static void initPieceMap() {

        pieceMap.put('A',
                new String[] {"false", "Amazon", "src/main/resources/XXLChess/b-amazon.png"});
        pieceMap.put('H', new String[] {"false", "Archbishop",
                "src/main/resources/XXLChess/b-archbishop.png"});
        pieceMap.put('B',
                new String[] {"false", "Bishop", "src/main/resources/XXLChess/b-bishop.png"});
        pieceMap.put('C',
                new String[] {"false", "Camel", "src/main/resources/XXLChess/b-camel.png"});
        pieceMap.put('E', new String[] {"false", "Chancellor",
                "src/main/resources/XXLChess/b-chancellor.png"});
        pieceMap.put('G',
                new String[] {"false", "General", "src/main/resources/XXLChess/b-general.png"});
        pieceMap.put('K', new String[] {"false", "King", "src/main/resources/XXLChess/b-king.png"});
        pieceMap.put('N',
                new String[] {"false", "Knight", "src/main/resources/XXLChess/b-knight.png"});
        pieceMap.put('P', new String[] {"false", "Pawn", "src/main/resources/XXLChess/b-pawn.png"});
        pieceMap.put('Q',
                new String[] {"false", "Queen", "src/main/resources/XXLChess/b-queen.png"});
        pieceMap.put('R', new String[] {"false", "Rook", "src/main/resources/XXLChess/b-rook.png"});

        pieceMap.put('a',
                new String[] {"true", "Amazon", "src/main/resources/XXLChess/w-amazon.png"});
        pieceMap.put('h', new String[] {"true", "Archbishop",
                "src/main/resources/XXLChess/w-archbishop.png"});
        pieceMap.put('b',
                new String[] {"true", "Bishop", "src/main/resources/XXLChess/w-bishop.png"});
        pieceMap.put('c',
                new String[] {"true", "Camel", "src/main/resources/XXLChess/w-camel.png"});
        pieceMap.put('e', new String[] {"true", "Chancellor",
                "src/main/resources/XXLChess/w-chancellor.png"});
        pieceMap.put('g',
                new String[] {"true", "General", "src/main/resources/XXLChess/w-general.png"});
        pieceMap.put('k', new String[] {"true", "King", "src/main/resources/XXLChess/w-king.png"});
        pieceMap.put('n',
                new String[] {"true", "Knight", "src/main/resources/XXLChess/w-knight.png"});
        pieceMap.put('p', new String[] {"true", "Pawn", "src/main/resources/XXLChess/w-pawn.png"});
        pieceMap.put('q',
                new String[] {"true", "Queen", "src/main/resources/XXLChess/w-queen.png"});
        pieceMap.put('r', new String[] {"true", "Rook", "src/main/resources/XXLChess/w-rook.png"});
    }

    protected ChessPiece initChessPiece(char pieceID, int x_idx, int y_idx, App app) {

        ChessPiece aTile;

        if ((pieceID == 'A') || (pieceID == 'a')) {
            aTile = new Amazon(pieceID, x_idx, y_idx, app);
            // this.tile2DArray[y_idx][x_idx] = aTile;
        } else if ((pieceID == 'H') || (pieceID == 'h')) {
            aTile = new Archbishop(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'B') || (pieceID == 'b')) {
            aTile = new Bishop(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'C') || (pieceID == 'c')) {
            aTile = new Camel(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'E') || (pieceID == 'e')) {
            aTile = new Chancellor(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'G') || (pieceID == 'g')) {
            aTile = new General(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'K') || (pieceID == 'k')) {
            aTile = new King(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'N') || (pieceID == 'n')) {
            aTile = new Knight(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'P') || (pieceID == 'p')) {
            aTile = new Pawn(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'Q') || (pieceID == 'q')) {
            aTile = new Queen(pieceID, x_idx, y_idx, app);

        } else if ((pieceID == 'R') || (pieceID == 'r')) {
            aTile = new Rook(pieceID, x_idx, y_idx, app);

        } else {
            // stop and catch this error
            System.out.println("Error: Invalid pieceID");
            // stop the program and print stacktraces
            Thread.dumpStack();
            aTile = new Pawn(pieceID, x_idx, y_idx, app);
        }

        if (aTile instanceof ChessPiece) {
            ChessPiece aChessPiece = (ChessPiece) aTile;
            if (aTile.isHuman) {
                this.HumanChessPiecesList.add(aChessPiece);
            } else {
                this.AIChessPiecesList.add(aChessPiece);
            }
        }

        return aTile;
        /*
         * If pieceID of aTile is uppercase, then it is a black piece. Otherwise, it is a white
         * 
         * Add aTile to the appropriate ArrayList
         * 
         * Lower case-white
         * 
         * Uppder case-black
         */
    }

    private void readLevel(App app) {

        this.tile2DArray = new Tile[App.BOARD_WIDTH][App.BOARD_WIDTH];

        // Read the `level1.txt` file
        File file = new File("level1.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("Error reading level file: " + e.getMessage());
            return;
        }

        // Loop through each line of the file
        int y_idx = 0;
        while (scanner.hasNextLine() && y_idx < App.BOARD_WIDTH) {

            String line = scanner.nextLine();
            // System.out.println(line.length());

            // Loop through each character in the line
            for (int x_idx = 0; x_idx < App.BOARD_WIDTH; x_idx++) {

                if ((line.length() == 0) || (x_idx >= line.length())
                        || (line.charAt(x_idx) == ' ')) {
                    Tile aTile = new Tile(x_idx, y_idx);
                    tile2DArray[y_idx][x_idx] = aTile;
                } else if (Character.isLetter(line.charAt(x_idx))) {
                    char pieceID = line.charAt(x_idx);
                    this.tile2DArray[y_idx][x_idx] =
                            this.initChessPiece(pieceID, x_idx, y_idx, app);
                } else {
                    Tile aTile = new Tile(x_idx, y_idx);
                    tile2DArray[y_idx][x_idx] = aTile;
                    System.out.printf("Invalid at (%d, %d)", x_idx, y_idx);
                }
            }
            y_idx++;
        }

        scanner.close();
    }

    // Constructor
    public TilesArray(App app) {
        HumanChessPiecesList = new ArrayList<>();
        AIChessPiecesList = new ArrayList<>();

        initPieceMap();
        readLevel(app);
        visitedTile = new Tile(99, 99);
        this.app = app;
    }

    // Draw
    public void drawCheckerboard(App app) {

        for (int y_idx = 0; y_idx < Math.min(tile2DArray.length, App.BOARD_WIDTH); y_idx++) {
            for (int x_idx = 0; x_idx < Math.min(tile2DArray[y_idx].length,
                    App.BOARD_WIDTH); x_idx++) {
                Tile aTile = tile2DArray[y_idx][x_idx];
                aTile.drawATile(app);
            }
        }
    }

    // Mark selected tile and the related ones
    public void tilearraySelected(Tile activeTile, App app) {

        activeTile.isSelectedGreen = true;
        if (activeTile instanceof ChessPiece) {
            ChessPiece activeTileChessPiece = (ChessPiece) activeTile;
            // Select the current active piece
            activeTileChessPiece.MovesShowOrReset("show");

            this.visitedTile = activeTileChessPiece;
        } else {
            System.out.println("Error: clicking\n" + activeTile.toString());
        }
    }

    public void tilearrayForceReset() {
        for (int y_idx = 0; y_idx < Math.min(tile2DArray.length, App.BOARD_WIDTH); y_idx++) {
            for (int x_idx = 0; x_idx < Math.min(tile2DArray[y_idx].length,
                    App.BOARD_WIDTH); x_idx++) {
                Tile aTile = tile2DArray[y_idx][x_idx];
                aTile.isCheckedDRed = false;
                aTile.isPastMoveYellow = false;
                aTile.isAbleCaptureLRed = false;
                aTile.isAbleMoveBlue = false;
                aTile.isSelectedGreen = false;
            }
        }
    }

    // Get private attributes
    public Tile[][] gettile2DArray() {
        return this.tile2DArray;
    }

    public void move(Tile vistedTileFrom, Tile activeTileTo) {

        System.out.println("---\nmove");


        // ChessPiece newTileTo = this.initChessPiece(visitedChessPieceFrom.pieceID,
        // activeTileTo.x_idx, activeTileTo.y_idx, app);
        // newTileTo.isPastMoveYellow = true;
        // this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx] = newTileTo;

        // force reset
        this.app.tilesarrayObj.tilearrayForceReset();

        if ((vistedTileFrom.x_idx != 99) && (vistedTileFrom.y_idx != 99)
                && (vistedTileFrom instanceof ChessPiece)) {

            int y = activeTileTo.y_idx;
            int x = activeTileTo.x_idx;

            System.out.print("1===\n");
            System.out.println("this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]: "
                    + this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]);
            System.out.println("vistedTileFrom: " + vistedTileFrom);
            System.out.println("activeTileTo: " + activeTileTo);
            System.out.println("this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]: "
                    + this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]);

            this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx] = vistedTileFrom;

            System.out.print("2===\n");
            System.out.println("this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]: "
                    + this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]);
            System.out.println("vistedTileFrom: " + vistedTileFrom);
            System.out.println("activeTileTo: " + activeTileTo);
            System.out.println("this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]: "
                    + this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]);

            this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx] =
                    new Tile(vistedTileFrom.x_idx, vistedTileFrom.y_idx);

            System.out.print("3===\n");
            System.out.println("this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]: "
                    + this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]);
            System.out.println("vistedTileFrom: " + vistedTileFrom);
            System.out.println("activeTileTo: " + activeTileTo);
            System.out.println("this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]: "
                    + this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]);

            this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx].y_idx = y;
            this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx].x_idx = x;

            System.out.print("4===\n");
            System.out.println("this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]: "
                    + this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]);
            System.out.println("vistedTileFrom: " + vistedTileFrom);
            System.out.println("activeTileTo: " + activeTileTo);
            System.out.println("this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]: "
                    + this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]);

            // this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx] =
            // new Tile(vistedTileFrom.x_idx, vistedTileFrom.y_idx);

            // System.out.print("5===\n");
            // System.out.println("this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]: "
            // + this.tile2DArray[vistedTileFrom.y_idx][vistedTileFrom.x_idx]);
            // System.out.println("vistedTileFrom: " + vistedTileFrom);
            // System.out.println("activeTileTo: " + activeTileTo);
            // System.out.println("this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]: "
            // + this.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx]);

        }

        this.app.tilesarrayObj.tilearrayForceReset();

        this.printString();
    }

    public void printString() {
        for (int y_idx = 0; y_idx < Math.min(tile2DArray.length, App.BOARD_WIDTH); y_idx++) {
            for (int x_idx = 0; x_idx < Math.min(tile2DArray[y_idx].length,
                    App.BOARD_WIDTH); x_idx++) {
                Tile aTile = tile2DArray[y_idx][x_idx];

                if (aTile instanceof ChessPiece) {
                    // print out (x_idx, y_idx)
                    System.out.printf("Index from tile2DArray (%d, %d): ", x_idx + 1, y_idx + 1);
                    ChessPiece aChessPiece = (ChessPiece) aTile;
                    System.out.printf(" For the piece itself %c: (%d, %d)\n", aChessPiece.pieceID,
                            aChessPiece.x_idx + 1, aChessPiece.y_idx + 1);
                }
            }
        }
    }
}

