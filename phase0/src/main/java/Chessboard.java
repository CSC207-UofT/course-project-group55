import java.util.ArrayList;
import java.util.Arrays;

public class Chessboard {
    Tile[][] tiles;
    ArrayList<Piece> takenPieces = new ArrayList<>();

    public Chessboard(int width, int height) {
        this.tiles = new Tile[width][height];
        for (int w = 0; w < 8; w++) {
            for (int h = 0; h < 8; h++) {
                tiles[h][w] = new Tile(new EmptyPiece(w,h));
            }
        }
    }

    public Piece takePiece(int x, int y) {
        if (tiles[x][y].isEmpty()) {
            return new EmptyPiece(x, y);
        }

        takenPieces.add(tiles[x][y].getCurrentPiece());
        Piece tempPiece = tiles[x][y].getCurrentPiece();
        tiles[x][y].clearPiece();
        return tempPiece;
    }

    public boolean movePiece(int startx, int starty, int destx, int desty) {
        // no piece on tile
        if (tiles[startx][starty].isEmpty()) {
            return false;
        }

        int[][] movePath = tiles[startx][starty].getCurrentPiece().giveMovePath(destx, desty);
        Piece movingPiece = tiles[startx][starty].getCurrentPiece();

        // no available path even without occlusion
        if (Arrays.deepEquals(movePath, new int[][] {})) {
            return false;
        }

        // Now we run an occlusion check since the potential path may be blocked.
        for (int[] pathTileCoords : movePath) {
            // Checks and fails move if there is a piece in the middle of the path
            if ((pathTileCoords[0] != destx | pathTileCoords[1] != desty) &
                    !(tiles[pathTileCoords[0]][pathTileCoords[1]].getCurrentPiece() instanceof EmptyPiece)) {
                return false;
            }
        }
        // An Allied piece on destination fails the move call.
        if (!(tiles[destx][desty].getCurrentPiece() instanceof EmptyPiece) &
                movingPiece.getColour().equals(tiles[destx][desty].getCurrentPiece().getColour())) {
            return false;
        }

        // No piece on destination
        else if (tiles[destx][desty].getCurrentPiece() instanceof EmptyPiece) {
            tiles[destx][desty].setCurrentPiece(tiles[startx][starty].getCurrentPiece());
            tiles[startx][starty].clearPiece();
            return true;
        }
        // Enemy piece on destination
        else if (!(tiles[destx][desty].getCurrentPiece() instanceof EmptyPiece) &
                !(movingPiece.getColour().equals(tiles[destx][desty].getCurrentPiece().getColour()))) {
            tiles[destx][desty].setCurrentPiece(takePiece(startx,starty));
            return true;
        }
        return true; // if it passes the occlusion check movement is fine.
    }

    // This function is not very performant at all. It works but see if it can be made less ugly.
    public boolean inCheck(int kingx, int kingy) {
        boolean emptyTracker = true;

        for (Tile[] tileRow : tiles) {
            for (Tile tile : tileRow) {
                if (!(tile.getCurrentPiece() instanceof EmptyPiece) &&
                        !(Arrays.deepEquals(tile.getCurrentPiece().giveMovePath(kingx, kingy), new int[][]{}))) {
                    // Now we run an occlusion check since the potential path may be blocked.
                    for (int[] pathTileCoords : tile.getCurrentPiece().giveMovePath(kingx, kingy)) {
                        // Checks and fails move if there is a piece in the middle of the path
                        if ((pathTileCoords[0] != kingx | pathTileCoords[1] != kingy) &
                                !(tiles[pathTileCoords[0]][pathTileCoords[1]].getCurrentPiece() instanceof EmptyPiece)) {
                            emptyTracker = false;
                            break; // path is not viable, so break loop
                        }
                    }
                    if (emptyTracker) { //this will only happen if a path has no occluding pieces, meaning check.
                        return emptyTracker;
                    } else {
                        emptyTracker = true;
                    }
                }
            }
        }
        return false;
    }
}
