package XXLChess;

public class MoveMotion extends TilesArray {

    public MoveMotion(App app) {
        super(app);
    }

    public void move(Tile visitedTileFrom, Tile activeTileTo) {

        System.out.println("---\nmove");

        this.prettyPrintTile2dArray();

        this.app.tilesarrayObj.tilearrayForceReset();

        if (isTileValidAndChessPiece(visitedTileFrom)) {
            ChessPiece visitedChessPieceFrom = (ChessPiece) visitedTileFrom;

            if (visitedTileFrom instanceof Pawn) {
                handlePawnMovement(visitedChessPieceFrom, activeTileTo);
            } else {
                handleNonPawnMovement(visitedChessPieceFrom, activeTileTo);
            }

            Tile newTileFrom = new Tile(visitedTileFrom.x_idx, visitedTileFrom.y_idx);
            newTileFrom.isPastMoveYellow = true;
            this.app.tilesarrayObj.tile2DArray[visitedTileFrom.y_idx][visitedTileFrom.x_idx] =
                    newTileFrom;
        }
    }

    public void prettyPrintTile2dArray() {
        System.out.println("--------------------------------------------------------");
        for (int y_idx = 0; y_idx < Math.min(this.app.tilesarrayObj.tile2DArray.length,
                App.BOARD_WIDTH); y_idx++) {
            System.out.print("|");
            for (int x_idx = 0; x_idx < Math.min(this.app.tilesarrayObj.tile2DArray[y_idx].length,
                    App.BOARD_WIDTH); x_idx++) {
                Tile aTile = this.app.tilesarrayObj.tile2DArray[y_idx][x_idx];
                if (aTile instanceof ChessPiece) {
                    ChessPiece aChessPiece = (ChessPiece) aTile;
                    System.out.print(" " + aChessPiece.pieceID + " ");
                } else {
                    System.out.print("   ");
                }
                System.out.print("|");
            }
            System.out.println("\n--------------------------------------------------------");
        }
    }

    private boolean isTileValidAndChessPiece(Tile tile) {
        return (tile.x_idx != 99) && (tile.y_idx != 99) && (tile instanceof ChessPiece);
    }

    private void handlePawnMovement(ChessPiece visitedChessPieceFrom, Tile activeTileTo) {
        if (isPawnPromotion(visitedChessPieceFrom, activeTileTo)) {
            ChessPiece newTileTo = createPromotedPawn(visitedChessPieceFrom, activeTileTo);
            movePieceToTile(newTileTo, activeTileTo);
        } else {
            Pawn newTileTo = new Pawn(visitedChessPieceFrom.pieceID, activeTileTo.x_idx,
                    activeTileTo.y_idx, app);
            newTileTo.setPieceMovesOneStep();
            newTileTo.isPastMoveYellow = true;
            movePieceToTile(newTileTo, activeTileTo);
        }
    }

    private boolean isPawnPromotion(ChessPiece visitedChessPieceFrom, Tile activeTileTo) {
        return (activeTileTo.y_idx == 7 && !visitedChessPieceFrom.isHuman)
                || (activeTileTo.y_idx == 5 && visitedChessPieceFrom.isHuman);
    }

    private ChessPiece createPromotedPawn(ChessPiece visitedChessPieceFrom, Tile activeTileTo) {
        char pieceID = Character.isUpperCase(visitedChessPieceFrom.pieceID) ? 'Q' : 'q';
        ChessPiece newTileTo = new Queen(pieceID, activeTileTo.x_idx, activeTileTo.y_idx, app);
        newTileTo.isPastMoveYellow = true;
        return newTileTo;
    }

    private void handleNonPawnMovement(ChessPiece visitedChessPieceFrom, Tile activeTileTo) {
        ChessPiece newTileTo = this.initChessPiece(visitedChessPieceFrom.pieceID,
                activeTileTo.x_idx, activeTileTo.y_idx, app);
        newTileTo.isPastMoveYellow = true;
        movePieceToTile(newTileTo, activeTileTo);
    }

    private void movePieceToTile(ChessPiece newTileTo, Tile activeTileTo) {
        this.app.tilesarrayObj.tile2DArray[activeTileTo.y_idx][activeTileTo.x_idx] = newTileTo;

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
