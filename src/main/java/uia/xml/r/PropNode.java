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

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.XObjectValue;

/**
 * Node for PropInfo.
 *
 * @author ks026400
 *
 */
class PropNode implements Node {

    private final String name;

    private final Object owner;

    private final Field f;

    private XObjectValue parser;

    PropNode(String name) {
        this.name = name;
        this.owner = null;
        this.f = null;
        this.parser = new XObjectValue.Simple();
    }

    PropNode(String name, Object owner, Field f, XObjectValue parser) {
        this.name = name;
        this.owner = owner;
        this.f = f;
        this.parser = parser;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        Object value = null;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.END_ELEMENT) {
                break;
            }

            value = this.parser.read(this.f, xmlReader.getText());
            if (this.owner != null) {
                this.f.set(this.owner, value);
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
