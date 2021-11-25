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

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Weightable Element Data Model
 * 
 * @author Christian Heina
 */
public class WeightableElement extends Element {

    private Complex elementWeight;

    /**
     * Constructor
     * 
     * @param elementLocation
     *            the location of element in wavelengths.
     * @param designFrequency
     *            the designed frequency of the element. This determines the distance between elements in meters.
     * @param elementWeight
     *            complex weight of element.
     */
    public WeightableElement(Vector3D elementLocation, double designFrequency, Complex elementWeight) {
        super(elementLocation, designFrequency);
        this.elementWeight = elementWeight;
    }

    /**
     * Get element weight.
     * 
     * @return element weight.
     */
    public Complex getElementWeight() {
        return elementWeight;
    }

    @Override
    public String toString() {
        return super.toString() + " using weight: " + elementWeight.toString();
    }

}
