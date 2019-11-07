/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

/**
 * Interface for objects that have "dataType" property. Data types may be used to optimize physical storage of loadable
 * values and affect the way these values are sorted.
 */
public interface HasDataType {

    /**
     * Returns object's data type (f.ex. INT, DECIMAL(12,2) ...)
     */
    String getDataType();

}