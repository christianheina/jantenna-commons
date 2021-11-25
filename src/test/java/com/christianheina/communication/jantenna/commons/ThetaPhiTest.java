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

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.christianheina.communication.jantenna.commons.ThetaPhi;

/**
 * Unit test for {@link ThetaPhi}
 * 
 * @author Christian Heina
 */
@SuppressWarnings("javadoc")
public class ThetaPhiTest {

    @Test
    public void fromDegreesTest() {
        double theta = 90;
        double phi = 0;
        ThetaPhi angle = ThetaPhi.fromDegrees(theta, phi);
        Assert.assertTrue(Math.toDegrees(angle.getTheta()) == theta);
        Assert.assertTrue(Math.toDegrees(angle.getPhi()) == phi);
    }

    @Test
    public void fromRadiansTest() {
        double theta = Math.PI / 2;
        double phi = 0;
        ThetaPhi angle = ThetaPhi.fromRadians(theta, phi);
        Assert.assertTrue(angle.getTheta() == theta);
        Assert.assertTrue(angle.getPhi() == phi);
    }

    @Test
    public void toStringTest() {
        ThetaPhi thetaPhi = ThetaPhi.fromDegrees(90, 0);
        String temp = thetaPhi.toString();
        Assert.assertEquals(temp,
                "(" + Math.toDegrees(thetaPhi.getTheta()) + ", " + Math.toDegrees(thetaPhi.getPhi()) + ")");
    }

    @Test
    public void equallySpacedSphereTest() {
        List<ThetaPhi> thetaPhiList = ThetaPhi.equallySpacedSphere(1);
        Assert.assertEquals(thetaPhiList.size(), 361 * 181);
        Assert.assertEquals(
                thetaPhiList.stream().filter(distinctByKey(ThetaPhi::getTheta)).collect(Collectors.toList()).size(),
                181);
        Assert.assertEquals(
                thetaPhiList.stream().filter(distinctByKey(ThetaPhi::getPhi)).collect(Collectors.toList()).size(), 361);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
