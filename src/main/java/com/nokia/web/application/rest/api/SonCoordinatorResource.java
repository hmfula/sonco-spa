package com.nokia.web.application.rest.api;

import com.unity.application.model.PerformanceIndicator;
import com.unity.application.rest.cluster.access.SparkClusterAccess;
import com.unity.application.service.SonCoordinatorServiceImpl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class realizes SON Coordinator REST application programming interface
 *
 * @author Harrison Mfula
 * @since 26.7.2015.
 */
@Path("/service")
public class SonCoordinatorResource {

    SonCoordinatorServiceImpl service = new SonCoordinatorServiceImpl();
    /**
     * Shows:
     * GET method
     * How to use path parameters
     */

    JSONArray jsonArray = new JSONArray();

    @GET
    @Path("/greet/{param}")
    public Response greet(@PathParam("param") String msg) {

        String output = "Hello " + msg + ", <br>Welcome to ePizza Server ";

        return Response.status(200).entity(output).build();

    }

    /**
     * Shows :
     * POST method,
     * How to pass an object in the request
     * How to return an object in the Response
     * <p/>
     * Example 1:
     * curl -H "Content-Type:application/json"  --data  "[{\"name\":\"Blognise\",\"size\":\"S\"}]" -X POST "http://localhost:8080/sonco-web/rest/service/create"
     * <p/>
     * Example 2:
     * url: http://localhost:8080/epizza/rest/service/create
     * set MIME type header:
     * <p/>
     * Content-Type application/json
     * <p/>
     * payload used:
     * <p/>
     * [{"name": "RedBull", "size": "X"},{"name": "Italiano", "size": "XXL"},{"name": "Blognise", "size": "S"}]
     * <p/>
     * <p/>
     * sample result:
     * <p/>
     * [{"name":"RedBull","size":"X"},{"name":"Italiano","size":"XXL"},{"name":"Blognise","size":"S"}]
     */

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(List<PerformanceIndicator> pizzas) {
        String result = "Created : ";
        List<PerformanceIndicator> list = new ArrayList<PerformanceIndicator>();
        //list.add(pizza);
        return Response.status(201).entity(pizzas).build();

    }

    /**
     * url:
     * http://localhost:8080/epizza/rest/service/read
     * <p/>
     * result:
     * [{"name": "RedBull", "size": "X"},{"name": "Italiano", "size": "XXL"},{"name": "Blognize", "size": "S"}]
     */
    @GET
    @Path("/read")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readOrder(@QueryParam("customerId") String customerId) {
        PerformanceIndicator pizza = new PerformanceIndicator();
        pizza.setName("Salami");
        pizza.setValue("XXL");
        if (customerId != null) pizza.setValue(customerId);
        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();

        PerformanceIndicator pizza1 = new PerformanceIndicator();
        pizza1.setName("Mexican");
        pizza1.setValue("XXL");

        try {
            json.put("name", pizza.getName());
            json.put("size", pizza.getValue());
            json1.put("name", pizza1.getName());
            json1.put("size", pizza1.getValue());
            jsonArray.put(json);
            jsonArray.put(json1);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String result = "Read : " + pizza;
        return Response.status(200).entity(jsonArray).build();//disadvantage - need to read jsonarray on client side

    }

    /**
     * Shows :
     * UPDATE method,
     * How to pass an object in the request
     * How to return an object in the Response
     * <p/>
     * <p/>
     * Example:
     * [{"name": "RedBull", "size": "X"},{"name": "Italiano", "size": "XXL"},{"name": "Blognize", "size": "S"}]
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(List<PerformanceIndicator> pizzas) {//ALWAYS wrap Pizza in a list
        String result = "Updated : ";
        //List<Pizza> list = new ArrayList<Pizza>();
        //list.add(pizza);
        return Response.status(201).entity(pizzas).build();

    }

    /**
     * Shows :
     * DELETE method,
     * How to pass an object in the request
     * How to return an object in the Response
     */
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@QueryParam("customerId") String customerId) {

        String result = "Deleted " + "pizza order for customer id: " + customerId;
        return Response.status(200).entity(result).build();

    }

    /**
     * Shows:
     * POST method
     * How to use form parameters
     * <p/>
     * Accepts registration details from the user
     * The full url to the resource is:
     * <note>http://localhost:8080/epizza/rest/service/register-user</note>
     *
     * @param name
     * @param age
     * @return
     */

    @POST
    @Path("/register-user")
    public Response addUser(
            @FormParam("name") String name,
            @FormParam("age") int age) {

        return Response.status(200)
                .entity("Thank you for registration.<br> User added to database, name : " + name + ", age : " + age)
                .build();

    }

    @GET
    @Path("/sparklinetest")
    @Produces("application/json")
    public String processResult() throws JSONException {


        return service.constructJSONArray();

    }


    /**
     * Download log file
     * <p/>
     * url:
     * http://localhost:8080/epizza/rest/service/download
     */
    private static final String FILE_PATH = "us-cities.csv";

    @GET
    @Path("/download")
    @Produces("text/plain")
    public Response getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(FILE_PATH).getFile());


        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=\"file_from_server.log\"");
        return response.build();

    }

    /**
     * Sample call:
     * http://localhost:8080/sonco-web/rest/service/optimization-result
     * Sample result:
     * [{"id":1,"name":"Cell load","value":"30"},{"id":2,"name":"Ho attempts","value":"75"},
     * {"id":3,"name":"Cell Edge EcN0","value":"75"},{"id":3,"name":"RAB Offset","value":"30"}]
     *
     */

    @GET
    @Path("/optimization-result")
    public Response getKpis() throws JSONException, InterruptedException {
        return getOldStyleResponse();
    }

    private Response getOldStyleResponse() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "Cell load");
        jsonObject.put("value", "70.5");
        jsonArray.put(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 2);
        jsonObject1.put("name", "Ho attempts");
        jsonObject1.put("value", "80");
        jsonArray.put(jsonObject1);

        JSONObject cellEdgeEcNo = new JSONObject();
        cellEdgeEcNo.put("id", 3);
        cellEdgeEcNo.put("name", "Cell Edge EcN0");
        cellEdgeEcNo.put("value", "15");
        jsonArray.put(cellEdgeEcNo);
        return Response.status(200)

                .entity(jsonArray)
                .build();
    }


    @POST
    @Path("/orders")
    public Response postAnOrder(JSONObject order) throws JSONException {
        order.put("id", Math.round(Math.random()));//random id
        System.out.println(order.optString("name", "TestingDefault"));//test default value setting optString
        return Response.status(200)

                .entity(order)
                .build();

    }

    @DELETE
    @Path("/orders/{id:[0-9]}")
    public Response deleteAnOrder(@PathParam("id") String id) throws JSONException {
        System.out.println("Deleted order with id: " + id);
        return Response.status(200)
                .build();

    }


    @PUT
    @Path("/orders/{id:[0-9]}")
    public Response updateOrder(@PathParam("id") String id) throws JSONException {
        System.out.println("Order update successful, order id: " + id);
        return Response.status(200)
                .build();

    }
}
