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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgpio.exceptions.BGPParseException;
import org.onosproject.bgpio.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

/**
 * Provides Implementation of AsPath mandatory BGP Path Attribute.
 */
public class AsPath implements BGPValueType {
    /**
     * Enum to provide AS types.
     */
    public enum ASTYPE {
        AS_SET(1), AS_SEQUENCE(2), AS_CONFED_SEQUENCE(3), AS_CONFED_SET(4);
        int value;

        /**
         * Assign val with the value as the AS type.
         *
         * @param val AS type
         */
        ASTYPE(int val) {
            value = val;
        }

        /**
         * Returns value of AS type.
         *
         * @return AS type
         */
        public byte getType() {
            return (byte) value;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(AsPath.class);
    public static final byte ASPATH_TYPE = 2;
    public static final byte ASPATH_SET_TYPE = 1;
    public static final byte ASPATH_SEQ_TYPE = 2;
    public static final byte ASNUM_SIZE = 2;
    public static final int TYPE_AND_LEN_AS_SHORT = 4;
    public static final int TYPE_AND_LEN_AS_BYTE = 3;

    private boolean isAsPath = false;
    private List<Short> aspathSet;
    private List<Short> aspathSeq;

    /**
     * Initialize Fields.
     */
    public AsPath() {
        this.aspathSeq = null;
        this.aspathSet = null;
    }

    /**
     * Constructor to initialize parameters.
     *
     * @param aspathSet ASpath Set type
     * @param aspathSeq ASpath Sequence type
     */
    public AsPath(List<Short> aspathSet, List<Short> aspathSeq) {
        this.aspathSeq = aspathSeq;
        this.aspathSet = aspathSet;
        this.isAsPath = true;
    }

    /**
     * Reads from the channel buffer and parses AsPath.
     *
     * @param cb ChannelBuffer
     * @return object of AsPath
     * @throws BGPParseException while parsing AsPath
     */
    public static AsPath read(ChannelBuffer cb) throws BGPParseException {
        List<Short> aspathSet = new ArrayList<>();
        List<Short> aspathSeq = new ArrayList<>();
        ChannelBuffer tempCb = cb.copy();
        Validation validation = Validation.parseAttributeHeader(cb);

        if (cb.readableBytes() < validation.getLength()) {
            Validation.validateLen(BGPErrorType.UPDATE_MESSAGE_ERROR, BGPErrorType.ATTRIBUTE_LENGTH_ERROR,
                    validation.getLength());
        }
        //if fourth bit is set, length is read as short otherwise as byte , len includes type, length and value
        int len = validation.isShort() ? validation.getLength() + TYPE_AND_LEN_AS_SHORT : validation
                .getLength() + TYPE_AND_LEN_AS_BYTE;
        ChannelBuffer data = tempCb.readBytes(len);
        if (validation.getFirstBit() && !validation.getSecondBit() && validation.getThirdBit()) {
            throw new BGPParseException(BGPErrorType.UPDATE_MESSAGE_ERROR, BGPErrorType.ATTRIBUTE_FLAGS_ERROR, data);
        }

        ChannelBuffer tempBuf = cb.readBytes(validation.getLength());
        while (tempBuf.readableBytes() > 0) {
            byte pathSegType = tempBuf.readByte();
            //no of ASes
            byte pathSegLen = tempBuf.readByte();
            int length = pathSegLen * ASNUM_SIZE;
            if (tempBuf.readableBytes() < length) {
                Validation.validateLen(BGPErrorType.UPDATE_MESSAGE_ERROR,
                        BGPErrorType.ATTRIBUTE_LENGTH_ERROR, length);
            }
            ChannelBuffer aspathBuf = tempBuf.readBytes(length);
            while (aspathBuf.readableBytes() > 0) {
                short asNum;
                asNum = aspathBuf.readShort();
                switch (pathSegType) {
                case ASPATH_SET_TYPE:
                    aspathSet.add(asNum);
                    break;
                case ASPATH_SEQ_TYPE:
                    aspathSeq.add(asNum);
                    break;
                default: log.debug("Other type Not Supported:" + pathSegType);
                }
            }
        }
        return new AsPath(aspathSet, aspathSeq);
    }

    @Override
    public short getType() {
        return ASPATH_TYPE;
    }

    /**
     * Returns whether ASpath path attribute is present.
     *
     * @return whether ASpath path attribute is present
     */
    public boolean isaspathSet() {
        return this.isAsPath;
    }

    /**
     * Returns list of ASNum in ASpath Sequence.
     *
     * @return list of ASNum in ASpath Sequence
     */
    public List<Short> asPathSeq() {
        return this.aspathSeq;
    }

    /**
     * Returns list of ASNum in ASpath SET.
     *
     * @return list of ASNum in ASpath SET
     */
    public List<Short> asPathSet() {
        return this.aspathSet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aspathSet, aspathSeq);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AsPath) {
            AsPath other = (AsPath) obj;
            return Objects.equals(aspathSet, other.aspathSet) && Objects.equals(aspathSeq, other.aspathSeq);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .omitNullValues()
                .add("aspathSet", aspathSet)
                .add("aspathSeq", aspathSeq)
                .toString();
    }

    @Override
    public int write(ChannelBuffer cb) {
        //Not required to Implement as of now
        return 0;
    }
}