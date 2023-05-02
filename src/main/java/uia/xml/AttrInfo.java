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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

/**
 * Used to define the attribute of a element.
 *
 * @author ks026400
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AttrInfo {

    /**
     * The attribute name. Default is name of annotated variable.
     *
     * @return The attribute name.
     */
    String name() default "";

    /**
     * The value parser(optional).
     *
     * @return The value parser.
     */
    Class<? extends XObjectValue> parser() default XObjectValue.Simple.class;
}
