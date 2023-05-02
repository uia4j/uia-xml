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
 * Used to define an element with simple content.
 *
 * @author ks026400
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropInfo {

    /**
     * The element name. Default is name of annotated variable.
     *
     * @return The element name.
     */
    String name() default "";

    /**
     * If content is CDATA or not. Default is false.
     *
     * @return True if content is CDATA.
     */
    boolean cdata() default false;

    /**
     * The value parser.
     *
     * @return The value parser.
     */
    Class<? extends XObjectValue> parser() default XObjectValue.Simple.class;
}
