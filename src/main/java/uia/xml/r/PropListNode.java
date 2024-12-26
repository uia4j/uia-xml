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

import java.util.List;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Node for TagListInfo.
 *
 * @author ks026400
 *
 */
class PropListNode implements Node {

    private final String name;

    private final List<Object> objs;

    PropListNode(String name, List<Object> objs) {
        this.name = name;
        this.objs = objs;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                String name0 = xmlReader.getLocalName();
                PropNode node = new PropNode(name0);
                this.objs.add(node.read(xmlReader));
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
