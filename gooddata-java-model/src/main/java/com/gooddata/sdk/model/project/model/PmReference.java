/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class PmReference {

    private final String target;

    @JsonCreator
    public PmReference(String target) {
        this.target = target;
    }

    @JsonValue
    public String getTarget() {
        return target;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(target);
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
//        final PmReference other = (PmReference) obj;
//        return Objects.equal(this.target, other.target);
//    }

    @Override
    public String toString() {
        return target;
    }
}
