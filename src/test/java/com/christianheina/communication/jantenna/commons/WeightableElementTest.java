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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.christianheina.communication.jantenna.commons.WeightableElement;

/**
 * Unit test for {@link WeightableElement}
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings("javadoc")
public class WeightableElementTest {

    private WeightableElement antenna;

    @BeforeMethod
    public void setup() {
        antenna = new WeightableElement(new Vector3D(0, 0, 0), 28e9, new Complex(1, 0));
    }

    @Test
    public void getElementLocationTest() {
        Vector3D location = antenna.getElementLocation();
        Assert.assertEquals(location, new Vector3D(0, 0, 0));
    }

    @Test
    public void getDesignFrequencyTest() {
        double designFrequency = antenna.getDesignFrequency();
        Assert.assertEquals(designFrequency, 28e9, 1e-8);
    }

    @Test
    public void getWeightTest() {
        Complex weight = antenna.getElementWeight();
        Assert.assertEquals(weight, new Complex(1, 0));
    }

    @Test
    public void toStringTest() {
        String temp = antenna.toString();
        Assert.assertEquals(temp, "(" + antenna.elementLocation.getX() + ", " + antenna.elementLocation.getY() + ", "
                + antenna.elementLocation.getZ() + ")" + " using weight: " + antenna.getElementWeight().toString());
    }

}
