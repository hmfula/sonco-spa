package com.unity.application.service;

import com.unity.application.model.PerformanceIndicator;

import java.util.List;

/**
 * This class declares the SON Coordinator application programming interface
 * @author Harrison Mfula
 * @since  26.7.2015.
 */
public interface SonCoordinatorService {

    List<PerformanceIndicator> getResults();
}
