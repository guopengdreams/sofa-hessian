/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.hessian.generic.io;

import com.alipay.hessian.generic.model.GenericClass;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

import java.io.IOException;

/**
 * 
 * @author <a href="mailto:caojie.cj@antfin.com">Jie Cao</a>
 * @since 5.3.0
 */
public class GenericClassSerializer extends AbstractSerializer {

    private static GenericClassSerializer instance = new GenericClassSerializer();

    public static GenericClassSerializer getInstance() {
        return instance;
    }

    private GenericClassSerializer() {
    }

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        GenericClass genericClass = (GenericClass) obj;

        if (genericClass == null) {
            out.writeNull();
        } else if (out.addRef(obj)) {
            return;
        } else {
            int ref = out.writeObjectBegin("java.lang.Class");

            if (ref < -1) {
                out.writeString("name");
                out.writeString(genericClass.getClazzName());
                out.writeMapEnd();
            } else {
                if (ref == -1) {
                    out.writeInt(1);
                    out.writeString("name");
                    out.writeObjectBegin("java.lang.Class");
                }

                out.writeString(genericClass.getClazzName());
            }
        }
    }
}
