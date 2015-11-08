/*
* Define an angular module and routes for  SonCoordinatorUiApplication 
*@author Harrison Mfula
*@since 5.11.2015
*/
var SonCoordinatorUiApplication = angular.module('sampleApp', []);


//Define Routing for SonCoordinatorUiApplication

SonCoordinatorUiApplication.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/AddNewOrder', {
	templateUrl: 'partial/add_order.html',
	controller: 'addOrderController'
      }).
      when('/ShowOrders', {
	templateUrl: 'partial/show_orders.html',
	controller: 'showOrdersController'
      }).
      otherwise({
	redirectTo: '/AddNewOrder'
      });
}]);






