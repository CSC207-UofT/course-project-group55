package glicko;

/**
 * This class represents the outcome of a competitive two player match.
 */
public class Result {
    // Different point values for each potential player outcome.
    private static final double WIN_POINTS = 1.0;
    private static final double DRAW_POINTS = 0.5;
    private static final double LOSS_POINTS = 0.0;

    // Check for draw, and ratings for two different players.
    private boolean isDraw = false;
    private final GlickoRating winner;
    private final GlickoRating loser;

    /**
     * Record result for match between two players with no draw
     *
     * @param winner rating data for winner
     * @param loser  rating data for loser
     */
    public Result(GlickoRating winner, GlickoRating loser) {
        if (!validPlayers(winner, loser)) {
            throw new IllegalArgumentException();
        }
        this.winner = winner;
        this.loser = loser;
    }

    /**
     * Record result for match between two players resulting in a draw
     *
     * @param player1 rating data for first player
     * @param player2 rating data for second player
     * @param isDraw  (must be true for this constructor to work)
     */
    public Result(GlickoRating player1, GlickoRating player2, boolean isDraw) {
        if (!isDraw || !validPlayers(player1, player2)) {
            throw new IllegalArgumentException();
        }

        this.winner = player1;
        this.loser = player2;
        this.isDraw = true;
    }

    /**
     * Basic check that the two competing players aren't the same player.
     *
     * @param player1 rating data for first player
     * @param player2 rating data for second player
     * @return true if players are not the same, false otherwise
     */
    private boolean validPlayers(GlickoRating player1, GlickoRating player2) {
        return (!player1.equals(player2));
    }

    /**
     * Basic check to see whether a player has participated in the match being represented by the result
     *
     * @param player rating data for player
     * @return true if the player participated, false otherwise
     */
    public boolean participated(GlickoRating player) {
        return (winner.equals(player) || loser.equals(player));
    }

    /**
     * Returns the score for the match, used in rating calculation later
     *
     * @param player rating data for player
     * @return 1.0 for a win, 0.5 for a draw, and 0.0 for a loss
     * @throws IllegalArgumentException if given player didn't participate in the match
     */
    public double getScore(GlickoRating player) throws IllegalArgumentException {
        double score;

        if (winner.equals(player)) {
            score = WIN_POINTS;
        } else if (loser.equals(player)) {
            score = LOSS_POINTS;
        } else {
            throw new IllegalArgumentException("Player did not participate in match");
        }

        if (isDraw) {
            score = DRAW_POINTS;
        }

        return score;
    }

    /**
     * For a given player, returns the opponent in the match this result represents
     *
     * @param player rating data for player
     * @return opponent
     */
    public GlickoRating getOpponent(GlickoRating player) {
        GlickoRating opponent;

        if (winner.equals(player)) {
            opponent = loser;
        } else if (loser.equals(player)) {
            opponent = winner;
        } else {
            throw new IllegalArgumentException("Player did not participate in match.");
        }

        return opponent;
    }

    /**
     * Return the winner in the game result
     * In the case of isDraw=true, returns player1
     *
     * @return GlickoRating, winner
     */
    public GlickoRating getWinner() {
        return this.winner;
    }

    /**
     * Return the loser in the game result
     * In the case of isDraw=true, returns player2
     *
     * @return GlickoRating, loser
     */
    public GlickoRating getLoser() {
        return this.loser;
    }
}