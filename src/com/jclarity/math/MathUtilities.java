package com.jclarity.math;

/**
 * Created by IntelliJ IDEA.
 *
 * @author kirk
 * @since 8:10:40 AM Sep 25, 2009
 *        Copyright 2009, Kodewerk Ltd.
 */
public class MathUtilities {

    public static double pi( int numberOfTerms) {
        double numerator = Math.sqrt(2.0d);
        double total = numerator / 2.0d;

        for ( int i = 1; i < numberOfTerms; i++) {
            numerator = Math.sqrt(2.0d + numerator);
            total *= numerator / 2.0d;
        }

        return 2.0d / total;
    }
}
