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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.christianheina.communication.jantenna.commons.exceptions.AntennaException;

/**
 * Unit test for {@link Field}
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings("javadoc")
public class FieldTest {

    private static final String FILENAME = "test.json";

    private Field field;

    @BeforeMethod(onlyForGroups = "useCommonField")
    public void createField() {
        List<Complex> electricField = new ArrayList<>();
        electricField.add(new Complex(1, 0));
        List<ThetaPhi> thetaPhiList = new ArrayList<>();
        thetaPhiList.add(ThetaPhi.fromDegrees(90, 0));
        field = Field.newBuilder().addElectricField(ElectricField.RELATIVE_GAIN, electricField)
                .setFieldType(FieldType.FARFIELD).setFreqency(28 * Math.pow(1, 9)).setThetaPhiList(thetaPhiList)
                .build();
    }

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
        Assert.assertTrue(field.getThetaPhiList().size() == 1);
        Assert.assertTrue(field.getThetaPhiList().get(0).getTheta() == ThetaPhi.fromDegrees(90, 0).getTheta());
        Assert.assertTrue(field.getThetaPhiList().get(0).getPhi() == ThetaPhi.fromDegrees(90, 0).getPhi());
    }

    @Test(groups = "useCommonField")
    public void saveAndLoadJsonTest() throws IOException {
        field.saveJson(FILENAME);
        Field loadedfield = Field.loadJson(FILENAME);
        Files.deleteIfExists(Paths.get(FILENAME));
        Assert.assertTrue(field.getAvailableElectricFields().size() == loadedfield.getAvailableElectricFields().size());
        Assert.assertTrue(field.getElectricField(ElectricField.RELATIVE_GAIN).size() == loadedfield
                .getElectricField(ElectricField.RELATIVE_GAIN).size());
        Assert.assertEquals(field.getElectricField(ElectricField.RELATIVE_GAIN).get(0),
                loadedfield.getElectricField(ElectricField.RELATIVE_GAIN).get(0));
        Assert.assertTrue(field.getFieldType() == loadedfield.getFieldType());
        Assert.assertTrue(field.getFrequency() == loadedfield.getFrequency());
        Assert.assertTrue(field.getThetaPhiList().size() == loadedfield.getThetaPhiList().size());
        Assert.assertTrue(field.getThetaPhiList().get(0).getTheta() == loadedfield.getThetaPhiList().get(0).getTheta());
        Assert.assertTrue(field.getThetaPhiList().get(0).getPhi() == loadedfield.getThetaPhiList().get(0).getPhi());
    }

    @Test(groups = "useCommonField")
    public void multiplyTest() {
        Field multipliedField = field.multiply(field);
        Assert.assertEquals(multipliedField.getAvailableElectricFields(), field.getAvailableElectricFields());
        Assert.assertEquals(multipliedField.getFieldType(), field.getFieldType());
        Assert.assertEquals(multipliedField.getThetaPhiList(), field.getThetaPhiList());
        Assert.assertEquals(multipliedField.getFrequency(), field.getFrequency());
        Assert.assertEquals(multipliedField.getElectricField(ElectricField.RELATIVE_GAIN).size(),
                field.getElectricField(ElectricField.RELATIVE_GAIN).size());
        Assert.assertEquals(multipliedField.getElectricField(ElectricField.RELATIVE_GAIN).get(0), new Complex(1, 0));

        List<Complex> electricField = new ArrayList<>();
        electricField.add(new Complex(0, 0));
        List<ThetaPhi> thetaPhiList = new ArrayList<>();
        thetaPhiList.add(ThetaPhi.fromDegrees(90, 0));
        Field differentField = Field.newBuilder().addElectricField(ElectricField.RELATIVE_GAIN, electricField)
                .setThetaPhiList(thetaPhiList).setFieldType(FieldType.FARFIELD).build();
        multipliedField = field.multiply(differentField);
        Assert.assertEquals(multipliedField.getAvailableElectricFields(), field.getAvailableElectricFields());
        Assert.assertEquals(multipliedField.getFieldType(), field.getFieldType());
        Assert.assertEquals(multipliedField.getThetaPhiList(), field.getThetaPhiList());
        Assert.assertNotEquals(multipliedField.getFrequency(), field.getFrequency());
        Assert.assertEquals(multipliedField.getElectricField(ElectricField.RELATIVE_GAIN).size(),
                field.getElectricField(ElectricField.RELATIVE_GAIN).size());
        Assert.assertEquals(multipliedField.getElectricField(ElectricField.RELATIVE_GAIN).get(0), new Complex(0, 0));
    }

    @Test(expectedExceptions = AntennaException.class, groups = "useCommonField")
    public void multiplyExceptionDifferentThetaPhiTest() {
        Field differentThetaPhiAnglesField = Field.newBuilder().setThetaPhiList(new ArrayList<>()).build();
        field.multiply(differentThetaPhiAnglesField);
    }

    @Test(expectedExceptions = AntennaException.class, groups = "useCommonField")
    public void multiplyExceptionDifferentElectricalFieldTest() {
        List<ThetaPhi> thetaPhiList = new ArrayList<>();
        thetaPhiList.add(ThetaPhi.fromDegrees(90, 0));
        Field differentElectricalFieldField = Field.newBuilder().addElectricField(ElectricField.PHI, new ArrayList<>())
                .setThetaPhiList(thetaPhiList).build();
        field.multiply(differentElectricalFieldField);
    }

    @Test(expectedExceptions = AntennaException.class, groups = "useCommonField")
    public void multiplyExceptionDifferentFieldTypeTest() {
        List<Complex> electricField = new ArrayList<>();
        electricField.add(new Complex(1, 0));
        List<ThetaPhi> thetaPhiList = new ArrayList<>();
        thetaPhiList.add(ThetaPhi.fromDegrees(90, 0));
        Field differentFieldTypeField = Field.newBuilder().addElectricField(ElectricField.RELATIVE_GAIN, electricField)
                .setThetaPhiList(thetaPhiList).setFieldType(null).build();
        field.multiply(differentFieldTypeField);
    }

}
