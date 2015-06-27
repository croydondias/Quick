// Define the application
app = angular.module('tasks', ['restangular', 'ngResource', 'hateoas']);

// Configure the application
app.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl(
        'http://localhost:8080/api');
        // Note that we run everything on the localhost
});

//app.config(function (HateoasInterceptorProvider) {
//    HateoasInterceptorProvider.transformAllResponses();
//});

// Define the controller
app.controller('mainCtrl', function($scope, Restangular, $resource, $http) {
//    Restangular.all('task').getList().then(function(result) {
//        $scope.tasks = result;
//        console.log('tasks ->' + $scope.tasks);
//    });
    
    $scope.resource = Restangular.all('employee');
	
	$scope.resource.get(1).then(function(response){
			//console.log(response);
	});
	
	$scope.resource.doGET().then(function(response){
		//console.log(response);
	});
    
    $scope.employees = Restangular.all('employee').doGET().$object;
    $scope.categories = Restangular.all('category').doGET().$object;
    $scope.projects = Restangular.all('project').doGET().$object;
    $scope.tasks = Restangular.all('task').doGET().$object;
    
//    $http.get("http://www.w3schools.com/angular/customers.php")
//    .success(function (response) {
//    	//console.log(response);
//    	//console.log(response.records);
//    	$scope.names = response.records;
//    });
    
});

// Standardize data format (extract from meta-data where needed)
app.config(function(RestangularProvider) {
    // add a response intereceptor
    RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
      var extractedData;
      // .. to look for getList operations
      if (operation === "getList") {
        // .. and handle the data and meta data
        extractedData = data.tasks;
      } else {
        extractedData = data;
      }
      return extractedData;
    });
});
