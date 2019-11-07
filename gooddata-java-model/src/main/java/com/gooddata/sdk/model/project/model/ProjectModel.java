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

import java.util.Collections;
import java.util.List;

/**
 * Root of the project model. ProjectModel is a directed acyclic graph of datasets and date dimensions which mix some
 * aspects of Logical Data Model and data loading interfaces.
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("projectModel")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"modelMetadata", "datasets", "dateDimensions"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectModel {
    /**
     * Immutable empty project model
     */
    public static final ProjectModel EMPTY = new ProjectModel(null, null, null);

    private final List<PmDataset> datasets;

    private final List<PmDateDimension> dateDimensions;

    private final ModelMetadata modelMetadata;

    public ProjectModel(List<PmDataset> datasets, List<PmDateDimension> dateDimensions) {
        this(null, datasets, dateDimensions);
    }

    @JsonCreator
    public ProjectModel(@JsonProperty("modelMetadata") ModelMetadata modelMetadata,
                        @JsonProperty("datasets") List<PmDataset> datasets,
                        @JsonProperty("dateDimensions") List<PmDateDimension> dateDimensions) {
        this.datasets = datasets == null ? Collections.<PmDataset>emptyList() : datasets;
        this.dateDimensions = dateDimensions == null ? Collections.<PmDateDimension>emptyList() : dateDimensions;
        this.modelMetadata = modelMetadata;
    }

    public List<PmDataset> getDatasets() {
        return datasets;
    }

    public List<PmDateDimension> getDateDimensions() {
        return dateDimensions;
    }

    public ModelMetadata getModelMetadata() {
        return modelMetadata;
    }

//    /**
//     * Returns all {@link PmAttribute}s in all datasets (both plain attributes and anchors)
//     */
//    @JsonIgnore
//    public List<PmAttribute> getAllAttributes() {
//        return flatMap(getDatasets(), new Function<PmDataset, List<PmAttribute>>() {
//            @Override
//            public List<PmAttribute> apply(PmDataset ds) {
//                return ds.getAllAttributes();
//            }
//        });
//    }
//
//    /**
//     * Returns all facts in all datasets
//     */
//    @JsonIgnore
//    public List<PmFact> getAllFacts() {
//        return flatMap(getDatasets(), new Function<PmDataset, List<PmFact>>() {
//            @Override
//            public List<PmFact> apply(PmDataset ds) {
//                return ds.getFacts();
//            }
//        });
//    }
//
//    /**
//     * Returns all labels of all {@link PmAttribute}s in all datasets (both labels of plain attributes
//     * and anchor labels)
//     */
//    @JsonIgnore
//    public List<PmLabel> getAllLabels() {
//        return flatMap(getAllAttributes(), new Function<PmAttribute, List<PmLabel>>() {
//            @Override
//            public List<PmLabel> apply(PmAttribute attr) {
//                return attr.getLabels();
//            }
//        });
//    }
//
//    /**
//     * Finds dataset by it's identifier. Returns null when no dataset matches.
//     */
//    public PmDataset findDataset(String identifier) {
//        return findById(identifier, getDatasets());
//    }
//
//    public PmDateDimension findDateDimension(final String name) {
//        notNull(name, "name cannot be null");
//        return find(dateDimensions, new Predicate<PmDateDimension>() {
//            @Override
//            public boolean apply(PmDateDimension dd) {
//                return name.equals(dd.getName());
//            }
//        }, null);
//    }
//
//    /**
//     * Finds attribute (both plain and / or anchor) by it's identifier. Returns null when no attribute matches.
//     */
//    public PmAttribute findAttribute(String identifier) {
//        return findById(identifier, getAllAttributes());
//    }
//
//    /**
//     * Finds parent attribute (plain attribute or anchor) that serves as parent for the given label.
//     * Returns null when no attribute matches.
//     */
//    public PmAttribute findParentAttributeOfLabel(String labelId) {
//        for (PmAttribute attr : getAllAttributes()) {
//            if (findById(labelId, attr.getLabels()) != null) {
//                return attr;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Finds fact by it's identifier. Returns null when no fact matches.
//     */
//    public PmFact findFact(String identifier) {
//        return findById(identifier, getAllFacts());
//    }
//
//    /**
//     * Finds label (belonging to either plain attribute or anchor) by label's identifier. Returns null when no attribute matches.
//     */
//    public PmLabel findLabel(String identifier) {
//        return findById(identifier, getAllLabels());
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(datasets, dateDimensions);
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
//        final ProjectModel other = (ProjectModel) obj;
//        return Objects.equal(this.modelMetadata, other.modelMetadata)
//            && containsSameElements(this.datasets, other.datasets)
//            && containsSameElements(this.dateDimensions, other.dateDimensions);
//    }

    @Override
    public String toString() {
        return "ProjectModel{" +
            " modelMetadata=" + modelMetadata +
            ", datasets=" + datasets +
            ", dateDimensions=" + dateDimensions +
            '}';
    }
}