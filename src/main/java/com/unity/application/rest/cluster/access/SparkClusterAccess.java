package com.unity.application.rest.cluster.access;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * This class encapsulates cluster access logic. It delegate calls to the Spark cluster via Spark Job Server API
 *
 * @author Harrison Mfula
 * @since 29.7.2015.
 */
public class SparkClusterAccess {
    private String jobId;

    //For test purposes only
    public static void main(String arg[]) throws JSONException {
        SparkClusterAccess clusterAccess = new SparkClusterAccess();

        clusterAccess.createSonJobContextIfNeeded();
        clusterAccess.runSonJob();
        JSONArray resultFromSpark = clusterAccess.processResult();

        System.out.println("Result from spark: " + resultFromSpark);
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8090/jobs/")
                .path(clusterAccess.getJobId());
        ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

//        JSONArray jsonArray;// uses reflection to cast to JSONArray
//        jsonArray = response.getEntity(JSONObject.class);
//        for (int i = 0; i < jsonArray.length();i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            //here, if needed we can create own type of domain objects and them to list
//            System.out.println(jsonObject.opt("name") + " " + jsonObject.opt("size"));
//        }

        String dns = response.getEntity(JSONObject.class).get("result").toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\\\", "").replaceAll("\"", "");
        //List res = Arrays.asList(dns.split(","));

        System.out.println(dns.split(",")[0]);
        System.out.println(dns.split(",")[5]);
        System.out.println(dns.split(",")[2]);
    }


    /**
     * Posts a job to the Spark JobServer
     *
     * @return response object in json format
     * @throws org.codehaus.jettison.json.JSONException in case of a problem
     */

    public JSONObject runSonJob() throws JSONException {

        //  curl -X POST 'localhost:8090/jobs?appName=test&classPath=RunSonJob&context=test-context'
        MultivaluedMapImpl multivaluedMap = new MultivaluedMapImpl();
        multivaluedMap.putSingle("appName", "test");
        multivaluedMap.putSingle("classPath", "RunSonJob");
        multivaluedMap.putSingle("context", "test-context");


        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8090/jobs")
                .queryParams(multivaluedMap);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class);

        JSONObject jsonObject = response.getEntity(JSONObject.class);
        System.out.println("Result from Spark Cluster is: " + jsonObject.opt("result"));
        JSONObject json = (JSONObject) jsonObject.opt("result");
        setJobId(json.opt("jobId").toString());
        System.out.println("The jobId is: " + json.opt("result"));
        return jsonObject;
    }

    public void createSonJobContextIfNeeded() throws JSONException {

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8090/contexts");
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        String contexts = response.getEntity(Object.class).toString().replaceAll("\\[", "").replaceAll("\\]", "");
        List<String> res = Arrays.asList(contexts.split(","));
        if (res == null) {
            throw new IllegalStateException("Missing SparkContext");
        }

        for (String context : res) {
            if ("test-context".equals(context)) {
                System.out.println("test-context already exists, continuing... ");
                return;
            }
        }

        // -X POST 'localhost:8090/contexts/test-context'
        webResource = client.resource("http://localhost:8090").path("contexts").path("test-context");
        response = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class);
        String status = response.getEntity(JSONObject.class).toString();
        System.out.println("Created Spark context is: " + status);
    }

    public JSONArray processResult() throws JSONException {

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        System.out.println("Processing result for jobId  " + getJobId());

        WebResource webResource = client.resource("http://localhost:8090/jobs/")
                .path(getJobId());
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);


        String dns = response.getEntity(JSONObject.class).get("result").toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\\\", "").replaceAll("\"", "");
        List res = Arrays.asList(dns.split(","));


        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "Cell load");
        jsonObject.put("value", res.get(0));
        jsonArray.put(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 2);
        jsonObject1.put("name", "Ho attempts");
        jsonObject1.put("value", res.get(5));
        jsonArray.put(jsonObject1);

        JSONObject cellEdgeEcNo = new JSONObject();
        cellEdgeEcNo.put("id", 3);
        cellEdgeEcNo.put("name", "Cell Edge EcN0");
        cellEdgeEcNo.put("value", res.get(2));
        jsonArray.put(cellEdgeEcNo);


        JSONObject noname = new JSONObject();
        noname.put("id", 3);
        noname.put("name", "RAB Offset");
        noname.put("value", res.get(4));
        jsonArray.put(noname);
        System.out.println("Spark screwing completed OK: " /*+ getJobId()*/);
        return jsonArray;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
        System.out.println("jobId set to: " + jobId);
    }

    public String getJobId() {
        System.out.println("jobId is: " + jobId);
        return jobId;
    }
}
