/*
 * Copyright 2018, OpenRemote Inc.
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
package org.openremote.model.rules.json.predicate;

import org.openremote.model.query.filter.AttributeMetaPredicate;
import org.openremote.model.query.filter.DateTimePredicate;
import org.openremote.model.query.filter.StringPredicate;
import org.openremote.model.query.filter.ValuePredicate;

/**
 * There is an implicit AND condition between each value
 */
// TODO: Introduce value converter functionality and merge with existing AttributePredicate
public class AttributePredicate extends org.openremote.model.query.filter.AttributePredicate {
    public AttributeMetaPredicate[] meta;
    public ValuePredicate lastValue;
    public DateTimePredicate valueTimestamp;
}
