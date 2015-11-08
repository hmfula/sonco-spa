package com.unity.application.rest;

import java.io.Serializable;

/**
 * Simple performance indicator
 * @author Harrison Mfula
 * @since 15.10.2015.
 */
public class PI implements Serializable{
    private  String name;
    private String value;
    private String summLevel;

    public PI(){

    }

    public PI(String name, String value, String summLevel) {
        setValue(name);
        setValue(value);
        setSummLevel(summLevel);
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

    public void setValue(String value) {
        this.value = value;
    }

    public String getSummLevel() {
        return summLevel;
    }

    public void setSummLevel(String summLevel) {
        this.summLevel = summLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PI pi = (PI) o;

        if (!name.equals(pi.name)) return false;
        if (summLevel != null ? !summLevel.equals(pi.summLevel) : pi.summLevel != null) return false;
        if (value != null ? !value.equals(pi.value) : pi.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (summLevel != null ? summLevel.hashCode() : 0);
        return result;
    }
}
