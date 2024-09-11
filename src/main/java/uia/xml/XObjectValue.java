/*******************************************************************************
 * Copyright 2023 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.xml;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The value parser interface.
 *
 * @author ks026400
 *
 */
public interface XObjectValue {

    /**
     * Convert a text to an object.
     *
     * @param f The field definition.
     * @param text The text.
     * @return Converted result.
     * @throws Exception Failed to convert.
     */
    public Object read(Field f, String text) throws Exception;

    /**
     * Convert object to a string.
     * @param value The value.
     * @return Converted result.
     * @throws Exception Failed to convert.
     */
    public String write(Object value) throws Exception;

    /**
     * Default implementation of the value parser
     *
     * @author ks026400
     *
     */
    public static final class Simple implements XObjectValue {

        @Override
        public Object read(Field f, String text) {
            return XObjectHelper.read(f, text);
        }

        @Override
        public String write(Object value) {
            return value == null ? "" : "" + value;

        }
    }

    /**
     * Default implementation of the value parser
     *
     * @author ks026400
     *
     */
    public static final class DateTimeValue implements XObjectValue {

        private String fmt;

        public DateTimeValue(String fmt) {
            this.fmt = fmt;
        }

        @Override
        public Object read(Field f, String text) {
            try {
                return new SimpleDateFormat(this.fmt).parse(text);
            }
            catch (ParseException e) {
                return null;
            }
        }

        @Override
        public String write(Object value) {
            return value instanceof Date
                    ? new SimpleDateFormat(this.fmt).format((Date) value)
                    : null;

        }
    }
}
