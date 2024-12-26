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
package uia.xml.r;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.DateAttrInfo;
import uia.xml.DatePropInfo;
import uia.xml.PropInfo;
import uia.xml.PropListInfo;
import uia.xml.TagInfo;
import uia.xml.TagListInfo;
import uia.xml.XObjectHelper;
import uia.xml.XObjectValue;
import uia.xml.XmlInfo;

/**
 * Node for TagInfo.
 *
 * @author ks026400
 *
 */
public class TagNode implements Node {

    private final String name;

    private final Object obj;

    /**
     * The constructor.
     *
     * @param name The element name.
     * @param obj The value for this element.
     */
    public TagNode(String name, Object obj) {
        this.name = name;
        this.obj = obj;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        Class<?> clz = this.obj.getClass();
        Field[] fs = XObjectHelper.fields(clz);

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
                Object value = attr.parser().newInstance().read(f.getType(), text);
                f.set(this.obj, value);
                continue;
            }

            DateAttrInfo attr4t = XObjectHelper.getDeclaredAnnotation(f, DateAttrInfo.class);
            if (attr4t != null) {
                String name = attr4t.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                String text = xmlReader.getAttributeValue(null, name);
                f.set(this.obj, new SimpleDateFormat(attr4t.format()).parse(text));
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
                try {
                    @SuppressWarnings("unchecked")
                    List<Object> value = (List<Object>) list.clz().newInstance();
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
                }
                catch (Exception ex) {
                    throw new Exception(this.name + "> " + list.name() + " failed", ex);
                }
                continue;
            }

            PropInfo prop = XObjectHelper.getDeclaredAnnotation(f, PropInfo.class);
            if (prop != null) {
                String name = prop.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                if (prop.multi()) {
                    ArrayList<Object> value = new ArrayList<>();
                    f.set(this.obj, value);
                    ParameterizedType pt = (ParameterizedType) f.getGenericType();
                    Type gt = null;
                    if (pt != null) {
                        gt = pt.getActualTypeArguments()[0];
                    }

                    subs.put(name, new PropNodeM(name, value, gt, prop.parser().newInstance()));
                }
                else {
                    subs.put(name, new PropNode(name, this.obj, f, prop.parser().newInstance()));
                }
                continue;
            }

            PropListInfo plist = XObjectHelper.getDeclaredAnnotation(f, PropListInfo.class);
            if (plist != null) {
                @SuppressWarnings("unchecked")
                List<Object> value = (List<Object>) plist.clz().newInstance();
                f.set(this.obj, value);
                subs.put(plist.name(), new PropListNode(plist.name(), value));
                continue;
            }

            DatePropInfo prop4t = XObjectHelper.getDeclaredAnnotation(f, DatePropInfo.class);
            if (prop4t != null) {
                String name = prop4t.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                subs.put(name, new PropNode(name, this.obj, f, new XObjectValue.DateTimeValue(prop4t.format())));
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
                    String name = xmlReader.getLocalName();
                    if (name.equals("rcs_check")) {
                        System.out.println();
                    }
                    Node node = subs.get(name);
                    if (node == null) {
                        throw new Exception(this.name + "> " + name + " NOT FOUND");
                    }
                    node.read(xmlReader);
                }
                else if (event == XMLEvent.CHARACTERS || event == XMLEvent.CDATA) {
                    if (cont != null) {
                        XObjectValue xov = cont.parser().newInstance();
                        contF.set(this.obj, xov.read(contF.getType(), xmlReader.getText()));
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
