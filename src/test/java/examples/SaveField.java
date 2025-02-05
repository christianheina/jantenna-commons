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

package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

import com.christianheina.communication.jantenna.commons.ElectricField;
import com.christianheina.communication.jantenna.commons.Field;
import com.christianheina.communication.jantenna.commons.FieldType;
import com.christianheina.communication.jantenna.commons.ThetaPhi;

/**
 * Save field to file example
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings({ "javadoc", "uncommentedmain" })
public class SaveField {

    private static final String FILENAME = "example_save_field.json";

    public static void main(String[] args) {
        List<Complex> relativeGainList = new ArrayList<>();
        relativeGainList.add(Complex.ONE);
        List<ThetaPhi> angleList = new ArrayList<>();
        angleList.add(ThetaPhi.fromDegrees(90, 0));
        Field field = Field.newBuilder().addElectricField(ElectricField.RELATIVE_GAIN, relativeGainList)
                .setThetaPhiList(angleList).setFieldType(FieldType.FARFIELD).setFreqency(28e9).build();
        try {
            field.saveJson(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
