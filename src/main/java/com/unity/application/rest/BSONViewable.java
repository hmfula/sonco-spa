package com.unity.application.rest;

import java.util.List;

/**
 * BSON API for visualization components
 * @author Harrison Mfula
 * @since 15.10.2015.
 */
public interface BSONViewable {
    /**
     * Returns all available performance indicators
     * @return list of PIs
     */
    List<Cell> getAllPIs();
    /**
     * Returns all available performance indicators for a given  scopeId and runId
     * @return list of PIs
     */
    List<Cell> getAllPIsForScope(Long scopeId, Long runId);

}
