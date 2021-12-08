package glicko;

import java.util.List;

/**
 * Main algorithm for calculating Glicko values.
 * Uses the public domain work of Mark Glickman as reference. Variable names match his documentation,
 * so it's recommended to cross-reference to fully understand what's going on here. Documentation here
 * is going to be ugly, not much way around that.
 */
public class GlickoCalculator {
    // Default values for algorithm, as outlined by Glickman.
    private final static double DEFAULT_RATING = 1500.0;
    private final static double DEFAULT_DEVIATION = 350;
    private final static double DEFAULT_VOLATILITY = 0.06;
    private final static double DEFAULT_TAU = 0.75;
    private final static double MULTIPLIER = 173.7178;
    private final static double CONVERGENCE_TOLERANCE = 0.000001;

    private final double tau; // constraint on volatility over time
    private final double defaultVolatility;

    /**
     * Constructor with default volatility and tau values.
     */
    public GlickoCalculator() {
        tau = DEFAULT_TAU;
        defaultVolatility = DEFAULT_VOLATILITY;
    }

    /**
     * Constructor using custom volatility and tau values instead of defaults.
     *
     * @param initialVolatility desired starting value for volatility
     * @param tau desired starting value for tau
     */
    public GlickoCalculator(double initialVolatility, double tau) {
        this.tau = tau;
        this.defaultVolatility = initialVolatility;
    }

    // All the methods in this chunk are purely supplemental private methods for the main algorithm
    // I don't even know how to begin to document these because I barely undertand the math.

    /**
     * Converts from algorithm scale rating to scale that matches ELO ratings
     *
     * @param rating Glicko2 scale rating
     * @return Glicko scale rating
     */
    public static double convertRatingToOriginalScale(double rating) {
        return ((rating - DEFAULT_RATING) / MULTIPLIER);
    }

    /**
     * Converts from traditionally scaled rating to rating scaled for algorithm
     *
     * @param rating Glicko scale rating
     * @return Glicko2 scale rating
     */
    public static double convertRatingToGlicko2Scale(double rating) {
        return ((rating - DEFAULT_RATING) / MULTIPLIER);
    }

    /**
     * Convert deviation from traditional range to algorithm scale
     *
     * @param deviation Glicko2 scale deviation
     * @return Glicko scale deviation
     */
    public static double convertDeviationToOriginalScale(double deviation) {
        return (deviation * MULTIPLIER);
    }

    /**
     * Converts from traditionally scaled deviation to deviation scaled for algorithm
     *
     * @param deviation Glicko scale deviation
     * @return Glicko2 scale deviation
     */
    public static double convertDeviationToGlicko2Scale(double deviation) {
        return (deviation / MULTIPLIER);
    }

    // Everything here in this chunk is purely to support the main calculator algorithm.

    private double f(double x, double delta, double phi, double v, double a, double tau) {
        return (Math.exp(x) * (Math.pow(delta, 2) - Math.pow(phi, 2) - v - Math.exp(x)) /
                (2.0 * Math.pow(Math.pow(phi, 2) + v + Math.exp(x), 2))) - ((x - a) / Math.pow(tau, 2));
    }

    private double g(double deviation) {
        return 1.0 / (Math.sqrt(1.0 + (3.0 * Math.pow(deviation, 2) / Math.pow(Math.PI, 2))));
    }

    private double E(double rating, double enemyRating, double enemyDeviation) {
        return 1.0 / (1.0 + Math.exp(-1.0 * g(enemyDeviation) * (rating - enemyRating)));
    }

    private double v(GlickoRating rating, List<Result> results) {
        double v = 0.0;

        // This is a lot of math right here, I would highly recommend reading this code alongside Glickman's paper
        // to fully understand what's going on.
        for (Result result : results) {
            v = v + ((Math.pow(g(result.getOpponent(rating).getGlicko2Deviation()), 2)) * E(rating.getGlicko2Rating(),
                    result.getOpponent(rating).getGlicko2Rating(), result.getOpponent(rating).getGlicko2Deviation())
                    * (1.0 - E(rating.getGlicko2Rating(), result.getOpponent(rating).getGlicko2Rating(),
                    result.getOpponent(rating).getGlicko2Deviation())));
        }
        return Math.pow(v, -1);
    }

    private double ratingByOutcome(GlickoRating rating, List<Result> results) {
        double ratingByOutcome = 0;
        for (Result result : results) {
            ratingByOutcome = ratingByOutcome + (g(result.getOpponent(rating).getGlicko2Deviation()) *
                    (result.getScore(rating) - E(rating.getGlicko2Rating(),
                            result.getOpponent(rating).getGlicko2Rating(),
                            result.getOpponent(rating).getGlicko2Deviation())));
        }
        return ratingByOutcome;
    }

    private double delta(GlickoRating rating, List<Result> results) {
        return v(rating, results) * ratingByOutcome(rating, results);
    }

    private double calculateNewDeviation(double phi, double sigma) {
        return Math.sqrt(Math.pow(phi, 2) + Math.pow(sigma, 2));
    }

    // End of the chunk of pure mathematical misery (but hey, maybe you're into that).

    /**
     * Rating calculator based on initial rating values and a series of results. Described in step 5 of Glickman's
     * outline of the rating system, see here: http://www.glicko.net/glicko/glicko2.pdf
     * This is private, but I'm documenting it anyway since it's pretty unintelligble without.
     *
     * @param rating  rating information for a player
     * @param results a set of game results, affects rating.
     */
    private void calculateRating(GlickoRating rating, List<Result> results) {
        // Naming conventions in line with Glickman's paper
        double phi = rating.getGlicko2Deviation();
        double sigma = rating.getVolatility();
        double a = Math.log(Math.pow(sigma, 2));
        double delta = delta(rating, results);
        double v = v(rating, results);

        double A = a;
        double B; // 0.0 by default
        if (Math.pow(delta, 2) > Math.pow(phi, 2) + v) {
            B = Math.log(Math.pow(delta, 2) - Math.pow(phi, 2) - v);
        } else {
            float k = 1;
            B = a - (k * Math.abs(tau));

            while (f(B, delta, phi, v, a, tau) < 0) {
                k++;
                B = a - (k * Math.abs(tau));
            }
        }

        double fA = f(A, delta, phi, v, a, tau);
        double fB = f(B, delta, phi, v, a, tau);

        while (Math.abs(B - A) > CONVERGENCE_TOLERANCE) {
            double C = A + (((A - B) * fA) / (fB - fA));
            double fC = f(C, delta, phi, v, a, tau);

            if (fC * fB < 0) {
                A = B;
                fA = fB;
            } else {
                fA = fA / 2.0;
            }

            B = C;
            fB = fC;
        }

        double newSigma = Math.exp(A / 2.0);

        rating.setTempVolatility(newSigma);

        double phiStar = calculateNewDeviation(phi, newSigma);

        double newPhi = 1.0 / Math.sqrt((1.0 / Math.pow(phiStar, 2)) + (1.0 / v));

        rating.setTempRating(rating.getGlicko2Rating() +
                (Math.pow(newPhi, 2) * ratingByOutcome(rating, results)));
        rating.setTempDeviation(newPhi);
        rating.addToNumResults(results.size());
    }

    /*
    (for now this could actually be private but it being viewable has no negative
    consequences and it might be helpful for expansion later).
    */

    /**
     * Return default glicko rating value
     *
     * @return double, DEFAULT_RATING
     */
    public double getDefaultRating() {
        return DEFAULT_RATING;
    }

    /**
     * Return default volatility value
     *
     * @return double, DEFAULT_VOLATILITY
     */
    public double getDefaultVolatility() {
        return defaultVolatility;
    }

    /**
     * Return default deviation value
     *
     * @return double, DEFAULT_DEVIATION
     */
    public double getDefaultDeviation() {
        return DEFAULT_DEVIATION;
    }
}