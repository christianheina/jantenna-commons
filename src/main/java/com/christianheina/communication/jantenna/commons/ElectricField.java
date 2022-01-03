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

/**
 * Electric field (E-field)
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
public enum ElectricField {
    /**
     * Relative gain electric field.
     */
    RELATIVE_GAIN("relative_gain"),
    /**
     * Theta polarized electrical field.
     */
    THETA("theta"),
    /**
     * Phi polarized electrical field.
     */
    PHI("phi"),
    /**
     * Plus 45 polarized electrical field.
     */
    PLUS45("plus45"),
    /**
     * Minus 45 polarized electrical field.
     */
    MINUS45("minus45"),
    /**
     * Ludwig3 vertically polarized electrical field.
     */
    LUDWIG3V("ludwig3V"),
    /**
     * Ludwig3 horizontally polarized electrical field.
     */
    LUDWIG3H("ludwig3H");

    private String name;

    private ElectricField(String name) {
        this.name = name;
    }

    /**
     * Get electrical field name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
}
