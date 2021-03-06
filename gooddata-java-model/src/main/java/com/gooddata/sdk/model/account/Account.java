/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.model.account;

import com.fasterxml.jackson.annotation.*;
import com.gooddata.sdk.model.util.UriHelper;
import com.gooddata.sdk.common.util.GoodDataToStringBuilder;

import java.util.List;

/**
 * Account setting
 */
@JsonTypeName("accountSetting")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    public static final String URI = "/gdc/account/profile/{id}";
    public static final String ACCOUNTS_URI = "/gdc/account/domains/{organization_name}/users";

    public static final String LOGIN_URI = "/gdc/account/login/{id}";

    public static final String CURRENT_ID = "current";

    private final String login;

    @JsonView(UpdateView.class)
    private String email;

    @JsonView(UpdateView.class)
    private String password;

    @JsonView(UpdateView.class)
    private String verifyPassword;

    @JsonView(UpdateView.class)
    private String firstName;

    @JsonView(UpdateView.class)
    private String lastName;

    @JsonView(UpdateView.class)
    private List<String> ipWhitelist;

    @JsonIgnore
    private final Links links;

    @JsonCreator
    private Account(
            @JsonProperty("login") String login,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("verifyPassword") String verifyPassword,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("ipWhitelist") List<String> ipWhitelist,
            @JsonProperty("links") Links links
    ) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ipWhitelist = ipWhitelist;
        this.links = links;
    }

    public Account(String firstName, String lastName, Links links) {
        this(null, null, null, null, firstName, lastName, null, links);
    }

    /**
     * Account creation constructor
     * @param email email
     * @param firstName first name
     * @param lastName last name
     * @param password password
     */
    public Account(String email, String password, String firstName, String lastName) {
        this(email, email, password, password, firstName, lastName, null, null);
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public String getUri() {
        return links.getSelf();
    }

    @JsonIgnore
    public String getProjectsUri() {
        return links.getProjects();
    }

    @JsonIgnore
    public String getId() {
        return getId(getUri());
    }

    public List<String> getIpWhitelist() {
        return ipWhitelist;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setVerifyPassword(final String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setIpWhitelist(final List<String> ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Links {
        private final String self;
        private final String projects;

        @JsonCreator
        public Links(@JsonProperty("self") String self, @JsonProperty("projects") String projects) {
            this.self = self;
            this.projects = projects;
        }

        public String getSelf() {
            return self;
        }

        public String getProjects() {
            return projects;
        }
    }

    /**
     * Extract Account's ID from Account's URI
     * @param uri Account's URI
     * @return Account's ID extracted from URI
     */
    public static String getId(String uri) {
        return UriHelper.getLastUriPart(uri);
    }

    @Override
    public String toString() {
        return GoodDataToStringBuilder.defaultToString(this, "password", "verifyPassword");
    }

    /**
     * Class representing update view of account
     */
    public static class UpdateView {
    }
}
