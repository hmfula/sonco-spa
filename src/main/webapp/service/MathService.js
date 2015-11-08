/*
*MathService.  Realizes the add orders use case.
*@author Harrison Mfula
*@since 5.11.2015
*/
SonCoordinatorUiApplication.service('mathService', function() {
   	this.add = function (a, b) {
	return a + b;
    };
});

