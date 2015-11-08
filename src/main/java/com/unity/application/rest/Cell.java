package com.unity.application.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Visualization model cell
 * @author Harrison Mfula
 * @since 15.10.2015.
 */
public class Cell implements Serializable{
    private String name;
    private String dn;
    private String sc;
    private String freq;
    private List<PI> pis = new ArrayList <PI>();

    public Cell(){

    }


    public Cell(List<PI> pis, String freq, String sc, String dn, String name) {
        setPis(pis);
        setFreq(freq);
        setSc(sc);
        setDn(dn);
        setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (freq != null ? !freq.equals(cell.freq) : cell.freq != null) return false;
        if (name != null ? !name.equals(cell.name) : cell.name != null) return false;
        if (sc != null ? !sc.equals(cell.sc) : cell.sc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sc != null ? sc.hashCode() : 0);
        result = 31 * result + (freq != null ? freq.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public List<PI> getPis() {
        return pis;
    }

    public void setPis(List<PI> pis) {
        this.pis = pis;
    }



}
