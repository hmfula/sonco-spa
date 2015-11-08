/*
*AddOrderController.  Realizes the add orders use case.
*@author Harrison Mfula
*@since 5.11.2015
*/
SonCoordinatorUiApplication.controller('addOrderController', function($scope, mathService, numberFactory) {
//variables
var a, b ;
//mimic getting data through AJAX
a = numberFactory.getNumber(6);
b = numberFactory.getNumber(1);
//call the service
$scope.message = mathService.add(a,b);
});





