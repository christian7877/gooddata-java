/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.executeafm.afm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gooddata.util.GoodDataToStringBuilder;

/**
 * Represents measure within {@link ObjectAfm}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasureItem implements LocallyIdentifiable {

    private final MeasureDefinition definition;
    private final String localIdentifier;
    private String alias;
    private String format;

    public MeasureItem(final MeasureDefinition definition, final String localIdentifier) {
        this.definition = definition;
        this.localIdentifier = localIdentifier;
    }

    @JsonCreator
    public MeasureItem(@JsonProperty("definition") final MeasureDefinition definition,
                       @JsonProperty("localIdentifier") final String localIdentifier,
                       @JsonProperty("alias") final String alias,
                       @JsonProperty("format") final String format) {
        this.definition = definition;
        this.localIdentifier = localIdentifier;
        this.alias = alias;
        this.format = format;
    }

    /**
     * @return contained definition of measure
     */
    public MeasureDefinition getDefinition() {
        return definition;
    }

    @Override
    public String getLocalIdentifier() {
        return localIdentifier;
    }

    /**
     * @return specified measure alias (will be used as header in result)
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets measure alias (will be used as header in result)
     * @param alias alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * @return measure format (used to format measure values in result)
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets measure format (used to format measure values in result)
     * @param format
     */
    public void setFormat(final String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return GoodDataToStringBuilder.defaultToString(this);
    }

    /**
     * @return true whether contains definition of measure not stored in metadata (= defined ad-hoc), false otherwise
     */
    @JsonIgnore
    public boolean isAdHoc() {
        return definition.isAdHoc();
    }
}
