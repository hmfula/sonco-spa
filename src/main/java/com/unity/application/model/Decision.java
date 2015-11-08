package com.unity.application.model;

/**
 * This class encapsulates a SON Coordinator decision for
 * choosing a certain SON Function
 *
 * @author Harrison Mfula
 * @since 26.7.2015.
 */
public class Decision {

    private int id;
    private String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReson() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
