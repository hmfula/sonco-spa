/*
*NumberFactory.  Realizes the add orders use case.
*@author Harrison Mfula
*@since 5.11.2015
*/
SonCoordinatorUiApplication.factory('numberFactory', function() {
    numberFactory = {};
    numberFactory.getNumber = function(a) { return a };
    return numberFactory;
});
