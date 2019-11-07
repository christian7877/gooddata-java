/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A simple string-wrapping type for references to labels by identifier
 */
public class PmLabelRef {

    private final String labelId;

    /**
     * @param labelId identifier of the referred label
     */
    @JsonCreator
    public PmLabelRef(String labelId) {
        this.labelId = labelId;
    }

    /**
     * Returns identifier of the referred label
     */
    @JsonValue
    public String getLabelId() {
        return labelId;
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(labelId);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        final PmLabelRef other = (PmLabelRef) obj;
//        return Objects.equal(this.labelId, other.labelId);
//    }

    @Override
    public String toString() {
        return labelId;
    }
}
