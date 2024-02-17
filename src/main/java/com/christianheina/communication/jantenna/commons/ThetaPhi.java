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

import java.util.ArrayList;
import java.util.List;

/**
 * Theta Phi Data Model.<br>
 * Theta and phi coordinates are in radians.
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class ThetaPhi {

    private double theta;
    private double phi;

    private ThetaPhi(double theta, double phi) {
        this.theta = theta;
        this.phi = phi;
    }

    /**
     * Get theta angle in radians.
     * 
     * @return theta angle.
     */
    public double getTheta() {
        return theta;
    }

    /**
     * Get phi angle in radians.
     * 
     * @return phi angle.
     */
    public double getPhi() {
        return phi;
    }

    /**
     * Create new instance from radians.
     * 
     * @param theta
     *            the theta value of new instance in radians.
     * @param phi
     *            the phi value of new instance in radians.
     * 
     * @return new instance of {@link ThetaPhi}.
     */
    public static ThetaPhi fromRadians(double theta, double phi) {
        return new ThetaPhi(theta, phi);
    }

    /**
     * Create new instance from degrees.<br>
     * These values will be converted to radians.
     * 
     * @param theta
     *            the theta value of new instance in degrees.
     * @param phi
     *            the phi value of new instance in degrees.
     * 
     * @return new instance of {@link ThetaPhi}.
     */
    public static ThetaPhi fromDegrees(double theta, double phi) {
        return new ThetaPhi(Math.toRadians(theta), Math.toRadians(phi));
    }

    @Override
    public String toString() {
        return "(" + Math.toDegrees(theta) + ", " + Math.toDegrees(phi) + ")";
    }

    /**
     * Create list of equally spaced theta and phi values using provided spacing.
     * 
     * @param spacing
     *            spacing in degrees to use.
     * 
     * @return {@link List} of {@link ThetaPhi}.
     */
    public static List<ThetaPhi> equallySpacedSphere(int spacing) {
        List<ThetaPhi> angleList = new ArrayList<>();
        for (int i = 0; i <= 180; i += spacing) {
            for (int j = -180; j <= 180; j += spacing) {
                angleList.add(ThetaPhi.fromDegrees(i, j));
            }
        }
        return angleList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ThetaPhi)) {
            return false;
        }

        ThetaPhi p = (ThetaPhi) o;
        return Double.compare(getPhi(), p.getPhi()) == 0 && Double.compare(getTheta(), p.getTheta()) == 0;
    }

}
