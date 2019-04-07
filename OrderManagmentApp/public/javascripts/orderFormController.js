var app = angular.module('OrderFormApp', []);

app.controller('mainCtrl', function($scope, $http) {


    $scope.colors = ["RED", "BLUE", "GREEN"];
    $scope.sizeList = ["S", "M", "L", "XL"];
    $scope.colorValue = "RED";
    $scope.sizeValue = "S";

    $scope.minAge = 18;
    $scope.maxAge = 100;

    //Protecting the database
    $scope.nameRegex = "^[A-Z]{1}[a-zA-Z]{1,30}$";

    $scope.itemlist = [];
    //just to fix the TypeError undefined caused by canAddItem
    $scope.stock = [{id: 999, size: "Inv", color: "Inv", quantity: 0}];

    //init
    $scope.name = "".trim();
    $scope.age = "".trim();


    $scope.addItem = function() {
        //creating and pushing item on the list
        var item = {name: angular.copy($scope.name),
                    age: angular.copy($scope.age),
                    size: angular.copy($scope.sizeValue),
                    color: angular.copy($scope.colorValue)};

        $scope.itemlist.push(item);
        //manipulating stock
        var index = getStockId(item.size,item.color);
        $scope.stock[index].quantity = $scope.stock[index].quantity-1;

        //clearing form
        $scope.name = "".trim();
        $scope.age = "".trim();
    };

    $scope.getStock = function () {
        //prep request
        var req = {
            method: 'GET',
            url: 'http://localhost:9000/getStock',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        //send it
        $http(req).then(function(response){
            $scope.stock = response.data;
        }, function(){
            alert("Cannot get Stock from Server")
        });
    };

    $scope.getStock();

    $scope.sendData = function () {
        //prep request
        var req1 = {
            method: 'POST',
            url: 'http://localhost:9000/postitems',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify($scope.itemlist)
        };
        //send request
        $http(req1).then(function(){

        }, function(){
            alert("Cannot send order to the Server")

        });
        //prep request
        var req2 = {
            method: 'POST',
            url: 'http://localhost:9000/updateStock',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify($scope.stock)
        };
        //send request
        $http(req2).then(function(){
        }, function(){
            alert("Cannot send order to the Server")
        });
        //clear items since they have been sent
        $scope.itemlist = [];
    };

    $scope.canSendData = function () {
        return $scope.itemlist.length === 0
    };

    function getStockId(size,color) {
        for(i=0; i < $scope.stock.length; i++ ){
            if($scope.stock[i].size === size && $scope.stock[i].color === color){
                return i;
            }
        }
        return 0;
    }
    function getItemId(name, age, size, color) {
        for(i=0; $scope.itemlist.length; i++){
            if($scope.itemlist[i].name === name && $scope.itemlist[i].age === age && $scope.itemlist[i].size === size && $scope.itemlist[i].color === color){
                return i;
            }
        }
        return 0;
    }

    $scope.canAddItem = function () {
        var index = getStockId($scope.sizeValue,$scope.colorValue);
        return $scope.stock[index].quantity === 0
    };

    $scope.deleteItem = function (item) {
        if(item === undefined) {
            return null
        } else {
            $scope.itemlist.splice(getItemId(item.name, item.age , item.size, item.color))
            $scope.stock[getStockId(item.size,item.color)].quantity = $scope.stock[getStockId(item.size,item.color)].quantity+1
        }
    }

});
