/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

/**
 * Interface for objects that have "identifier" property. Identifiers have to be unique for all objects in a single model.
 */
public interface HasIdentifier {

    /**
     * Returns identifier of the object
     */
    String getIdentifier();

}
