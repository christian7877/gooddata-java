/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Representation of fact in the project model.
 *
 * @see PmDataset
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("fact")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
@JsonPropertyOrder({"identifier", "title", "description", "folder", "dataType", "type"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmFact implements HasIdentifier, HasVisualProperties, HasFolder, HasDataType {

    private final String identifier;

    private final String title;

    private final String description;

    private final String folder;

    private final String dataType;

    private final Boolean deprecated;

    private final Boolean restricted;

    private final String type;

    private final List<String> flags;

    private static final String DEFAULT_FACT_TYPE  = "numeric";

    // ONLY to satisfy Jackson NON_DEFAULT serialization, do not use!!!
    private PmFact() {
        this(null, null, null, null, null, false, null, null, null);
    }

    /**
     * Create a numeric fact
     * @param identifier identifier unique among all other model objects
     * @param title human-readable short description
     * @param description longer human-readable description
     * @param folder title of the folder this attribute resides in
     * @param dataType type to which values of this label adhere (f.ex. INT, DECIMAL(12,2) ...)
     */
    public PmFact(String identifier, String title, String description, String folder, String dataType) {
        this(identifier, title, description, folder, dataType, false, null, null, null);
    }

    /**
     * Create a numeric fact
     *
     * @param identifier  identifier unique among all other model objects
     * @param title       human-readable short description
     * @param description longer human-readable description
     * @param folder      title of the folder this attribute resides in
     * @param dataType    type to which values of this label adhere (f.ex. INT, DECIMAL(12,2) ...)
     * @param type        fact type (hll or numeric), default value is null
     */
    public PmFact(String identifier, String title, String description, String folder, String dataType, String type) {
        this(identifier, title, description, folder, dataType, false, null, type, null);
    }

    /**
     * @param identifier identifier unique among all other model objects
     * @param title human-readable short description
     * @param description longer human-readable description
     * @param folder title of the folder this attribute resides in
     * @param dataType type to which values of this label adhere (f.ex. INT, DECIMAL(12,2) ...)
     * @param type type to determine numeric or hll. Default null value for numeric fact
     * @param deprecated is given fact deprecated?
     */
    public PmFact(@JsonProperty("identifier") String identifier,
                  @JsonProperty("title") String title,
                  @JsonProperty("description") String description,
                  @JsonProperty("folder") String folder,
                  @JsonProperty("dataType") String dataType,
                  @JsonProperty("deprecated") boolean deprecated,
                  @JsonProperty("restricted") Boolean restricted,
                  @JsonProperty("type") String type,
                  @JsonProperty("flags") List<String> flags) {

        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.folder = folder;
        this.dataType = dataType;
        this.type = type;
        this.deprecated = deprecated;
        this.restricted = restricted;
        this.flags = flags == null ? Collections.<String>emptyList() : Collections.unmodifiableList(flags);
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

    @Override
    public String getFolder() {
        return folder;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public String getType() {
        if (StringUtils.isEmpty(type)) {
            return DEFAULT_FACT_TYPE;
        }
        return type;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public Boolean getRestricted() {
        return restricted;
    }

    public List<String> getFlags() {
        return flags;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(identifier, title, description, folder, dataType, deprecated, restricted, type, flags);
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
//        final PmFact other = (PmFact) obj;
//        return Objects.equal(this.identifier, other.identifier)
//            && Objects.equal(this.title, other.title)
//            && Objects.equal(this.description, other.description)
//            && Objects.equal(this.folder, other.folder)
//            && Objects.equal(this.dataType, other.dataType)
//            && Objects.equal(this.deprecated, other.deprecated)
//                && Objects.equal(this.restricted, other.restricted)
//                && Objects.equal(this.type, other.type)
//            && Objects.equal(this.flags, other.flags);
//    }

    @Override
    public String toString() {
        return String.format("%s {%s}", title, identifier);
    }
}
