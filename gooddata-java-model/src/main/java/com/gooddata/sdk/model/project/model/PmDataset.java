/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gooddata.util.GoodDataToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subset of logical data model that can be loaded via SLI. It consists of:
 * <ul>
 *     <li>anchor - attribute with finest granularity (same granularity as facts, if there are any); it provides identity
 *         to dataset records and can be used for COUNTing number of records in fact table in MAQL.</li>
 *     <li>attributes - all other attributes in the dataset (except anchor); these have coarser granularity than the
 *         anchor and can be used in metrics build from facts in this dataset</li>
 *     <li>facts - you know, the facts :)</li>
 *     <li>references - references to other datasets (by identifier) / date dimensions (by name); attributes contained
 *         in referenced datasets can be used in metrics build from facts in this dataset</li>
 * </ul>
 *
 * @see ProjectModel
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("dataset")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"identifier", "title", "description", "anchor", "attributes", "facts", "references", "bridges"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmDataset implements HasIdentifier, HasVisualProperties {

    private final String identifier;

    private final String title;

    private final String description;

    private final boolean production;

    private final PmAttribute anchor;

    private final List<PmAttribute> attributes;

    private final List<PmFact> facts;

    private final List<PmReference> references;

    private final List<PmReference> bridges;

    /**
     * @param identifier identifier unique among all other model objects
     * @param title human-readable short description
     * @param description longer human-readable description
     * @param anchor attribute with finest granularity which defines identity for dataset records
     * @param attributes all attributes contained in this dataset (not including the anchor)
     * @param facts facts contained in this dataset
     * @param references references to other datasets or date dimensions
     * @param bridges bridges to other datasets
     */
    public PmDataset(@JsonProperty("identifier") String identifier,
                     @JsonProperty("title") String title,
                     @JsonProperty("description") String description,
                     @JsonProperty("production") boolean production,
                     @JsonProperty("anchor") PmAttribute anchor,
                     @JsonProperty("attributes") List<PmAttribute> attributes,
                     @JsonProperty("facts") List<PmFact> facts,
                     @JsonProperty("references") List<PmReference> references,
                     @JsonProperty("bridges") List<PmReference> bridges) {

        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.production = production;
        this.anchor = anchor;
        this.attributes = attributes == null ? Collections.<PmAttribute>emptyList() : attributes;
        this.facts = facts == null ? Collections.<PmFact>emptyList() : facts;
        this.references = references == null ? Collections.<PmReference>emptyList() : references;
        this.bridges = bridges == null ? Collections.<PmReference>emptyList() : bridges;
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


    public boolean isProduction() {
        return production;
    }

    /**
     * Returns attribute with finest granularity which defines identity for dataset records
     */
    public PmAttribute getAnchor() {
        return anchor;
    }

    /**
     * Returns plain attributes contained in this dataset (EXCLUDING the anchor)
     */
    public List<PmAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    /**
     * Returns ALL attributes contained in this dataset (INCLUDING the anchor)
     */
    @JsonIgnore
    public List<PmAttribute> getAllAttributes() {
        final ArrayList<PmAttribute> attrs = new ArrayList<PmAttribute>();
        if (anchor != null) {
            attrs.add(anchor);
        }
        attrs.addAll(attributes);
        return attrs;
    }

    /**
     * Returns facts contained in this dataset
     */
    public List<PmFact> getFacts() {
        return Collections.unmodifiableList(facts);
    }

    /**
     * Returns bridges to other datasets
     */
    public List<PmReference> getBridges() {
        return Collections.unmodifiableList(bridges);
    }

    /**
     * Returns references to other datasets or date dimensions
     */
    public List<PmReference> getReferences() {
        return Collections.unmodifiableList(references);
    }

    public PmDataset copyWithAnchor(PmAttribute newAnchor) {
        return new PmDataset(identifier, title, description, production, newAnchor, attributes, facts, references, bridges);
    }

    public PmDataset copyWithAttributes(List<PmAttribute> newAttributes) {
        return new PmDataset(identifier, title, description, production, anchor, newAttributes, facts, references, bridges);
    }

    public PmDataset copyWithFacts(List<PmFact> newFacts) {
        return new PmDataset(identifier, title, description, production, anchor, attributes, newFacts, references, bridges);
    }

    public PmDataset copyWithReferences(List<PmReference> newRefs) {
        return new PmDataset(identifier, title, description, production, anchor, attributes, facts, newRefs, bridges);
    }

    public PmDataset addReference(PmReference reference) {
        final ArrayList<PmReference> newRefs = new ArrayList<PmReference>(references);
        newRefs.add(reference);
        return copyWithReferences(newRefs);
    }

    public PmDataset copyWithBridges(List<PmReference> newBridges) {
        return new PmDataset(identifier, title, description, production, anchor, attributes, facts, references, newBridges);
    }

    public PmDataset addBridge(PmReference bridge) {
        final ArrayList<PmReference> newBridges = new ArrayList<PmReference>(bridges);
        newBridges.add(bridge);
        return copyWithBridges(newBridges);
    }

//
//    public PmDataset upsertAttribute(PmAttribute attr) {
//        return copyWithAttributes(upsert(attr, attributes));
//    }
//
//    public PmDataset upsertFact(PmFact fact) {
//        return copyWithFacts(upsert(fact, facts));
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(identifier, title, description, production, anchor, attributes, facts, references, bridges);
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
//        final PmDataset other = (PmDataset) obj;
//        return Objects.equal(this.identifier, other.identifier)
//            && Objects.equal(this.title, other.title)
//            && Objects.equal(this.description, other.description)
//            && Objects.equal(this.production, other.production)
//            && Objects.equal(this.anchor, other.anchor)
//            && containsSameElements(this.attributes, other.attributes)
//            && containsSameElements(this.facts, other.facts)
//            && containsSameElements(this.references, other.references)
//            && containsSameElements(this.bridges, other.bridges);
//
//    }

    @Override
    public String toString() {
        return new GoodDataToStringBuilder(this).toString();
    }
}
