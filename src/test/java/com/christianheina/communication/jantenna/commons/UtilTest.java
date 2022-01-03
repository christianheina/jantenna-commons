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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.christianheina.communication.jantenna.commons.Constants;
import com.christianheina.communication.jantenna.commons.Util;

/**
 * Unit test for {@link Util}
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings("javadoc")
public class UtilTest {

    @Test
    public void lambdaTest() {
        double freq = 2e9;
        double lambda = Util.calculateLambda(freq);
        Assert.assertTrue(lambda == Constants.VACCUM_SPEED_OF_LIGHT / freq);
        lambda = Util.calculateLambda(freq, 300000);
        Assert.assertTrue(lambda == 300000 / freq);
    }

    @Test
    public void fraunhoferDistanceTest() {
        double freq = 28e9;
        double lambda = Util.calculateLambda(freq);
        double dimension = 0.15;
        double distance = Util.calculateFraunhoferDistance(lambda, dimension);
        Assert.assertTrue(distance == 2 * Math.pow(dimension, 2) / lambda);
    }

    @Test
    public void isNearFieldTest() {
        double freq = 28e9;
        double lambda = Util.calculateLambda(freq);
        double dimension = 0.15;
        boolean isNearField = Util.isNearField(lambda, dimension, 2);
        Assert.assertTrue(isNearField);
        isNearField = Util.isNearField(lambda, dimension, 15);
        Assert.assertFalse(isNearField);
    }

    @Test
    public void isFarFieldTest() {
        double freq = 28e9;
        double lambda = Util.calculateLambda(freq);
        double dimension = 0.15;
        boolean isFarField = Util.isFarField(lambda, dimension, 20);
        Assert.assertTrue(isFarField);
        isFarField = Util.isFarField(lambda, dimension, 1);
        Assert.assertFalse(isFarField);

    }

}
