// Define the application
app = angular.module('tasks', ['restangular', 'ngResource', 'hateoas']);

// Configure the application
app.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl(
        'http://localhost:8080/api');
        // Note that we run everything on the localhost
});

app.config(function (HateoasInterceptorProvider) {
    HateoasInterceptorProvider.transformAllResponses();
});

// Define the controller
app.controller('mainCtrl', function($scope, Restangular, $resource) {
    Restangular.all('task').getList().then(function(result) {
        $scope.tasks = result;
        console.log(result);
    });

    var item = $resource("/api/task").get(null, function () {
    console.log("Here's a related $resource object: ", item);
    console.log(item._embedded.task[0])
    });
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
