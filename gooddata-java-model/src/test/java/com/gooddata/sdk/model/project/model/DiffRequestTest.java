/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.model.project.model;

import org.testng.annotations.Test;

import java.util.Collections;

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DiffRequestTest {

    @Test
    public void testSerialization() throws Exception {
        String valueAsString = OBJECT_MAPPER.writeValueAsString(new DiffRequest(new ProjectModel(emptyList(), emptyList())));
        assertThat(valueAsString, is("{\"diffRequest\":{\"targetModel\":{\"projectModel\":{}}}}"));
    }
}
