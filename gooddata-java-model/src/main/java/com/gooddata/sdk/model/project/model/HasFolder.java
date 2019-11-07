/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.sdk.model.project.model;

/**
 * Interface for objects that have "folder" property. Folders are used in UI for grouping related attributes and facts.
 */
public interface HasFolder {

    /**
     * Returns title of folder this object resides in
     */
    String getFolder();

}
