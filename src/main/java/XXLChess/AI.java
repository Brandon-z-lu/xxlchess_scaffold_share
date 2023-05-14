package XXLChess;

import java.util.ArrayList;
import java.util.Random;

public class AI extends TilesArray {

    protected ArrayList<ChessMove> AIAvailableChessMovesList;

    public AI(App app) {
        super(app);
    }

    // Find all available moves for the AI
    public void AIAction() {

        System.out.println("\nAI round--------------------");

        AIAvailableChessMovesList = new ArrayList<>();

        // Update the available moves list for each chesspiece of the AIChessPiecesList

        // Iterate through the tiles2DArray and add the chesspieces to the AIChessPiecesList
        for (int y_idx = 0; y_idx < Math.min(this.app.tilesarrayObj.tile2DArray.length,
                App.BOARD_WIDTH); y_idx++) {
            for (int x_idx = 0; x_idx < Math.min(this.app.tilesarrayObj.tile2DArray[y_idx].length,
                    App.BOARD_WIDTH); x_idx++) {
                Tile aTile = this.app.tilesarrayObj.tile2DArray[y_idx][x_idx];
                if (aTile instanceof ChessPiece) {
                    ChessPiece aChessPiece = (ChessPiece) aTile;
                    if (aChessPiece.isAI) {
                        aChessPiece.MovesShowOrReset("AIshow");
                    }
                }
            }
        }

        // for (ChessPiece chessPiece : this.AIChessPiecesList) {
        //     chessPiece.MovesShowOrReset("AIshow");
        // }

        // this.showAIMoves();

        this.chooseAIBestMove();

        System.out.println("\nAI round-----end----result---");

        this.app.movemotionObj.prettyPrintTile2dArray();
    }

    public void showAIMoves() {
        for (ChessMove chessMove : this.AIAvailableChessMovesList) {
            System.out.println(chessMove);
        }
    }

    public void chooseAIBestMove() {
        // First, we select a chesspiece at random from

        Random rand = new Random();
        int randomIndex = rand.nextInt(this.AIAvailableChessMovesList.size());

        this.app.tilesarrayObj.move(this.AIAvailableChessMovesList.get(
                randomIndex).fromChessPiece,
                this.AIAvailableChessMovesList.get(randomIndex).toTile);

        // Including resetting visitedTileArray
        this.tilearrayForceReset();

        if (app.isInPlayerRound)
            app.playerTimeLeft += app.playerIncrement;
        else
            app.AITimeLeft += app.cpuIncrement;

        app.isInPlayerRound = !app.isInPlayerRound;
    }
}
