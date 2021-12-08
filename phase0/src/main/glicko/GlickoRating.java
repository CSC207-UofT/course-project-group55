package glicko;


/**
 * This class holds a user's Glicko-2 rating and other information relevant to it.
 * <p>
 * This includes the average skill value, a standard deviation, and a volatility values that
 * denotes a player's consistency.
 * <p>
 * As per Glickman's outline of the Glicko rating system, this is scaled for better comparison
 * with other ratings systems like the ELO system.
 */
public class GlickoRating {

    private double rating;
    private double deviation;
    private double volatility;
    private int numResults = 0; // number of match results used in calculation

    // temp variables for calculations
    private double tempRating;
    private double tempDeviation;
    private double tempVolatility;

    /**
     * Constructor for GlickoRating that uses default values.
     *
     * @param ratingSystem GlickoCalculator object, used for rating calculations
     */
    public GlickoRating(GlickoCalculator ratingSystem) {
        this.rating = ratingSystem.getDefaultRating();
        this.deviation = ratingSystem.getDefaultDeviation();
        this.volatility = ratingSystem.getDefaultVolatility();
    }

    /**
     * Constructor for GlickoRating that uses user-defined starting values.
     *
     * @param ratingSystem GlickoCalculator object, used for rating calculations
     * @param startRating Preset rating value different from default to instantiate with
     * @param startDeviation Preset deviation value different from default to instantiate with
     * @param startVolatility Preset volatility value different from default to instantiate with
     */
    public GlickoRating(GlickoCalculator ratingSystem, double startRating,
                        double startDeviation, double startVolatility) {
        this.rating = startRating;
        this.deviation = startDeviation;
        this.volatility = startVolatility;
    }

    /**
     * Returns player's average skill value.
     *
     * @return double
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Sets player's average skill value to given parameter value.
     *
     * @param rating double that skill value will be set to
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Returns player's average skill value,
     * scaled for the algorithm instead of for comparison.
     *
     * @return double
     */
    public double getGlicko2Rating() {
        return GlickoCalculator.convertRatingToGlicko2Scale(this.rating);
    }

    /**
     * Set player's average skill value using scaled rating values.
     *
     * @param rating scaled double that skill value will be set to
     */
    public void setGlicko2Rating(double rating) {
        this.rating = GlickoCalculator.convertRatingToOriginalScale(rating);
    }

    /**
     * Return player's current standard deviation, scaled for comparison to other rating systems.
     *
     * @return double
     */
    public double getDeviation() {
        return deviation;
    }

    /**
     * Set player's deviation
     *
     * @param deviation desired value to set player's deviation
     */
    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    /**
     * Returns player's standard deviation, scaled for the algorithm.
     *
     * @return double, glicko2 deviation
     */
    public double getGlicko2Deviation() {
        return GlickoCalculator.convertDeviationToGlicko2Scale(deviation);
    }

    /**
     * Set player's standard deviation using scaled deviation value.
     *
     * @param deviation scaled double that s.d. will be set to
     */
    public void setGlicko2Deviation(double deviation) {
        this.deviation = GlickoCalculator.convertDeviationToOriginalScale(deviation);
    }

    /**
     * Return user's current volatility value.
     *
     * @return double, volatility
     */
    public double getVolatility() {
        return volatility;
    }

    /**
     * Set user's volatility value using given value.
     *
     * @param volatility double, desired volatility value
     */
    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    /**
     * Set user's tempRating value using given value.
     *
     * @param tempRating double, desired tempRating value
     */
    public void setTempRating(double tempRating) {
        this.tempRating = tempRating;
    }

    /**
     * Set user's tempDeviation value using given value.
     *
     * @param tempDeviation double, desired tempDeviation value
     */
    public void setTempDeviation(double tempDeviation) {
        this.tempDeviation = tempDeviation;
    }

    /**
     * Set user's tempVolatility value using given value.
     *
     * @param tempVolatility double, desired tempVolatility value
     */
    public void setTempVolatility(double tempVolatility) {
        this.tempVolatility = tempVolatility;
    }

    /**
     * Return the number of results associated with the current rating.
     *
     * @return int, numResults
     */
    public int getNumResults() {
        return numResults;
    }

    /**
     * Add increment (will always be positive) to number of results.
     *
     * @param increment int to increment results by
     */
    public void addToNumResults(int increment) {
        this.numResults = numResults + increment;
    }

    /**
     * Moves temp values to final places, for end of calculations.
     */
    public void setFinalValues() {
        this.setGlicko2Rating(tempRating);
        this.setGlicko2Deviation(tempDeviation);
        this.setVolatility(tempVolatility);

        this.setTempRating(0);
        this.setTempDeviation(0);
        this.setTempVolatility(0);
    }
}