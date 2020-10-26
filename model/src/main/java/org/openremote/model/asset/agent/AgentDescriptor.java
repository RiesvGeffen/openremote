/*
 * Copyright 2020, OpenRemote Inc.
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
package org.openremote.model.asset.agent;

import org.openremote.model.asset.AssetDescriptor;
import org.openremote.model.v2.AttributeDescriptor;

/**
 * Special type of {@link AssetDescriptor} that describes an agent {@link org.openremote.model.asset.Asset}
 */
public class AgentDescriptor<T extends Agent> extends AssetDescriptor<T> {

    protected boolean instanceDiscovery;
    protected boolean instanceImport;
    protected boolean assetDiscovery;
    protected boolean assetImport;

    public AgentDescriptor(String name, String icon, String colour, Class<T> type, AttributeDescriptor<?>[] additionalAttributeDescriptors, boolean instanceDiscovery, boolean instanceImport, boolean assetDiscovery, boolean assetImport) {
        super(name, icon, colour, type, additionalAttributeDescriptors);
        this.instanceDiscovery = instanceDiscovery;
        this.instanceImport = instanceImport;
        this.assetDiscovery = assetDiscovery;
        this.assetImport = assetImport;
    }

    public AgentDescriptor(String name, String icon, String colour, Class<T> type, boolean instanceDiscovery, boolean instanceImport, boolean assetDiscovery, boolean assetImport) {
        super(name, icon, colour, type);
        this.instanceDiscovery = instanceDiscovery;
        this.instanceImport = instanceImport;
        this.assetDiscovery = assetDiscovery;
        this.assetImport = assetImport;
    }

    public boolean isInstanceDiscovery() {
        return instanceDiscovery;
    }

    public boolean isInstanceImport() {
        return instanceImport;
    }

    public boolean isAssetDiscovery() {
        return assetDiscovery;
    }

    public boolean isAssetImport() {
        return assetImport;
    }
}