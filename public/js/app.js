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

(function() {
	 angular.module("tasks").factory("Employees",
	 ["Restangular", function(Restangular) {
	 var service = Restangular.service("employee");  
	                        // I can add custom methods to my Employee service
	                        // by adding functions here service
	                        service.validateData = function(employee) {
	                           //validate employee data
	                        }
	 return service;
	 }]);
	}());

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
	
	$scope.selectedProjectId = '';
	$scope.tasks = '';
	
	$scope.employeeService = Restangular.all('employee');
	$scope.categoryService = Restangular.all('category');
	$scope.projectService = Restangular.all('project');
	$scope.taskService = Restangular.all('task');
	
	
	$scope.employeeService.doGET().then(function(response){
    	$scope.employees = response;
	});
	$scope.categoryService.doGET().then(function(response){
    	$scope.categories = response;
	});
	$scope.projectService.doGET().then(function(response){
    	$scope.projects = response;
	});
	$scope.taskService.doGET().then(function(response){
    	//$scope.tasks = response;
	});
	
	
	
	$scope.loadTasks = function (id) {
		console.log('Loading tasks..' + id);
		$scope.selectedProjectId = id;
		
		
		$scope.taskService.doGET("/findByProjectId/" + id).then(function(response){
	    	var selectedTasks = response;
	    	$scope.tasks = selectedTasks;
	    	if (angular.isUndefined(selectedTasks)) {
	    		console.log('0 tasks loaded');
	    	} else {
	    		console.log(selectedTasks.length + ' tasks loaded');
	    	}
	    	
	    	
			
		});
		
	};
	
	
	$scope.newTask = '';
	$scope.editedTodo = null;
	
	
	
	$scope.addTask = function () {
		
		var newTask = {
			name: $scope.newTask.trim(),
			done: false
		};

		if (!newTask.name) {
			return;
		}
		
		console.log('Adding task ' + newTask.name);

		$scope.saving = true;
		
		$http.post('/api' + '/tasks', {
            name: newTask.name,
            description: 'tesr descp',
            done: false,
            project_id: $scope.selectedProjectId
        }).
	   success(function(data, status, headers) {
	   		//alert("Task added");
	   		console.log('task added');
	   	
	            //var newTaskUri = headers()["location"];
	            //console.log("Might be good to GET " + newTaskUri + " and append the task.");
	            // Refetching EVERYTHING every time can get expensive over time
	            // Better solution would be to $http.get(headers()["location"]) and add it to the list
	            //findAllTasks();
	   			$scope.newTask = '';
	   			$scope.loadTasks($scope.selectedProjectId);
	     });
		
		$scope.saving = false;
//		store.insert(newTodo)
//			.then(function success() {
//				$scope.newTodo = '';
//			})
//			.finally(function () {
//				$scope.saving = false;
//			});
	};
	
	
	$scope.removeTask = function (task) {
		console.log('Removing task: ' + task.name);
		
		$scope.taskService.get(task.id).then(function(response){
			console.log(response);
			response.remove();
		});
	};
	
	
//    $scope.employees = Restangular.all('employee').doGET().$object;
//    $scope.categories = Restangular.all('category').doGET().$object;
//    $scope.projects = Restangular.all('project').doGET().$object;
//    $scope.tasks = Restangular.all('task').doGET().$object;
    
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
