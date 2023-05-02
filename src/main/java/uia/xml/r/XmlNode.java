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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Node for XmlInfo.
 *
 * @author ks026400
 *
 */
class XmlNode implements Node {

    private final String name;

    private final Object owner;

    private final Field f;

    XmlNode(String name) {
        this.name = name;
        this.owner = null;
        this.f = null;
    }

    XmlNode(String name, Object owner, Field f) {
        this.name = name;
        this.owner = owner;
        this.f = f;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        String value = null;
        int d = 1;
        StringBuilder b = new StringBuilder();
        String n = null;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                n = xmlReader.getLocalName();
                if (this.name.equals(n)) {
                    d++;
                }
                b.append("<").append(n);
                for (int a = 0; a < xmlReader.getAttributeCount(); a++) {
                    QName an = xmlReader.getAttributeName(a);
                    String av = xmlReader.getAttributeValue(a);
                    b.append(" ").append(an).append("=\"").append(av).append("\"");
                }
                b.append(">");
            }
            else if (event == XMLEvent.END_ELEMENT) {
                n = xmlReader.getLocalName();
                if (!this.name.equals(n)) {
                    b.append("</").append(n).append(">");
                    continue;
                }

                d--;
                if (d == 0) {
                    break;
                }
            }
            else if (event == XMLEvent.CHARACTERS || event == XMLEvent.CDATA) {
                b.append(xmlReader.getText().trim());
            }
        }
        value = b.toString();
        if (this.owner != null) {
            this.f.set(this.owner, value);
        }
        return value;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
