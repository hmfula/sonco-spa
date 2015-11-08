/**
 * Created with IntelliJ IDEA.
 * User: harrison
 * Date: 4/4/15
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    var $display_graphs = $("#display-graphs");



    $display_graphs.on("click", function () {  //attach click listener
        var dataString = "";

        $.ajax({
            type: "GET",
            url: "rest/service/optimization-result",
            success: function (kpis) {
                $.each(kpis, function (index, kpi) {
                    console.log("successful call to rest/service/optimization-result", kpis);
                    dataString += "\n" + "KPI Name: " + kpi.name + ",  Value: " + kpi.value;
                });

                alert("AJAX call successful" + dataString);
                constructDonutGraph(kpis)
            },
            error: function () {
                alert("AJAX call failed. url = rest/service/optimization-result");
            }

        });


    })


    function constructData(kpis, data, dataLabels) {
        $.each(kpis, function (index, kpi) {
            data.push(kpi.value);
            dataLabels.push(kpi.name);
            console.log("Added KPI: " + kpi.name, kpi.value);
        });
    }

    function constructDonutGraph(kpis) {
        var data = [];
        var dataLabels = [];
        constructData(kpis, data, dataLabels)

    //Begin graph
    //Width and height
        var w = 550;
        var h = 550;

        var outerRadius = w / 2;
        var innerRadius = w / 3;
        var arc = d3.svg.arc().innerRadius(innerRadius)
            .outerRadius(outerRadius);

        var pie = d3.layout.pie();

        //Easy colors accessible via a 10-step ordinal scale
        var color = d3.scale.category10();

        //Create SVG element
        var svg = d3.select("body")
            .append("svg")
            .attr("width", w)
            .attr("height", h);


        //Set up groups
        var arcs = svg.selectAll("g.arc")
            .data(pie(data))
            .enter()
            .append("g")
            .attr("class", "arc")
            .attr("transform", "translate(" + outerRadius + "," + outerRadius + ")");

        //Draw arc paths
        arcs.append("path")
            .attr("fill", function (d, i) {
                return color(i);
            })
            .attr("d", arc);

        //Labels
        arcs.append("text")
            .attr("transform", function (d) {
                return "translate(" + arc.centroid(d) + ")";
            })
            .attr("text-anchor", "middle")
            .text(function (d, i) {
                return d.value + " " + dataLabels[i];
            });

        //end graph
        createBarGraph()
    }


    /**
     * Bar graph
     */

    function createBarGraph() {


// read data from external file  eg data.json
        d3.json("data/data.json", function (error, data) {

//declare variables
//var data = [10, 50, 80]
            var width = 500;
            var height = 500;

            var color = d3.scale.category10();

//create canvas
            var canvas = d3.select("body")
                .append("svg")
                .attr("width", width)
                .attr("height", height);

//create rects
            canvas.selectAll("rect")
                .data(data)
                .enter()
                .append("rect")
                .attr("width", function (d) {
                    return d.age * 10
                })
                .attr("height", 48)
                .attr("y", function (d, i) {
                    return i * 50
                })
                .attr("fill", function (d, i) {
                    return color(i);
                })

//add texts
            canvas.selectAll("text")
                .data(data)
                .enter()
                .append("text")
                .attr("fill", "white")
                .attr("y", function (d, i) {
                    return i * 50 + 24
                })
                .text(function (d) {
                    return d.name + " " + d.age + " Years"
                })
        })
    }


    /*
     * FOR REFERENCE ONLY
     * Simple function that adds  an order to the page
     * @param order
     */
    //function addOrder(order) {
    //    $orders.append("<li>Name: " + order.name + ",  Drink: " + order.drink + "</li>");
    //
    //}
    //
    //function addOrderUsingMustache(order) {
    //    $orders.append(Mustache.render(orderTemplate, order));//this is how to call mustache
    //
    //
    //}

//technicality $(".remove").on("click",function()
//    $orders.delegate(".remove", "click", function () {
//        var $li = $(this).closest("li");
//        $.ajax({
//            type: "DELETE",
//            url: "rest/service/orders/" + $(this).attr("data-id"),
//            success: function () {
//                //$li.remove();this is OK
//                $li.fadeOut(300, function () {//esthetics
//                    $(this).remove();//this refers to $li
//                })
//            }
//        });
//
//    })
//
//    //edit mode
//    $orders.delegate(".editOrder", "click", function () {
//        var $li = $(this).closest("li");
//        $li.find("input.name").val($li.find("span.name").html());
//        $li.find("input.drink").val($li.find("span.drink").html());
//        $li.addClass("edit");
//
//    });
//    //cancel edit
//    $orders.delegate(".cancelEdit", "click", function () {
//        $(this).closest("li").removeClass("edit");
//
//
//    });
//
//    $orders.delegate(".saveEdit", "click", function () {
//        var $li = $(this).closest("li").removeClass("edit");
//        var order = {
//            name: $li.find("input.name").val(),
//            drink: $li.find("input.drink").val()
//        };
//        $.ajax({
//            type: "PUT",
//            data: JSON.stringify(order),//stringify is mandatory for json payload
//            url: "rest/service/orders/" + $li.attr("data-id"),
//            contentType: "application/json",//mandatory
//            success: function (newOrder) {
//                $li.find("span.name").html(order.name);
//                $li.find("span.drink").html(order.drink);
//                $li.removeClass("edit");
//            },
//            error: function () {
//                alert("Error updating order");
//            }
//
//        })
//
//    });
});
