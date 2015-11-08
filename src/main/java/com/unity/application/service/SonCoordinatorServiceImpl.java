package com.unity.application.service;

import com.unity.application.model.PerformanceIndicator;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;



/**
 * This class realizes the SON Coordinator application programming interface
 * @author Harrison Mfula
 * @since  26.7.2015.
 */
public class SonCoordinatorServiceImpl implements  SonCoordinatorService{

    @Override
    public List<PerformanceIndicator> getResults() {
        return null;
    }

    public String constructJSONArray() throws JSONException {

        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();

        JSONArray result = new JSONArray();
        Random r = new Random();
        int[] r1 = { r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100) };
        int[] r2 = { r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100) };
        int[] r3 = { r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100) };

        one.put("one", Arrays.toString(r1));
        two.put("two", Arrays.toString(r2));
        three.put("three", Arrays.toString(r3));

        result.put(one);
        result.put(two);
        result.put(three);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("sparkData", result);
        System.out.println("Sending this data to view (sparkline.html): " + jsonObj);

        return jsonObj.toString();
    }
}
