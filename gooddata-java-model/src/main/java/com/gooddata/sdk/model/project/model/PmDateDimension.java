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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("dateDimension")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "title", "production", "urn", "identifier", "identifierPrefix", "bridges"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PmDateDimension implements HasTitle {

    private final String name;

    private final String title;

    private final boolean production;

    private final String urn;

    private final String identifier;

    private final String identifierPrefix;

    private final List<PmReference> bridges;

    /**
     * Create {@code production} date dimension.
     */
    public PmDateDimension(String name, String title) {
        this(name, title, true, null, null, null, Collections.<PmReference>emptyList());
    }

    public PmDateDimension(String name,
                           String title,
                           boolean production,
                           String urn,
                           String identifier,
                           String identifierPrefix) {
        this(name, title, production, urn, identifier, identifierPrefix, Collections.<PmReference>emptyList());
    }
    
    @JsonCreator
    public PmDateDimension(@JsonProperty("name") String name,
                           @JsonProperty("title") String title,
                           @JsonProperty("production") boolean production,
                           @JsonProperty("urn") String urn,
                           @JsonProperty("identifier") String identifier,
                           @JsonProperty("identifierPrefix") String identifierPrefix,
                           @JsonProperty("bridges") List<PmReference> bridges) {
        this.name = name;
        this.title = title;
        this.production = production;
        this.urn = urn;
        this.identifier = identifier;
        this.identifierPrefix = identifierPrefix;
        this.bridges = bridges == null ? Collections.<PmReference>emptyList() : bridges;
    }

    public PmDateDimension copyWithBridges(List<PmReference> newBridges) {
        return new PmDateDimension(name, title, production, urn, identifier, identifierPrefix, newBridges);
    }

    public PmDateDimension addBridge(PmReference bridge) {
        final ArrayList<PmReference> newBridges = new ArrayList<PmReference>(bridges);
        newBridges.add(bridge);
        return copyWithBridges(newBridges);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public boolean isProduction() {
        return production;
    }

    public String getUrn() {
        return urn;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getIdentifierPrefix() {
        return identifierPrefix;
    }

    /**
     * Returns bridges to other datasets
     */
    public List<PmReference> getBridges() {
        return Collections.unmodifiableList(bridges);
    }
    
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(name, title, production, urn, identifier, identifierPrefix, bridges);
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
//        final PmDateDimension other = (PmDateDimension) obj;
//        return Objects.equal(this.name, other.name)
//            && Objects.equal(this.title, other.title)
//            && Objects.equal(this.production, other.production)
//            && Objects.equal(this.urn, other.urn)
//            && Objects.equal(this.identifier, other.identifier)
//            && Objects.equal(this.identifierPrefix, other.identifierPrefix)
//            && containsSameElements(this.bridges, other.bridges);
//    }

    @Override
    public String toString() {
        return new GoodDataToStringBuilder(this).toString();
    }
}
