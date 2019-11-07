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
import com.gooddata.util.GoodDataToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Representation of attribute in the project model.
 *
 * @see PmDataset
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("attribute")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
@JsonPropertyOrder({"identifier", "title", "description", "dataType", "folder", "labels", "defaultLabel", "sortOrder", "relations"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmAttribute implements HasIdentifier, HasVisualProperties, HasFolder, HasDataType {

    public static final String DEFAULT_ATTRIBUTE_PK_DATATYPE = "INT";
    
    private final String identifier;

    private final String title;

    private final String description;

    private final String folder;

    private final String dataType;

    private final List<PmLabel> labels;

    private final PmLabelRef defaultLabel;

    private final AttributeSortOrder sortOrder;

    private final boolean deprecated;

    private final List<? extends PmGrainReference> grain;

    private final List<String> relations;

    private final List<String> flags;

    // ONLY to satisfy Jackson NON_DEFAULT serialization, do not use!!!
    private PmAttribute() {
        this(null, null, null, null, null, null, null, false, null, null, null, null);
    }

    /**
     * @param identifier  identifier unique among all other model objects
     * @param title       human-readable short description
     * @param description longer human-readable description
     * @param folder      title of the folder this attribute resides in
     * @param labels      list of all attribute labels
     */
    public PmAttribute(String identifier, String title, String description, String folder, List<PmLabel> labels) {
        this(identifier, title, description, folder, labels, null, null, false, null, null, null, null);
    }

    /**
     * @param identifier   identifier unique among all other model objects
     * @param title        human-readable short description
     * @param description  longer human-readable description
     * @param folder       title of the folder this attribute resides in
     * @param labels       list of all attribute labels
     * @param defaultLabel default label for this attribute (may be null)
     * @param sortOrder    sort order settings for this attribute (may be null)
     */
    public PmAttribute(String identifier, String title, String description, String folder, List<PmLabel> labels, PmLabelRef defaultLabel, AttributeSortOrder sortOrder) {
        this(identifier, title, description, folder, labels, defaultLabel, sortOrder, false, null, null, null, null);
    }

    /**
     * @param identifier   identifier unique among all other model objects
     * @param title        human-readable short description
     * @param description  longer human-readable description
     * @param folder       title of the folder this attribute resides in
     * @param labels       list of all attribute labels
     * @param defaultLabel default label for this attribute (may be null)
     * @param sortOrder    sort order settings for this attribute (may be null)
     * @param deprecated   is the attribute deprecated?
     * @param grain list references to all attributes in grain
     * @param relations relations of a computed attribute to other attribute
     * @param flags list of all attribute flags (Eg. restricted flag)
     */
    public PmAttribute(String identifier,
                       String title,
                       String description,
                       String folder,
                       List<PmLabel> labels,
                       PmLabelRef defaultLabel,
                       AttributeSortOrder sortOrder,
                       boolean deprecated,
                       List<? extends PmGrainReference> grain,
                       List<String> relations,
                       List<String> flags){
        this(identifier, title, description, folder, labels, defaultLabel, sortOrder,
                deprecated, grain, relations, flags, null);
    }
    
    /**
     * @param identifier identifier unique among all other model objects
     * @param title human-readable short description
     * @param description longer human-readable description
     * @param folder title of the folder this attribute resides in
     * @param labels list of all attribute labels
     * @param defaultLabel default label for this attribute (may be null)
     * @param sortOrder sort order settings for this attribute (may be null)
     * @param deprecated is the attribute deprecated?
     * @param grain list references to all attributes in grain
     * @param relations relations of a computed attribute to other attribute
     * @param flags list of all attribute flags (Eg. restricted flag)
     * @param dataType is the data type of attribute's primary column
     */
    public PmAttribute(@JsonProperty("identifier") String identifier,
                       @JsonProperty("title") String title,
                       @JsonProperty("description") String description,
                       @JsonProperty("folder") String folder,
                       @JsonProperty("labels") List<PmLabel> labels,
                       @JsonProperty("defaultLabel") PmLabelRef defaultLabel,
                       @JsonProperty("sortOrder") AttributeSortOrder sortOrder,
                       @JsonProperty("deprecated") boolean deprecated,
                       @JsonProperty("grain") List<? extends PmGrainReference> grain,
                       @JsonProperty("relations") List<String> relations,
                       @JsonProperty("flags") List<String> flags,
                       @JsonProperty("dataType") String dataType) {


        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.folder = folder;
        this.labels = labels == null ? Collections.<PmLabel>emptyList() : Collections.unmodifiableList(labels);
        this.defaultLabel = defaultLabel;
        this.sortOrder = sortOrder;
        this.deprecated = deprecated;
        this.grain = grain == null ? Collections.<PmGrainReference>emptyList() : Collections.unmodifiableList(grain);
        this.relations = relations == null ? Collections.<String>emptyList() : Collections.unmodifiableList(relations);
        this.flags = flags == null ? Collections.<String>emptyList() : Collections.unmodifiableList(flags);
        this.dataType = dataType == null || dataType.isEmpty() ? DEFAULT_ATTRIBUTE_PK_DATATYPE : dataType;
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

    public List<PmLabel> getLabels() {
        return labels;
    }

    public PmLabelRef getDefaultLabel() {
        return defaultLabel;
    }

    public String getDataType() {
        return dataType;
    }

    /**
     * Return a <em>possible</em> reference id for this attribute - by matching an id with 3 segments.
     * Note that this is a semi-heuristic that we intend to change as soon as possible.
     *
     * @return the reference id for the attribute
     */
    public String referenceKey() {
        for (PmLabel label: labels) {
            String id = label.getIdentifier();
            if (id.split("\\.").length == 3) {
                return id;
            }
        }

        return null;
    }

    public AttributeSortOrder getSortOrder() {
        return sortOrder;
    }

    public PmAttribute copyWithLabels(List<PmLabel> newLabels) {
        return new PmAttribute(identifier, title, description, folder, newLabels, defaultLabel, sortOrder, deprecated, grain, relations, flags, dataType);
    }

//    public PmAttribute upsertLabel(PmLabel label) {
//        return copyWithLabels(upsert(label, labels));
//    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public List<? extends PmGrainReference> getGrain() {
        return grain;
    }

    public List<String> getRelations() {
        return relations;
    }

    public List<String> getFlags() {
        return flags;
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(identifier, title, description, folder, labels, defaultLabel, sortOrder, deprecated, grain, relations, flags, dataType);
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
//        final PmAttribute other = (PmAttribute) obj;
//
//        return Objects.equal(this.identifier, other.identifier)
//                && Objects.equal(this.title, other.title)
//                && Objects.equal(this.description, other.description)
//                && Objects.equal(this.folder, other.folder)
//                && containsSameElements(this.labels, other.labels)
//                && Objects.equal(this.defaultLabel, other.defaultLabel)
//                && Objects.equal(this.sortOrder, other.sortOrder)
//                && Objects.equal(this.deprecated, other.deprecated)
//                && Objects.equal(this.grain, other.grain)
//                && Objects.equal(this.relations, other.relations)
//                && Objects.equal(this.flags, other.flags)
//                && Objects.equal(this.dataType, other.dataType);
//    }

    @Override
    public String toString() {
        return new GoodDataToStringBuilder(this).toString();
    }
}
