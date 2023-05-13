package XXLChess;

public class ChessMove {

    public ChessPiece fromChessPiece;
    public Tile toTile;
    public int score;

    public ChessMove(ChessPiece fromChessPiece, Tile toTile, int score) {
        this.fromChessPiece = fromChessPiece;
        this.toTile = toTile;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%s (%d, %d) -> %s (%d, %d)", fromChessPiece.pieceID,
                fromChessPiece.x_idx+1, fromChessPiece.y_idx+1,
                toTile instanceof ChessPiece ? ((ChessPiece) toTile).pieceID : "Tile",
                toTile.x_idx+1, toTile.y_idx+1);
    }
}
