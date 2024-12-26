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

import java.lang.reflect.Type;
import java.util.List;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.XObjectValue;

/**
 * Node for PropInfo.
 *
 * @author ks026400
 *
 */
class PropNodeM implements Node {

    private final String name;

    private final List<Object> vs;

    private final Type type;

    private XObjectValue parser;

    PropNodeM(String name, List<Object> vs, Type type, XObjectValue parser) {
        this.name = name;
        this.vs = vs;
        this.type = type;
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

            value = this.parser.read(this.type, xmlReader.getText());
            this.vs.add(value);
        }
        return value;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
