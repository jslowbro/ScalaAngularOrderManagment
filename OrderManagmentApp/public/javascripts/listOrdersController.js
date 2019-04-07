var app = angular.module('ShowOrdersApp', []);


app.controller('mainCtrl', function($scope, $http) {

    console.log("Hello world");

    $scope.orderlist = [];


    $scope.getOrders = function () {
        //prep the request
        var req = {
            method: 'GET',
            url: 'http://localhost:9000/getListOfOrders',
            headers: {
                'Content-Type': 'application/json'
            }

        };
        //send request
        $http(req).then(function(response){
            $scope.orderlist =  response.data;
        }, function(){
            alert("Cannot access database")
        });
    };



});
