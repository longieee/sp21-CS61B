package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.Arrays;

/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        // We don't have to check if there's any legal move
        // because checkGameOver already accounted for that case

        // Rotate the table so that the side given behaves as NORTH in this method
        board.setViewingPerspective(side);
        // Top vacancy row: array corresponding to columns
        // [ [row, value, just_merged?], [row, value, just_merged?], ...]
        // with the order is column order
        int[][] top_vacancy_row = new int[board.size()][3];
        int[][] prev_top_vacancy_row;

        // Determine the vacancy cells of the top row (row 3)
        // Column order will always stay the same
        for (int col = 0; col < board.size(); col++) {
            top_vacancy_row[col][0] = board.size()-1;
            top_vacancy_row[col][1] = board.tile(col, board.size()-1) == null ? 0 : board.tile(col, board.size()-1).value();
            top_vacancy_row[col][2] = 0;    // 0 -> was not merge just before; 1 -> was just merged
        }

        // The piece on the top row (row 3) stays put, so we only process from
        // The second row downwards
        for (int row = board.size() - 2; row >= 0 ; row--) {
            // Save the previous top vacancy row for comparison
            prev_top_vacancy_row = Arrays.stream(top_vacancy_row).map(arr -> Arrays.copyOf(arr, arr.length)).toArray(int[][]::new);
            // Process the next row to check for tilt, moving tiles will be done inside this method
            top_vacancy_row = rowTiltProcess(row, top_vacancy_row);
            // Check to see if there's any change
            for (int col = 0; col < board.size(); col++) {
                if (top_vacancy_row[col][1] != prev_top_vacancy_row[col][1] || top_vacancy_row[col][0] != prev_top_vacancy_row[col][0]) {
                    changed = true;
                    break;
                }
            }
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }

        // Rotate the table to original after we've done tilting north
        board.setViewingPerspective(Side.NORTH);

        return changed;
    }

    /** Helper method to process tilt for each row
     * Process the rows from TOP downwards: ... -> 2 -> 1 -> 0
     * This method handles the board as if the direction of tilt is always NORTH
     * (For other direction, the setViewingPerspective method helps to rotate the table
     * so that the tilt is always NORTH)
     * */
    private int[][] rowTiltProcess(int row, int[][] top_vacancy_row) {

        for (int col = 0; col < board.size(); col++) {
            if (board.tile(col, row) != null) {    // Only check if tile is not empty
                // Case move to empty tile
                if (top_vacancy_row[col][1] == 0) {
                    top_vacancy_row[col][1] += board.tile(col, row).value();
                    board.move(col, top_vacancy_row[col][0], board.tile(col, row));
                }
                // Case merge to cell that has not been just merged before
                else if (top_vacancy_row[col][2] == 0 && board.tile(col, row).value() == top_vacancy_row[col][1]) {
                    // Update score from merge only in case merged
                    score += (board.tile(col, row).value() * 2);
                    // Update value of checker list first before move
                    top_vacancy_row[col][1] += board.tile(col, row).value();
                    board.move(col, top_vacancy_row[col][0], board.tile(col, row));
                    // Update just_merged status so next time it won't be merged again
                    top_vacancy_row[col][2] = 1;
                }
                // Case no merge -> move to the tile directly below
                else {
                    // Update top_vacancy_row to reflect movement just made
                    top_vacancy_row[col][1] = board.tile(col, row).value();
                    board.move(col, top_vacancy_row[col][0] - 1, board.tile(col, row));
                    // Top available row for merge should be updated only after move
                    top_vacancy_row[col][0] -= 1;
                    // Reset just_merged status so this tile is ready for merge again
                    top_vacancy_row[col][2] = 0;
                }
            }
        }
        return top_vacancy_row;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int row = 0; row < b.size() ; row++) {
            for (int col = 0; col < b.size() ; col++) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int row = 0; row < b.size() ; row++) {
            for (int col = 0; col < b.size(); col++) {
                if (b.tile(col, row) != null && b.tile(col, row).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        }
        else { //If there is no empty space, then there must be 2 adjacent tiles with the same value
            // Can move left/right?
            for (int row = 0; row < b.size() ; row++) {
                for (int col = 0; col < b.size() - 1 ; col++) {
                    if (b.tile(col, row) != null && b.tile(col, row).value() == b.tile(col + 1, row).value()) {
                        return true;
                    }
                }
            }
            // Can move up/down?
            for (int row = 1; row < b.size(); row++) {
                for (int col = 0; col < b.size(); col++) {
                    if (b.tile(col, row)!= null && b.tile(col, row).value() == b.tile(col, row - 1).value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
