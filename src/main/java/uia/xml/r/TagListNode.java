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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

/**
 * Node for TagListInfo.
 *
 * @author ks026400
 *
 */
class TagListNode implements Node {

    private final String name;

    private final List<Object> objs;

    private Map<String, Class<?>> mapping;

    TagListNode(String name, List<Object> objs, TagListInfo info) {
        this.name = name;
        this.objs = objs;
        this.mapping = new TreeMap<String, Class<?>>();
        for (TagListElem elem : info.elems()) {
            this.mapping.put(elem.name(), elem.type());
        }
    }

    @Override
    public List<Object> read(XMLStreamReader xmlReader) throws Exception {
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                Class<?> clz = this.mapping.get(xmlReader.getLocalName());
                if (clz == null) {
                    throw new Exception(xmlReader.getLocalName() + " NOT FOUND in " + this.name);
                }
                Object value = clz.newInstance();
                for (Annotation an : clz.getDeclaredAnnotations()) {
                    Node node = null;
                    if (an instanceof TagInfo) {
                        node = new TagNode(xmlReader.getLocalName(), value);
                    }
                    else if (an instanceof TagListInfo) {
                        node = new TagListNode(xmlReader.getLocalName(), new ArrayList<Object>(), (TagListInfo) an);
                    }
                    else if (an instanceof PropInfo) {
                        node = new PropNode(xmlReader.getLocalName());
                    }
                    else {
                        continue;
                    }
                    try {
                        this.objs.add(node.read(xmlReader));
                    }
                    catch (Exception ex) {
                        throw new Exception(this.name + " failed", ex);
                    }
                }
            }
            else if (event == XMLEvent.END_ELEMENT) {
                break;
            }
        }
        return this.objs;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
