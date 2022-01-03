/*
 * Copyright 2021 Christian Heina
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.christianheina.communication.jantenna.commons;

/**
 * Utilities for antenna.
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class Util {

    private Util() {
        /* Hidden Constructor */ }

    /**
     * Calculate lambda (wavelength) for certain frequency assuming speed of light in vaccum.
     * 
     * @param frequency
     *            the frequency used to calculate lambda
     * 
     * @return calculated lambda
     */
    public static double calculateLambda(double frequency) {
        return calculateLambda(frequency, Constants.VACCUM_SPEED_OF_LIGHT);
    }

    /**
     * Calculate lambda (wavelength) for certain frequency.
     * 
     * @param frequency
     *            the frequency used to calculate lambda
     * @param speedOfLight
     *            the speed of light used to calculate lambda
     * 
     * @return calculated lambda
     */
    public static double calculateLambda(double frequency, double speedOfLight) {
        return speedOfLight / frequency;
    }

    /**
     * Calculate Fraunhofer distance. This distance is the limit between near and far field.
     * 
     * @param lambda
     *            the lambda (wavelength) used to calculate Fraunhofer distance.
     * @param largestDimension
     *            the largest dimension of radiator (antenna). Usually the diameter.
     * 
     * @return calculated Fraunhofer distance.
     */
    public static double calculateFraunhoferDistance(double lambda, double largestDimension) {
        return 2 * Math.pow(largestDimension, 2) / lambda;
    }

    /**
     * Check if radiator is in near field.
     * 
     * @param lambda
     *            the lambda (wavelength) used to determine radiator Fraunhofer distance.
     * @param largestDimension
     *            the largest dimension of radiator (antenna) used to determine Fraunhofer distance.
     * @param distance
     *            the distance to check.
     * 
     * @return true if distance is in near field, false otherwise. This is determined inclusively comparing distance
     *         against Fraunhofer distance.
     */
    public static boolean isNearField(double lambda, double largestDimension, double distance) {
        return distance <= calculateFraunhoferDistance(lambda, largestDimension);
    }

    /**
     * Check if radiator is in far field.
     * 
     * @param lambda
     *            the lambda (wavelength) used to determine radiator Fraunhofer distance.
     * @param largestDimension
     *            the largest dimension of radiator (antenna) used to determine Fraunhofer distance.
     * @param distance
     *            the distance to check.
     * 
     * @return true if distance is in far field, false otherwise. This is determined inclusively comparing distance
     *         against Fraunhofer distance.
     */
    public static boolean isFarField(double lambda, double largestDimension, double distance) {
        return distance >= calculateFraunhoferDistance(lambda, largestDimension);
    }

}
