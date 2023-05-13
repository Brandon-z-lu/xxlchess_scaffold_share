package XXLChess;

import java.util.ArrayList;

public class AI extends TilesArray {

    protected ArrayList<ChessMove> AIAvailableChessMovesList;

    public AI(App app) {
        super(app);
    }

    // Find all available moves for the AI
    public void AIAction() {

        AIAvailableChessMovesList = new ArrayList<ChessMove>();

        // Update the available moves list for each chesspiece of the AIChessPiecesList
        for (int i = 0; i < this.AIChessPiecesList.size(); i++) {
            this.AIChessPiecesList.get(i).MovesShowOrReset("AIshow");
        }

        // Showing all the moves
        // Iterate through the AIAvailableChessMovesList
        for (int i = 0; i < this.AIAvailableChessMovesList.size(); i++) {
            System.out.println(this.AIAvailableChessMovesList.get(i));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.chooseAIBestMove();
    }

    public void chooseAIBestMove() {
        // First, we select a chesspiece at random from
        // Random rand = new Random();
        // int randomIndex = rand.nextInt(this.AIAvailableChessMovesList.size());

        this.app.tilesarrayObj.move(this.AIAvailableChessMovesList.get(0).fromChessPiece,
                this.AIAvailableChessMovesList.get(0).toTile);

        // Including resetting visitedTileArray
        this.tilearrayForceReset();

        if (app.isInPlayerRound)
            app.playerTimeLeft += app.playerIncrement;
        else
            app.AITimeLeft += app.cpuIncrement;

        app.isInPlayerRound = !app.isInPlayerRound;

        System.out.println("AI Reset done");
    }
}
