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

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.complex.Complex;

import com.christianheina.communication.jantenna.commons.exceptions.AntennaException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Field Data Model
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public class Field {

    private FieldType fieldType = FieldType.FARFIELD;
    private List<ThetaPhi> thetaPhiList;
    private Map<ElectricField, List<Complex>> electricFieldMap;
    private double frequency;

    private Field(Builder builder) {
        this.fieldType = builder.fieldType;
        this.thetaPhiList = builder.thetaPhiList;
        this.electricFieldMap = builder.electricFieldMap;
        this.frequency = builder.frequency;
    }

    /**
     * Create new builder.
     * 
     * @return new instance of {@link Builder}.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Multiply field by other field.<br>
     * Multiplication is done for each {@link ElectricField}, angle ({@link ThetaPhi}) by angle.
     * 
     * @param toMultiplyWith
     *            field to multiply with.
     * 
     * @return new instance containing multiplied results. Returned instance will have the same {@link FieldType},
     *         electric fields ({@link ElectricField}) and angles ({@link ThetaPhi}). Default frequency,
     *         {@value Builder#DEFAULT_FREQUENCY}, is used unless both fields have the same frequency in which case this
     *         frequency value will be used.
     * 
     * @throws AntennaException
     *             {@link ThetaPhi} angles are not the same, available {@link ElectricField} are not the same or
     *             {@link FieldType} is not the same.
     */
    public Field multiply(Field toMultiplyWith) {
        if (!getThetaPhiList().equals(toMultiplyWith.getThetaPhiList())) {
            throw new AntennaException("Fields needs to have the same angles");
        }
        if (!getAvailableElectricFields().equals(toMultiplyWith.getAvailableElectricFields())) {
            throw new AntennaException("Fields needs to have the same electrical fields");
        }
        if (getFieldType() != toMultiplyWith.getFieldType()) {
            throw new AntennaException("Fields needs to have the same field type");
        }

        Builder builder = newBuilder().setFieldType(getFieldType()).setThetaPhiList(getThetaPhiList());
        if (getFrequency() == toMultiplyWith.getFrequency()) {
            builder.setFreqency(getFrequency());
        }
        for (ElectricField electricField : getAvailableElectricFields()) {
            List<Complex> multipliedElectricFieldData = new ArrayList<>();
            List<Complex> thisElectricFieldData = getElectricField(electricField);
            List<Complex> toMultiplyElectricFieldData = toMultiplyWith.getElectricField(electricField);
            for (int i = 0; i < getElectricField(electricField).size(); i++) {
                multipliedElectricFieldData
                        .add(thisElectricFieldData.get(i).multiply(toMultiplyElectricFieldData.get(i)));
            }
            builder.addElectricField(electricField, multipliedElectricFieldData);
        }
        return builder.build();
    }

    /**
     * Load json data to field.
     * 
     * @param filename
     *            name of file to load
     * 
     * @return new instance of field containing json data.
     * 
     * @throws IOException
     *             if an I/O error occurs opening the file
     */
    public static Field loadJson(String filename) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            return new Gson().fromJson(reader, Field.class);
        }
    }

    /**
     * Save field data to json file.
     * 
     * @param filename
     *            name of saved file
     * 
     * @throws IOException
     *             if the named file exists but is a directory rather than a regular file, does not exist but cannot be
     *             created, or cannot be opened for any other reason
     */
    public void saveJson(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            new Gson().toJson(toJson(), writer);
        }
    }

    /**
     * Convert field data to json.
     * 
     * @return field data as json object
     */
    public JsonElement toJson() {
        return new Gson().toJsonTree(this);
    }

    /**
     * Get field type.
     * 
     * @return {@link FieldType}.
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Get field theta and phi angles.<br>
     * Each value corresponds to complex electric field value with same index in {@link #getElectricField(ElectricField)
     * getElectricField}.
     * 
     * @return {@link List} of {@link ThetaPhi}.
     */
    public List<ThetaPhi> getThetaPhiList() {
        return thetaPhiList;
    }

    /**
     * Get electrical field values.<br>
     * Each value corresponds to angle with same index in {@link #getThetaPhiList() getThetaPhiList}.
     * 
     * @param electricField
     *            the {@link ElectricField} to retrieve.
     * 
     * @return {@link List} of {@link Complex} representing the field.
     */
    public List<Complex> getElectricField(ElectricField electricField) {
        return electricFieldMap.get(electricField);
    }

    /**
     * Get available electrical fields in object instance.
     * 
     * @return {@link Set} of {@link ElectricField}.
     */
    public Set<ElectricField> getAvailableElectricFields() {
        return electricFieldMap.keySet();
    }

    /**
     * Get frequency of this field.
     * 
     * @return frequency.
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * Builder for {@link Field}.
     * 
     * @author Christian Heina
     */
    public static class Builder {

        private static final FieldType DEFAULT_FIELD_TYPE = FieldType.FARFIELD;
        private static final double DEFAULT_FREQUENCY = -1;

        private FieldType fieldType = DEFAULT_FIELD_TYPE;
        private List<ThetaPhi> thetaPhiList = new ArrayList<>();
        private Map<ElectricField, List<Complex>> electricFieldMap = new HashMap<>();
        private double frequency = DEFAULT_FREQUENCY;

        private Builder() {
            /* Hidden Constructor */}

        /**
         * Set frequency.
         * 
         * @param frequency
         *            frequency to set.
         * 
         * @return this instance of {@link Builder}.
         */
        public Builder setFreqency(double frequency) {
            this.frequency = frequency;
            return this;
        }

        /**
         * Set theta and phi list.
         * 
         * @param thetaPhiList
         *            {@link List} of {@link ThetaPhi} to set. <br>
         *            Each value corresponds to complex electric field value added in
         *            {@link #addElectricField(ElectricField, List) addElectricField} with same index in list.
         * 
         * @return this instance of {@link Builder}.
         */
        public Builder setThetaPhiList(List<ThetaPhi> thetaPhiList) {
            this.thetaPhiList = thetaPhiList;
            return this;
        }

        /**
         * Add electrical field.
         * 
         * @param electricField
         *            {@link ElectricField} to add.
         * @param electricFieldData
         *            {link List} of {@link Complex} representing the field values to add.<br>
         *            Each value corresponds to angle set in {@link #setThetaPhiList(List) setThetaPhiList} with same
         *            index.
         * 
         * @return this instance of {@link Builder}.
         */
        public Builder addElectricField(ElectricField electricField, List<Complex> electricFieldData) {
            electricFieldMap.put(electricField, electricFieldData);
            return this;
        }

        /**
         * Set field type.
         * 
         * @param fieldType
         *            {@link FieldType} to set.
         * 
         * @return this instance of {@link Builder}.
         */
        public Builder setFieldType(FieldType fieldType) {
            this.fieldType = fieldType;
            return this;
        }

        /**
         * Build new instance of Field using this builder.
         * 
         * @return new instance of {@link Field}.
         */
        public Field build() {
            return new Field(this);
        }

    }

}
