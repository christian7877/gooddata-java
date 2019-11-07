/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.gooddata.util.Validate.notNull;

/**
 * Specifies sort order metadata for attributes
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(AttributeSortOrder.ROOT_NODE_NAME)
@JsonPropertyOrder({"label", "direction"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeSortOrder {

    public static final String ROOT_NODE_NAME = "attributeSortOrder";

    private final PmLabelRef label;
    private final SortDirection direction;

    /**
     * @param label reference to label to sort by
     * @param direction sort direction
     */
    public AttributeSortOrder(@JsonProperty("label") PmLabelRef label,
                              @JsonProperty("direction") SortDirection direction) {

        notNull(label, "label");
        notNull(direction, "direction");

        this.label = label;
        this.direction = direction;
    }

    /**
     * Returns reference to label to sort by
     */
    public PmLabelRef getLabel() {
        return label;
    }

    /**
     * Returns the sort direction
     */
    public SortDirection getDirection() {
        return direction;
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(label, direction);
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
//        final AttributeSortOrder other = (AttributeSortOrder) obj;
//        return Objects.equal(this.label, other.label) && Objects.equal(this.direction, other.direction);
//    }

    @Override
    public String toString() {
        return "ORDER BY " + label + " " + direction;
    }

    public static enum SortDirection {
        ASC, DESC
    }

}
