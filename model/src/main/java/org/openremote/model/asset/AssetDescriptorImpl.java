/*
 * Copyright 2019, OpenRemote Inc.
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
package org.openremote.model.asset;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import org.openremote.model.attribute.AttributeDescriptor;

@JsType(namespace = "Model", name = "AssetDescriptor")
public class AssetDescriptorImpl implements AssetDescriptor {

    protected String name;
    protected String type;
    protected String icon;
    protected String color;
    AttributeDescriptor[] attributeDescriptors;

    @JsonCreator
    @JsConstructor
    public AssetDescriptorImpl(@JsonProperty("name") String name,
                               @JsonProperty("type") String type,
                               @JsonProperty("icon") String icon,
                               @JsonProperty("color") String color,
                               @JsonProperty("attributeDescriptors") AttributeDescriptor[] attributeDescriptors) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.color = color;
        this.attributeDescriptors = attributeDescriptors;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public AttributeDescriptor[] getAttributeDescriptors() {
        return attributeDescriptors;
    }
}
