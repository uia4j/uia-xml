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
package uia.xml.nodes;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListInfo;
import uia.xml.XObjectHelper;
import uia.xml.XObjectValue;
import uia.xml.XmlInfo;

public class TagNode implements Node {

    private final String name;

    private final Object obj;

    public TagNode(String name, Object obj) {
        this.name = name;
        this.obj = obj;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        Class<?> clz = this.obj.getClass();
        Field[] fs = XObjectHelper.fields(clz, new Field[] {});

        Map<String, Node> subs = new TreeMap<String, Node>();
        ContentInfo cont = null;
        Field contF = null;
        TagListNode inline = null;
        for (Field f : fs) {
            f.setAccessible(true);
            AttrInfo attr = XObjectHelper.getDeclaredAnnotation(f, AttrInfo.class);
            if (attr != null) {
                String name = attr.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                String text = xmlReader.getAttributeValue(null, name);
                Object value = attr.parser().newInstance().read(f, text);
                f.set(this.obj, value);
                continue;
            }

            TagInfo tag = XObjectHelper.getDeclaredAnnotation(f, TagInfo.class);
            if (tag != null) {
                String name = tag.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                Object value = f.getType().newInstance();
                f.set(this.obj, value);
                subs.put(name, new TagNode(name, value));
                continue;
            }

            TagListInfo list = XObjectHelper.getDeclaredAnnotation(f, TagListInfo.class);
            if (list != null) {
                @SuppressWarnings("unchecked")
                List<Object> value = (List<Object>) f.getType().newInstance();
                f.set(this.obj, value);
                if (list.inline()) {
                    inline = new TagListNode(this.name, value, list);
                }
                else {
                    String name = list.name();
                    if (name.isEmpty()) {
                        name = f.getName();
                    }
                    subs.put(name, new TagListNode(name, value, list));
                }
                continue;
            }

            PropInfo prop = XObjectHelper.getDeclaredAnnotation(f, PropInfo.class);
            if (prop != null) {
                String name = prop.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                subs.put(name, new PropNode(name, this.obj, f, prop.parser()));
                continue;
            }

            XmlInfo xml = XObjectHelper.getDeclaredAnnotation(f, XmlInfo.class);
            if (xml != null) {
                String name = xml.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                subs.put(name, new XmlNode(name, this.obj, f));
                continue;
            }

            if (cont == null) {
                cont = XObjectHelper.getDeclaredAnnotation(f, ContentInfo.class);
                if (cont != null) {
                    contF = f;
                }
            }
        }

        if (inline != null) {
            inline.read(xmlReader);
        }
        else {
            while (xmlReader.hasNext()) {
                int event = xmlReader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    Node node = subs.get(xmlReader.getLocalName());
                    if (node == null) {
                        throw new Exception(xmlReader.getLocalName() + " NOT FOUND in " + this.name);
                    }
                    node.read(xmlReader);
                }
                else if (event == XMLEvent.CHARACTERS || event == XMLEvent.CDATA) {
                    if (cont != null) {
                        XObjectValue xov = cont.parser().newInstance();
                        contF.set(this.obj, xov.read(contF, xmlReader.getText()));
                    }
                }
                else if (event == XMLEvent.END_ELEMENT) {
                    break;
                }
            }
        }
        return this.obj;

    }

    @Override
    public String toString() {
        return this.name;
    }

}
