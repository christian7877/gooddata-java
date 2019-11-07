/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representation of metadata in the project model.
 *
 * @see ProjectModel
 */
@JsonTypeName("modelMetadata")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelMetadata {
    private final boolean containCA;

    @JsonCreator
    public ModelMetadata(@JsonProperty("containCA") boolean containCA) {
        this.containCA = containCA;
    }

    public boolean isContainCA() {
        return containCA;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(containCA);
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
//        final ModelMetadata other = (ModelMetadata)obj;
//        return this.containCA == other.containCA;
//    }

    @Override
    public String toString() {
        return "modelMetadata{" +
                " containCA=" + containCA +
                "}";
    }
}
