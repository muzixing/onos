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
package org.onosproject.bgp;

import org.junit.Test;
import org.onosproject.bgpio.types.attr.BgpLinkAttrProtectionType;

import com.google.common.testing.EqualsTester;

/**
 * Test for MPLS protocol mask attribute.
 */
public class BgpLinkAttrProtectionTypeTest {
    boolean bExtraTraffic = true;
    boolean bUnprotected = true;
    boolean bShared = true;
    boolean bDedOneIstoOne = true;
    boolean bDedOnePlusOne = true;
    boolean bEnhanced = true;

    boolean bExtraTraffic1 = false;
    boolean bUnprotected1 = false;
    boolean bShared1 = false;
    boolean bDedOneIstoOne1 = false;
    boolean bDedOnePlusOne1 = false;
    boolean bEnhanced1 = false;

    private final BgpLinkAttrProtectionType data = BgpLinkAttrProtectionType
            .of(bExtraTraffic, bUnprotected, bShared, bDedOneIstoOne,
                bDedOnePlusOne, bEnhanced);
    private final BgpLinkAttrProtectionType sameAsData = BgpLinkAttrProtectionType
            .of(bExtraTraffic, bUnprotected, bShared, bDedOneIstoOne,
                bDedOnePlusOne, bEnhanced);
    private final BgpLinkAttrProtectionType diffData = BgpLinkAttrProtectionType
            .of(bExtraTraffic1, bUnprotected1, bShared1, bDedOneIstoOne1,
                bDedOnePlusOne1, bEnhanced1);

    @Test
    public void basics() {

        new EqualsTester().addEqualityGroup(data, sameAsData)
        .addEqualityGroup(diffData).testEquals();
    }
}