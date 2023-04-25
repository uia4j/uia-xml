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

public final class XObject {

    public static <T> T read(Class<T> clz, File file) throws Exception {
        return XObjectReader.run(clz, file);
    }

    public static <T> T read(Class<T> clz, String xmlContent) throws Exception {
        return XObjectReader.run(clz, xmlContent);
    }

    public static <T> T read(Class<T> clz, String xmlContent, String charsetName) throws Exception {
        return XObjectReader.run(clz, xmlContent, charsetName);
    }

    public static <T> T read(Class<T> clz, InputStream fis) throws Exception {
        return XObjectReader.run(clz, fis);
    }

    public void write(Object obj, OutputStream fos) throws Exception {
        XObjectWriter.run(obj, fos);
    }

    public String write(Object obj, String charsetName) throws Exception {
        return XObjectWriter.run(obj, charsetName);
    }
}
