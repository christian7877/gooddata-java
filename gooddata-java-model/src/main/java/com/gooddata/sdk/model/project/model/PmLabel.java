/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gooddata.util.GoodDataToStringBuilder;

/**
 * Representation of label in the project model.
 *
 * Label is a concrete representation of the abstract value represented by it's parent {@link PmAttribute attribute}.
 * For example, if attribute is "Employee" then labels may be "Employee ID" or "Employee Name".
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(PmLabel.ROOT_NODE_NAME)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"identifier", "title", "description", "type", "dataType"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmLabel implements HasIdentifier, HasVisualProperties, HasDataType {

    public static final String ROOT_NODE_NAME = "label";

    private final String identifier;

    private final String title;

    private final String description;

    private final String type;

    private final String dataType;

    /**
     * @param identifier identifier unique among all other model objects
     * @param title human-readable short description
     * @param description longer human-readable description
     * @param type type of label (i.e. "GDC.link" for hyperlink labels), can be null for text labels
     * @param dataType data type to which values of this label adhere (f.ex. INT / VARCHAR(128)...)
     */
    @JsonCreator
    public PmLabel(@JsonProperty("identifier") String identifier,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("type") String type,
                   @JsonProperty("dataType") String dataType) {

        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.type = type;
        this.dataType = dataType;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Type of label (i.e. "GDC.link" for hyperlink labels), can be null for text labels
     */
    public String getType() {
        return type;
    }

    @Override
    public String getDataType() {
        return dataType;
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(identifier, title, description, type, dataType);
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
//        final PmLabel other = (PmLabel) obj;
//        return Objects.equal(this.identifier, other.identifier)
//            && Objects.equal(this.title, other.title)
//            && Objects.equal(this.description, other.description)
//            && Objects.equal(this.type, other.type)
//            && Objects.equal(this.dataType, other.dataType);
//    }

    @Override
    public String toString() {
        return new GoodDataToStringBuilder(this).toString();
    }
}
