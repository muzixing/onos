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
package org.onosproject.bgpio.types;

import java.util.Objects;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgpio.exceptions.BGPParseException;
import org.onosproject.bgpio.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

/**
 * Provides Implementation of Med BGP Path Attribute.
 */
public class Med implements BGPValueType {
    private static final Logger log = LoggerFactory.getLogger(Med.class);
    public static final byte MED_TYPE = 4;
    public static final int TYPE_AND_LEN_AS_SHORT = 4;
    public static final int TYPE_AND_LEN_AS_BYTE = 3;
    public static final byte MED_MAX_LEN = 4;

    private int med;

    /**
     * Constructor to initialize med.
     *
     * @param med MULTI_EXIT_DISC value
     */
    public Med(int med) {
        this.med = med;
    }

    /**
     * Returns Med value.
     *
     * @return Med value
     */
    public int med() {
        return this.med;
    }

    /**
     * Reads the channel buffer and returns object of Med.
     *
     * @param cb ChannelBuffer
     * @return object of Med
     * @throws BGPParseException while parsing Med path attribute
     */
    public static Med read(ChannelBuffer cb) throws BGPParseException {
        int med;
        ChannelBuffer tempCb = cb.copy();
        Validation parseFlags = Validation.parseAttributeHeader(cb);

        if ((parseFlags.getLength() > MED_MAX_LEN) || cb.readableBytes() < parseFlags.getLength()) {
            Validation.validateLen(BGPErrorType.UPDATE_MESSAGE_ERROR, BGPErrorType.ATTRIBUTE_LENGTH_ERROR,
                    parseFlags.getLength());
        }
        int len = parseFlags.isShort() ? parseFlags.getLength() + TYPE_AND_LEN_AS_SHORT : parseFlags
                .getLength() + TYPE_AND_LEN_AS_BYTE;
        ChannelBuffer data = tempCb.readBytes(len);
        if (!parseFlags.getFirstBit() && parseFlags.getSecondBit() && parseFlags.getThirdBit()) {
            throw new BGPParseException(BGPErrorType.UPDATE_MESSAGE_ERROR, BGPErrorType.ATTRIBUTE_FLAGS_ERROR, data);
        }

        med = cb.readInt();
        return new Med(med);
    }

    @Override
    public short getType() {
        return MED_TYPE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(med);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Med) {
            Med other = (Med) obj;
            return Objects.equals(med, other.med);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("med", med)
                .toString();
    }

    @Override
    public int write(ChannelBuffer cb) {
        //Not to implement as of now
        return 0;
    }
}