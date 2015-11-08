package com.unity.application.model;

import java.io.Serializable;

/**
 * This class realizes SON Coordinator Performance Indicator
 *
 * @author Harrison Mfula
 * @since 26.7.2015.
 */

public class PerformanceIndicator implements  Serializable  {

    private String dn;
    private String objectType;
    private String name;
    private String value;

    public String getDn() {
        return dn;
    }
    public void setDn(String dn) {
        this.dn = dn;
    }
    public String getObjectType() {
        return objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String title) {
        this.value = title;
    }

}
