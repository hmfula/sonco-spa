/**
 * Created with IntelliJ IDEA.
 * User: harrison
 * Date: 4/4/15
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
$(function () {
var $orders = $("#orders");
var $name = $("#name");
var $drink = $("#drink");
var orderTemplate = $("#order-template").html() ;//mustache will use data json  object to find values for fields

    $.ajax({
        type:"GET",
        url:"rest/service/orders",
        success:function(orders){

            $.each(orders,function(index, order){
              //  console.log("success Harry",orders);
            //   $orders.append("<li>Name: " + order.name + ",  Drink: " + order.drink + "</li>")  ;
              //  addOrder(order);
                addOrderUsingMustache(order);

            });
        },
        error:function(){
            alert("Error when loading orders");
        }

    });

    //attach click listener
    $("#add-order").on("click",function(){

       //create  order json object
        var order = {
             name: $name.val(),
            drink:$drink.val()
        };

       //POST call
        $.ajax({
            type:"POST",
            data:JSON.stringify(order),//stringify is mandatory for json payload
            url:"rest/service/orders",
            contentType:"application/json",//mandatory
            success:function(newOrder){

                   // $orders.append("<li>Name: " + newOrder.name + ",  Drink: " + newOrder.drink + "</li>");
               // addOrder(newOrder)
                addOrderUsingMustache(newOrder)

            },
            error:function(){
                alert("Error when processing the new order");
            }

        });
    })

    /**
     * Simple function that adds  an order to the page
     * @param order
     */
    function addOrder(order){
     $orders.append("<li>Name: " + order.name + ",  Drink: " + order.drink + "</li>")  ;

 }
    function addOrderUsingMustache(order){
        $orders.append(Mustache.render(orderTemplate, order));//this is how to call mustache


    }
//technicality $(".remove").on("click",function()
    $orders.delegate(".remove","click",function(){
        var $li = $(this).closest("li");
        $.ajax({
            type:"DELETE",
            url:"rest/service/orders/" + $(this).attr("data-id"),
            success:function(){
                //$li.remove();this is OK
                $li.fadeOut(300, function(){//esthetics
                    $(this).remove();//this refers to $li
                })
            }
        });

    })

    //edit mode
    $orders.delegate(".editOrder","click",function(){
        var $li = $(this).closest("li");
        $li.find("input.name").val($li.find("span.name").html());
        $li.find("input.drink").val($li.find("span.drink").html());
        $li.addClass("edit");

    });
    //cancel edit
    $orders.delegate(".cancelEdit","click",function(){
         $(this).closest("li").removeClass("edit");


    });

    $orders.delegate(".saveEdit","click",function(){
        var $li =  $(this).closest("li").removeClass("edit");
        var order={
            name:$li.find("input.name").val(),
            drink:$li.find("input.drink").val()
        };
        $.ajax({
            type:"PUT",
            data:JSON.stringify(order),//stringify is mandatory for json payload
            url:"rest/service/orders/" +$li.attr("data-id"),
            contentType:"application/json",//mandatory
            success:function(newOrder){
            $li.find("span.name").html(order.name);
                $li.find("span.drink").html(order.drink);
                $li.removeClass("edit");
             },
            error:function(){
                alert("Error updating order");
            }

        })

    });
});