/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

/**
 * Interface for objects that have properties that describe the object in a human-readable way.
 */
public interface HasVisualProperties extends HasTitle {

    /**
     * Returns longer, human-readable description of the object.
     */
    String getDescription();

}
