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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Convert an object to the XML.
 *
 * @author ks026400
 *
 */
public class XObjectWriter {

    /**
     * Run.
     *
     * @param obj The object.
     * @param charsetName The XML charset.
     * @return The result.
     * @throws Exception Failed to convert.
     */
    public static String run(Object obj, String charsetName) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(baos);
        writer.writeStartDocument();

        TagInfo tag = XObjectHelper.getDeclaredAnnotation(obj.getClass(), TagInfo.class);

        run(obj, tag.name(), writer);
        writer.writeCharacters("\n");
        writer.writeEndDocument();
        return baos.toString(charsetName).replace("></", ">\n</");
    }

    /**
     * Run.
     *
     * @param obj The object.
     * @param os The XML output stream.
     * @throws Exception Failed to convert.
     */
    public static void run(Object obj, OutputStream os) throws Exception {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(os);
        writer.writeStartDocument();
        writer.writeCharacters("\n");

        TagInfo tag = XObjectHelper.getDeclaredAnnotation(obj.getClass(), TagInfo.class);

        run(obj, tag.name(), writer);
        writer.writeCharacters("\n");
        writer.writeEndDocument();
    }

    private static void run(Object obj, String tagName, XMLStreamWriter writer) throws Exception {
        Class<?> clz = obj.getClass();

        writer.writeCharacters("\n");
        writer.writeStartElement(tagName);
        Field[] fs = XObjectHelper.fields(clz);
        // attributes
        for (Field f : fs) {
            f.setAccessible(true);
            AttrInfo attr = XObjectHelper.getDeclaredAnnotation(f, AttrInfo.class);
            if (attr != null) {
                String name = attr.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                try {
                    String text = attr.parser().newInstance().write(f.get(obj));
                    writer.writeAttribute(name, text);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }
            }

            DateAttrInfo attr4t = XObjectHelper.getDeclaredAnnotation(f, DateAttrInfo.class);
            if (attr4t != null) {
                String name = attr4t.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                try {
                    Object dt = f.get(obj);
                    String text = dt != null && dt instanceof Date
                            ? new SimpleDateFormat(attr4t.format()).format((Date) dt)
                            : "";
                    writer.writeAttribute(name, text);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }
            }
        }

        // elements
        boolean ret = false;
        for (Field f : fs) {
            f.setAccessible(true);
            TagInfo tag2 = XObjectHelper.getDeclaredAnnotation(f, TagInfo.class);
            if (tag2 != null) {
                ret = true;
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = tag2.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                run(v, name, writer);
                continue;
            }

            TagListInfo tag3 = XObjectHelper.getDeclaredAnnotation(f, TagListInfo.class);
            if (tag3 != null) {
                ret = true;
                List<?> vs = null;
                try {
                    vs = (List<?>) f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (!tag3.inline()) {
                    String name = tag3.name();
                    if (name.isEmpty()) {
                        name = f.getName();
                    }
                    writer.writeCharacters("\n");
                    writer.writeStartElement(name);
                }
                Map<String, TagListElem> mapping = new TreeMap<String, TagListElem>();
                for (TagListElem elem : tag3.elems()) {
                    mapping.put(elem.type().getName(), elem);
                }
                for (Object w : vs) {
                    TagListElem elem = mapping.get(w.getClass().getName());
                    if (elem != null) {
                        run(w, elem.name(), writer);
                    }
                }
                if (!tag3.inline()) {
                    writer.writeCharacters("\n");
                    writer.writeEndElement();
                }
                continue;
            }

            PropInfo prop = XObjectHelper.getDeclaredAnnotation(f, PropInfo.class);
            if (prop != null) {
                ret = true;
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = prop.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                writer.writeCharacters("\n");
                writer.writeStartElement(name);
                if (v != null) {
                    if (prop.cdata()) {
                        writer.writeCData(prop.parser().newInstance().write(v));
                    }
                    else {
                        writer.writeCharacters(prop.parser().newInstance().write(v));
                    }
                }
                writer.writeEndElement();
                continue;
            }

            DatePropInfo prop4t = XObjectHelper.getDeclaredAnnotation(f, DatePropInfo.class);
            if (prop4t != null) {
                ret = true;
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = prop4t.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                writer.writeCharacters("\n");
                writer.writeStartElement(name);
                if (v != null && v instanceof Date) {
                    writer.writeCharacters(new SimpleDateFormat(prop4t.format()).format((Date) v));
                }
                writer.writeEndElement();
                continue;
            }

            ContentInfo cont = XObjectHelper.getDeclaredAnnotation(f, ContentInfo.class);
            if (cont != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (v != null) {
                    v = cont.parser().newInstance().write(v);
                    if (cont.cdata()) {
                        writer.writeCData("" + v);
                    }
                    else {
                        writer.writeCharacters("" + v);
                    }
                }
                continue;
            }

            XmlInfo xml = XObjectHelper.getDeclaredAnnotation(f, XmlInfo.class);
            if (xml != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = xml.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                writer.writeCharacters("\n");
                writer.writeStartElement(name);
                if (v != null) {
                    writer.writeCData("" + v);
                }
                writer.writeEndElement();
            }
        }

        if (ret) {
            writer.writeCharacters("\n");
        }
        writer.writeEndElement();

    }
}
