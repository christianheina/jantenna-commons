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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.complex.Complex;

/**
 * Field Data Model
 * 
 * @author Christian Heina
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
     *            the {@link ElectricalField} to retrieve.
     * 
     * @return {@link List} of {@link Complex} representing the field.
     */
    public List<Complex> getElectricField(ElectricField electricField) {
        return electricFieldMap.get(electricField);
    }

    /**
     * Get available electrical fields in object instance.
     * 
     * @return {@link Set} of {@link ElectricalField}.
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

        private FieldType fieldType = FieldType.FARFIELD;
        private List<ThetaPhi> thetaPhiList = new ArrayList<>();
        private Map<ElectricField, List<Complex>> electricFieldMap = new HashMap<>();
        private double frequency = -1;

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
         *            {@link ElectricalField} to add.
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
