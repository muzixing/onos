/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.olt;

import org.onlab.packet.VlanId;
import org.onosproject.net.DeviceId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.config.Config;

/**
 * Config object for access device data.
 */
public class AccessDeviceConfig extends Config<DeviceId> {

    private static final String UPLINK = "uplink";
    private static final String VLAN = "vlan";

    /**
     * Gets the access device configuration for this device.
     *
     * @return access device configuration
     */
    public AccessDeviceData getOlt() {
        PortNumber uplink = PortNumber.portNumber(node.path(UPLINK).asText());
        VlanId vlan = VlanId.vlanId(Short.parseShort(node.path(VLAN).asText()));

        return new AccessDeviceData(subject(), uplink, vlan);
    }
}
