package com.unity.application.controller;

import com.unity.application.model.Decision;
import com.unity.application.model.PerformanceIndicator;

import java.util.List;

/**
 * This class realizes SON Coordinator use cases
 *
 * @author Harrison Mfula
 * @since 26.7.2015.
 */

public class Controller {

    public Decision arbitrate(int id1, int id2) {
        //delegate to REST client
     return null;
    }

    public String resolveAndRun(String id1, String id2) {
        //delegate to REST client

        return "";
    }

    public List<PerformanceIndicator> run(int id) {
        //delegate to REST client
        return null;
    }
    public List<PerformanceIndicator> convertTO(int toolId) {
        //request job-server client (delegate to REST client)
        //request run
        //send result to ui
        return null;
    }



}
