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
package org.onosproject.openstackswitching;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Represents the network information given by Neutron.
 */
public final class OpenstackNetwork {

    private String name;
    private String tenantId;
    private String segmentId;
    private String networkType;
    private String id;

    /**
     * Returns the builder object of the OpenstackNetwork class.
     *
     * @return OpenstackNetwork builder object
     */
    public static OpenstackNetwork.Builder builder() {
        return new Builder();
    }

    private OpenstackNetwork(String name, String tenantId, String id, String sid,
                             String type) {
        this.name = checkNotNull(name);
        this.tenantId = checkNotNull(tenantId);
        this.segmentId = checkNotNull(sid);
        this.id = checkNotNull(id);
        this.networkType = checkNotNull(type);
    }

    public String name() {
        return this.name;
    }

    public String tenantId() {
        return this.tenantId;
    }

    public String id() {
        return this.id;
    }

    public String segmentId() {
        return this.segmentId;
    }

    public String networkType() {
        return this.networkType;
    }

    public static final class Builder {
        private String name;
        private String tenantId;
        private String id;
        private String sid;
        private String networkType;

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder tenantId(String tenantId) {
            this.tenantId = tenantId;

            return this;
        }

        public Builder id(String id) {
            this.id = id;

            return this;
        }

        public Builder segmentId(String sid) {
            this.sid = sid;

            return this;
        }

        public Builder networkType(String type) {
            this.networkType = type;

            return this;
        }

        public OpenstackNetwork build() {
            return new OpenstackNetwork(name, tenantId, id, sid, networkType);
        }

    }
}
