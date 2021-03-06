/*
 * Copyright 2016, OpenRemote Inc.
 *
 * See the CONTRIBUTORS.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.container.json;

import org.hibernate.HibernateException;
import org.openremote.model.value.ArrayValue;
import org.openremote.model.value.Values;

import java.io.Serializable;

public class ModelArrayValuePersistentType extends PostgreSQLJsonType {

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return value == null ? null : ((ArrayValue) value).toJson();
    }

    @Override
    public ArrayValue assemble(Serializable cached, Object owner) throws HibernateException {
        return cached == null ? null : Values.<ArrayValue>parse(cached.toString()).orElseThrow(() -> new HibernateException("Empty JSON data"));
    }

    @Override
    public Class returnedClass() {
        return ArrayValue.class;
    }

}

