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

import org.apache.commons.math3.complex.Complex;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for {@link Field}
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings("javadoc")
public class FieldTest {

    @Test
    public void builderTest() {
        List<Complex> electricField = new ArrayList<>();
        electricField.add(new Complex(1, 0));
        List<ThetaPhi> thetaPhiList = new ArrayList<>();
        thetaPhiList.add(ThetaPhi.fromDegrees(90, 0));
        Field field = Field.newBuilder().addElectricField(ElectricField.RELATIVE_GAIN, electricField)
                .setFieldType(FieldType.FARFIELD).setFreqency(28 * Math.pow(1, 9)).setThetaPhiList(thetaPhiList)
                .build();
        Assert.assertTrue(field.getAvailableElectricFields().size() == 1);
        Assert.assertTrue(field.getElectricField(ElectricField.RELATIVE_GAIN).size() == 1);
        Assert.assertEquals(field.getElectricField(ElectricField.RELATIVE_GAIN).get(0), new Complex(1, 0));
        Assert.assertTrue(field.getFieldType() == FieldType.FARFIELD);
        Assert.assertTrue(field.getFrequency() == 28 * Math.pow(1, 9));
        Assert.assertTrue(field.getAvailableElectricFields().size() == 1);
        Assert.assertTrue(field.getThetaPhiList().size() == 1);
        Assert.assertTrue(field.getThetaPhiList().get(0).getTheta() == ThetaPhi.fromDegrees(90, 0).getTheta());
        Assert.assertTrue(field.getThetaPhiList().get(0).getPhi() == ThetaPhi.fromDegrees(90, 0).getPhi());
    }

}
