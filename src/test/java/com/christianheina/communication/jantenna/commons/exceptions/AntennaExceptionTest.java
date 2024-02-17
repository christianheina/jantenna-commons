/*
 * Copyright 2024 Christian Heina
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

package com.christianheina.communication.jantenna.commons.exceptions;

import org.testng.annotations.Test;

/**
 * Unit test for {@link AntennaException}.
 * 
 * @author Christian Heina (developer@christianheina.com)
 */
@SuppressWarnings("javadoc")
public class AntennaExceptionTest {

    @Test(expectedExceptions = AntennaException.class)
    public void PhasedArrayAntennaExceptionWithMessageTest() {
        throw new AntennaException("Test");
    }

    @Test(expectedExceptions = AntennaException.class)
    public void PhasedArrayAntennaExceptionWithThrowableTest() {
        throw new AntennaException(new RuntimeException());
    }

    @Test(expectedExceptions = AntennaException.class)
    public void PhasedArrayAntennaExceptionWithMessageAndThrowableTest() {
        throw new AntennaException("Test", new RuntimeException());
    }

}
