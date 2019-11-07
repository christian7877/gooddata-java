/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Reference from attribute grain to another attribute or date. Is implemented as object so we can distinguish between
 * attribute and date reference.
 *
 * Since this class is backing public model REST API there is a little trick here. If there is a date dimension, the public API contains only name of
 * the dimension, not names of the attributes in that dimension. Grain can reference one attribute of this date dimension and it would have been strange
 * if we were referencing something that is not in the model. Let's say I have date dimension called "created". Grain can reference "created.date" attribute,
 * but there is no "created.date" thing in the model JSON. So we reference it only by "created" and add ".date" when transforming to and from JSON
 * using getEnrichedTarget().
 *
 * If the grain is referencing normal attribute, we use full identifier since it's contained in the model.
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PmGrainReference.PmAttributeGrainReference.class, name="attribute"),
        @JsonSubTypes.Type(value = PmGrainReference.PmDateGrainReference.class, name="dateDimension")
})
public abstract class PmGrainReference {
    private final String target;

    @JsonCreator
    protected PmGrainReference(String target) {
        this.target = Objects.requireNonNull(target, "Target can not be null");
    }

    @JsonValue
    public String getTarget() {
        return target;
    }

    @JsonIgnore
    public abstract String getType();

    /**
     * Adds ".date" to date attribute identifier, keeps normal attribute intact.
     */
    @JsonIgnore
    public abstract String getEnrichedTarget();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PmGrainReference that = (PmGrainReference) o;

        return Objects.equals(this.getTarget(), that.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }



    /**
     * Reference from attribute grain to another attribute.
     */
    @JsonTypeName("attribute")
    public static class PmAttributeGrainReference extends PmGrainReference {
        public PmAttributeGrainReference(String target) {
            super(target);
        }

        @Override
        public String getType() {
            return "ATTRIBUTE";
        }

        @Override
        public String getEnrichedTarget() {
            return getTarget();
        }
    }

    /**
     * Reference from attribute grain to date.
     *
     * Target does not contain ".date" at the end
     */
    @JsonTypeName("dateDimension")
    public static class PmDateGrainReference extends PmGrainReference {

        private static final String SUFFIX = ".date";

        protected PmDateGrainReference(String target) {
            super(target);
        }

        @Override
        public String getType() {
            return "DATE";
        }

        @Override
        public String getEnrichedTarget() {
            return getTarget() + SUFFIX;
        }

        /**
         * Creates PmDateGrainReference and strips ".date" from name
         */
        public static PmGrainReference createAndNormalizeName(String name) {
            Objects.requireNonNull(name, "Name can not be null");
            if (name.endsWith(SUFFIX)) {
                return new PmDateGrainReference(name.substring(0, name.length() - SUFFIX.length()));
            } else {
                throw new IllegalArgumentException("Date attribute identifier has to end on \".date\"");
            }
        }
    }
}
