package XXLChess;

import java.util.*;
import processing.core.PImage;

public abstract class ChessPiece extends Tile {
    protected char pieceID;
    protected String pieceName;
    protected double pieceValue;
    protected String imgPath;
    protected PImage pieceImg;
    protected App app;

    protected boolean isHuman;
    protected boolean isAI;

    protected int[] coordinates;
    protected int[] coordinatesIter;
    protected int i;
    protected int[][] pieceMoves;

    // Get
    public String getPieceName() {
        return this.pieceName;
    }

    // @Override
    // public String toString() {
    //     String ans = "PieceID: " + this.pieceID + " Coor: (" + this.x_idx + ", " + this.y_idx + ")"
    //             + "\n";
    //     return ans;
    // }

    // Constructor
    public ChessPiece(char pieceID, int x_idx, int y_idx, App app) {
        super(x_idx, y_idx);
        this.pieceID = pieceID;
        this.pieceValue = 9999;
        this.app = app;
        this.isHuman = (Character.isLowerCase(pieceID) && app.playerColour.equals("white"))
                || ((Character.isUpperCase(pieceID)) && (app.playerColour.equals("black")));
        this.isAI = !isHuman;

        // Get pieceName and isWhite
        String[] parsePieceID_pieceName_isWhite_imgPath = TilesArray.pieceMap.get(pieceID);
        this.isWhite = Boolean.parseBoolean(parsePieceID_pieceName_isWhite_imgPath[0]);
        this.pieceName = parsePieceID_pieceName_isWhite_imgPath[1];
        this.imgPath = parsePieceID_pieceName_isWhite_imgPath[2];
        this.pieceImg = app.loadImage(imgPath);
        this.pieceImg.resize(App.CELLSIZE, App.CELLSIZE);

        // Initialize ChessClicked
        this.coordinates = new int[2];
        this.coordinatesIter = new int[2];

        this.coordinates[0] = x_idx;
        this.coordinates[1] = y_idx;
        this.coordinatesIter = Arrays.copyOf(this.coordinates, this.coordinates.length);

        this.i = 0;
        this.setPieceMoves();
    }

    /*
     * Draw
     */
    public void drawATile(App app) {
        super.drawATile(app);
        app.image(pieceImg, x, y);
    }

    protected void setPieceMoves() {}

    protected void updateCoordinatesIter() {
        this.coordinatesIter[0] += this.pieceMoves[this.i][0];
        this.coordinatesIter[1] += this.pieceMoves[this.i][1];
    }

    protected boolean checkCoordinatesIterValid() {
        int x_idx = this.coordinatesIter[0];
        int y_idx = this.coordinatesIter[1];

        return ((x_idx >= 0 && x_idx < App.BOARD_WIDTH) && (y_idx >= 0 && y_idx < App.BOARD_WIDTH));
    }

    protected void MovesShowOrReset(String flag) {

        this.coordinatesIter = Arrays.copyOf(this.coordinates, this.coordinates.length);

        for (this.i = 0; this.i < pieceMoves.length; this.i++) {

            this.coordinatesIter = Arrays.copyOf(this.coordinates, this.coordinates.length);
            this.updateCoordinatesIter();

            while (this.checkCoordinatesIterValid()) {

                Tile temp = this.app
                        .getTilesArrayObj().tile2DArray[this.coordinatesIter[1]][this.coordinatesIter[0]];

                // 1. For non-chessPieces Tile only:
                if (!(temp instanceof ChessPiece)) {
                    if (flag == "show") {
                        temp.setAbleMoveBlue();
                    } else if (flag == "AIshow") {
                        // Put the <temp> in to the hashmap
                        ChessMove tempMove = new ChessMove(this, temp, 99);
                        this.app.AIobj.AIAvailableChessMovesList.add(tempMove);
                    } else {
                        System.out.println("Error: MovesShowOrReset() flag is not valid");
                        return;
                    }

                    if ((this instanceof Knight) || this instanceof Camel || this instanceof King
                            || this instanceof Pawn) {
                        break;
                    }

                    this.updateCoordinatesIter();
                } else {
                    // 2. For chesspieces only
                    ChessPiece tempChessPiece = (ChessPiece) temp;

                    // 2.1. For same-color chessPieces only: break
                    if (tempChessPiece.isHuman == this.isHuman) {
                        break;
                    } else {
                        // 2.2. For opp-color chessPieces only:
                        if (flag == "show") {
                            temp.setAbleCaptureLRed();
                            break;
                        } else if (flag == "AIshow") {
                            ChessMove tempMove = new ChessMove(this, temp, 99);
                            this.app.AIobj.AIAvailableChessMovesList.add(tempMove);
                            break;
                        } else {
                            System.out.println("Error: MovesShowOrReset() flag is not valid");
                            return;
                        }
                    }
                }
            }
        }
    }
}


class Rook extends ChessPiece {
    public Rook(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 5.25;
    }

    @Override
    protected void setPieceMoves() {
        this.pieceMoves = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    }

}


class Bishop extends ChessPiece {
    public Bishop(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 3.625;
    }

    @Override
    protected void setPieceMoves() {
        this.pieceMoves = new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    }

}


class Knight extends ChessPiece {
    public Knight(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 2;
    }

    @Override
    protected void setPieceMoves() {
        this.pieceMoves = new int[][] {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1},
                {-2, -1}};
    }

}


class Camel extends ChessPiece {
    public Camel(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 2;
    }

    @Override
    protected void setPieceMoves() {
        this.pieceMoves = new int[][] {{1, 3}, {1, -3}, {-1, 3}, {-1, -3}, {3, 1}, {3, -1}, {-3, 1},
                {-3, -1}};
    }

}


class King extends ChessPiece {
    public King(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
    }

    @Override
    protected void setPieceMoves() {
        this.pieceMoves =
                new int[][] {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    }

}


class Pawn extends ChessPiece {

    protected boolean pawnMoved;

    public Pawn(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 1;
        this.pawnMoved = false;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        this.coordinatesIter = Arrays.copyOf(this.coordinates, this.coordinates.length);

        Boolean isNotBlocked = true;
        this.i = 0;
        while (this.i < pieceMoves.length && isNotBlocked) {

            this.coordinatesIter = Arrays.copyOf(this.coordinates, this.coordinates.length);
            this.updateCoordinatesIter();

            while (this.checkCoordinatesIterValid()) {
                Tile temp = this.app
                        .getTilesArrayObj().tile2DArray[this.coordinatesIter[1]][this.coordinatesIter[0]];

                // 1. For non-chessPieces Tile only:
                if (!(temp instanceof ChessPiece)) {
                    if (flag == "show") {
                        temp.setAbleMoveBlue();
                    } else if (flag == "AIshow") {
                        // Put the <temp> in to the hashmap
                        ChessMove tempMove = new ChessMove(this, temp, 99);
                        this.app.AIobj.AIAvailableChessMovesList.add(tempMove);
                    } else {
                        System.out.println("Error: MovesShowOrReset() flag is not valid");
                        return;
                    }
                    break;
                } else {
                    // 2. For chesspieces only
                    isNotBlocked = false;

                    ChessPiece tempChessPiece = (ChessPiece) temp;

                    // 2.1. For same-color chessPieces only: break
                    if (tempChessPiece.isHuman == this.isHuman) {
                        break;
                    } else {
                        // 2.2. For opp-color chessPieces only:
                        if (flag == "show") {
                            temp.setAbleCaptureLRed();
                            break;
                        } else if (flag == "AIshow") {
                            ChessMove tempMove = new ChessMove(this, temp, 99);
                            this.app.AIobj.AIAvailableChessMovesList.add(tempMove);
                            break;
                        } else {
                            System.out.println("Error: MovesShowOrReset() flag is not valid");
                            return;
                        }
                    }
                }
            }
            this.i++;
        }
    }

    @Override
    protected void setPieceMoves() {
        if (this.y_idx == 1 || this.y_idx == 12) {
            if (this.isHuman) {
                this.pieceMoves = new int[][] {{0, -1}, {0, -2}};
            } else {
                this.pieceMoves = new int[][] {{0, 1}, {0, 2}};
            }
        } else {
            this.setPieceMovesOneStep();
        }

    }

    protected void setPieceMovesOneStep() {
        if (this.isHuman) {
            this.pieceMoves = new int[][] {{0, -1}};
        } else {
            this.pieceMoves = new int[][] {{0, 1}};
        }
    }

}


class Archbishop extends ChessPiece {
    public Archbishop(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 7.5;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        ChessPiece temp1 = new Knight(this.pieceID, this.x_idx, this.y_idx, app);
        ChessPiece temp2 = new Bishop(this.pieceID, this.x_idx, this.y_idx, app);
        temp1.MovesShowOrReset(flag);
        temp2.MovesShowOrReset(flag);
    }

}


class General extends ChessPiece {
    public General(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 5;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        ChessPiece temp1 = new Knight(this.pieceID, this.x_idx, this.y_idx, app);
        ChessPiece temp2 = new King(this.pieceID, this.x_idx, this.y_idx, app);
        temp1.MovesShowOrReset(flag);
        temp2.MovesShowOrReset(flag);
    }

}


class Amazon extends ChessPiece {
    public Amazon(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 12;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        ChessPiece temp1 = new Archbishop(this.pieceID, this.x_idx, this.y_idx, app);
        ChessPiece temp2 = new Rook(this.pieceID, this.x_idx, this.y_idx, app);
        temp1.MovesShowOrReset(flag);
        temp2.MovesShowOrReset(flag);
    }
}


class Chancellor extends ChessPiece {
    public Chancellor(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 8.5;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        ChessPiece temp1 = new Knight(this.pieceID, this.x_idx, this.y_idx, app);
        ChessPiece temp2 = new Rook(this.pieceID, this.x_idx, this.y_idx, app);
        temp1.MovesShowOrReset(flag);
        temp2.MovesShowOrReset(flag);
    }

}


class Queen extends ChessPiece {
    public Queen(char pieceID, int x_idx, int y_idx, App app) {
        super(pieceID, x_idx, y_idx, app);
        this.pieceValue = 9.5;
    }

    @Override
    protected void MovesShowOrReset(String flag) {
        ChessPiece temp1 = new Bishop(this.pieceID, this.x_idx, this.y_idx, app);
        ChessPiece temp2 = new Rook(this.pieceID, this.x_idx, this.y_idx, app);
        temp1.MovesShowOrReset(flag);
        temp2.MovesShowOrReset(flag);
    }

}


