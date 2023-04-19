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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.nodes.TagNode;

public final class XObjectReader {

    public static <T> T run(Class<T> clz, File file) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return run(clz, fis);
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, String xmlContent) throws Exception {
        InputStream bais = null;
        try {
            bais = new ByteArrayInputStream(xmlContent.getBytes());
            return run(clz, bais);
        }
        finally {
            if (bais != null) {
                bais.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, String xmlContent, String charsetName) throws Exception {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(xmlContent.getBytes(charsetName));
            return run(clz, bais);
        }
        finally {
            if (bais != null) {
                bais.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, InputStream fis) throws Exception {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        // https://rules.sonarsource.com/java/RSPEC-2755
        // prevent xxe
        //xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        //xmlInputFactory.setProperty(XMLConstants.XML_DTD_NS_URI, "");
        //xmlInputFactory.setProperty(XMLConstants.W3C_XML_SCHEMA_NS_URI, "");

        T t = clz.newInstance();
        XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(fis);
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                TagInfo tag = XObjectHelper.getDeclaredAnnotation(clz, TagInfo.class);
                if (tag != null) {
                    new TagNode(xmlReader.getLocalName(), t).read(xmlReader);
                }
                break;
            }
        }
        return t;
    }
}
