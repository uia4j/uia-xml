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

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The XML Object helper methods.
 *
 * @author ks026400
 *
 */
public final class XObject {

    /**
     * Read XML from a file.
     *
     * @param clz The XML object class type.
     * @param file The XML file.
     * @return The mapping result.
     * @throws Exception Failed to read.
     */
    public static <T> T read(Class<T> clz, File file) throws Exception {
        return XObjectReader.run(clz, file);
    }

    /**
     * Read XML from a string.
     *
     * @param clz The XML object class type.
     * @param xmlContent The XML string.
     * @return The mapping result.
     * @throws Exception Failed to read.
     */
    public static <T> T read(Class<T> clz, String xmlContent) throws Exception {
        return XObjectReader.run(clz, xmlContent);
    }

    /**
     * Read XML from a string.
     *
     * @param clz The XML object class type.
     * @param xmlContent The XML string.
     * @param charsetName The charset.
     * @return The mapping result.
     * @throws Exception Failed to read.
     */
    public static <T> T read(Class<T> clz, String xmlContent, String charsetName) throws Exception {
        return XObjectReader.run(clz, xmlContent, charsetName);
    }

    /**
     * Read XML from an input stream.
     *
     * @param clz The XML object class type.
     * @param is An input stream.
     * @return The mapping result.
     * @throws Exception Failed to read.
     */
    public static <T> T read(Class<T> clz, InputStream is) throws Exception {
        return XObjectReader.run(clz, is);
    }

    /**
     * Write an XML from a object.
     *
     * @param obj The object.
     * @param os A output stream.
     * @throws Exception Failed to write an XML.
     */
    public static void write(Object obj, OutputStream os) throws Exception {
        XObjectWriter.run(obj, os);
    }

    /**
     * Write an XML from a object.
     *
     * @param obj The object.
     * @param charsetName The charset.
     * @return The XML string.
     * @throws Exception Failed to write an XML.
     */
    public static String write(Object obj, String charsetName) throws Exception {
        return XObjectWriter.run(obj, charsetName);
    }

    /**
     * Write an XML from a object.
     *
     * @param obj The object.
     * @param charsetName The charset.
     * @return The XML string.
     * @throws Exception Failed to write an XML.
     */
    public static String writeLine(Object obj, String charsetName) throws Exception {
        return XObjectWriter.line(obj, charsetName);
    }
}
