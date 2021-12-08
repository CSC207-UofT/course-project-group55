package glicko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents an accumulation of many match results over a rating period.
 */
public class ResultsOverRatingPeriod {
    private final List<Result> results = new ArrayList<>();
    private Set<GlickoRating> participants = new HashSet<>();

    // empty constructor
    public ResultsOverRatingPeriod() {
    }

    /**
     * Constructor where list of participants can be initialized
     *
     * @param participants A set of ratings for different players
     */
    public ResultsOverRatingPeriod(Set<GlickoRating> participants) {
        this.participants = participants;
    }

    /**
     * Add a victory or loss result to the rating period set
     *
     * @param winner rating data for winning player
     * @param loser  rating data for losing player
     */
    public void addResult(GlickoRating winner, GlickoRating loser) {
        Result result = new Result(winner, loser);

        results.add(result);
    }

    /**
     * Add a draw result to the rating period set
     *
     * @param player1 rating data for first player
     * @param player2 rating data for second player
     */
    public void addDraw(GlickoRating player1, GlickoRating player2) {
        Result result = new Result(player1, player2, true);

        results.add(result);
    }

    /**
     * For a given player, return a list of their results.
     *
     * @param player rating data for given player
     * @return list of results containing player
     */
    public List<Result> getResults(GlickoRating player) {
        List<Result> filteredResults = new ArrayList<>();

        for (Result result : results) {
            if (result.participated(player)) {
                filteredResults.add(result);
            }
        }
        return filteredResults;
    }

    /**
     * Get all participants whose results are tracked in the rating period
     *
     * @return the set of all participants who are in the set of results
     */
    public Set<GlickoRating> getParticipants() {
        for (Result result : results) {
            participants.add(result.getWinner());
            participants.add(result.getLoser());
        }
        return participants;
    }

    /**
     * Add participant to the rating period for calculation, regardless of their
     * competition status (if they didn't compete at all, it doesn't matter).
     *
     * @param rating rating data for a player to be added
     */
    public void addParticipants(GlickoRating rating) {
        participants.add(rating);
    }

    /**
     * Clear the set of results
     */
    public void clearResults() {
        results.clear();
    }
}