// Define the application
app = angular.module('tasks', ['restangular', 'ngResource', 'hateoas']);

// Configure the application
app.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl(
        'http://localhost:8080/api');
        // Note that we run everything on the localhost
    
//    RestangularProvider.setRequestInterceptor(function(elem, operation) {
//  	  if (operation === "remove") {
//  	     return undefined;
//  	  } 
//  	  return elem;
//  	});
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
	
	$scope.reloadProjectTaskCount = function (projectId) {
		$scope.projectService.doGET("/" + projectId + "/remainingTaskCount").then(function(response){
			var count = parseInt(response);
	    	if (isNaN(count)) {
	    		count = 0;
	    	}
			
			$scope.projectTaskCounts[projectId] = count;
		});
	}
	
	$scope.reloadProjectTaskCounts = function () {
		
		$scope.projectTaskCounts = {};
		for (var i=0; i<$scope.projects.length; i++) {
			var projectId = $scope.projects[i].id;
			$scope.reloadProjectTaskCount(projectId);
		}
	};
	
	
	$scope.selectedProjectId = '';
	$scope.tasks = '';
	
	$scope.taskDataChanged = function () {
		$scope.reloadProjectTaskCounts();
	};
	
	
	
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
    	
    	$scope.reloadProjectTaskCounts();
	});
	$scope.taskService.doGET().then(function(response){
    	//$scope.tasks = response;
	});
	
	
	
	$scope.loadTasks = function (id) {
		//console.log('Loading tasks..' + id);
		$scope.selectedProjectId = id;
		
		$scope.taskService.doGET("/findByProjectId/" + id).then(function(response){
	    	var selectedTasks = response;
	    	$scope.tasks = selectedTasks;
	    	var count = 0;
	    	if (!angular.isUndefined(selectedTasks)) {
	    		count = selectedTasks.length;
	    	}
	    	console.log(count + ' tasks loaded');
		});
		
	};
	
	
	$scope.newTask = '';
	$scope.editedTodo = null;
	
	
	$scope.tasksRemaining = function () {
		var count = 0;
		if (angular.isArray($scope.tasks)) {
			for (var i=0; i<$scope.tasks.length; i++) {
				if (!$scope.tasks[i].done) {
					count = count + 1;
				}
			}	
		}
		return count;
	}
	
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
            description: 'test description',
            done: false,
            project_id: $scope.selectedProjectId
        }).
	   success(function(data, status, headers) {
	   		console.log('task added');
	   	
	            //var newTaskUri = headers()["location"];
	            //console.log("Might be good to GET " + newTaskUri + " and append the task.");
	            // Refetching EVERYTHING every time can get expensive over time
	            // Better solution would be to $http.get(headers()["location"]) and add it to the list
	            //findAllTasks();
	   			$scope.newTask = '';
	   			$scope.loadTasks($scope.selectedProjectId);
	   			$scope.taskDataChanged();
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
	
	$scope.toggleTaskDone = function (task) {
		console.log('Toggling task: ' + task.name + " " + task.done);
		
//		$http.put("/api/task/" + task.id).then(function(response){
//			console.log(response);
//		});
		
		$scope.taskService.get(task.id).then(function(response){
			response.done = task.done;
			response.save();
			$scope.taskDataChanged();
		});
	};
	
	
	$scope.removeTask = function (task) {
		console.log('Removing task: ' + task.name);
		
		$http.delete("/api/task/" + task.id).then(function(response){
			console.log(response);
			
			// Remove the task from the array, rather than reloading
			// from the database.
			var index = $scope.tasks.indexOf(task);
		    if (index > -1) $scope.tasks.splice(index, 1);
		    $scope.taskDataChanged();
		});
		
//		$scope.taskService.remove("/"+task.id).then(function(response){
//			console.log(response);
//			//response.remove();
//		});
		
//		$scope.taskService.get(task.id).then(function(response){
//			console.log(response);
//			response.remove();
//		});
	};
	
	$scope.selectedItem = {};
	$scope.changeEmployeeOnTask = function (task, employeeId) {
		console.log("employee change on task: " + task.id + " ==>" + employeeId);
		
		$scope.taskService.get(task.id).then(function(response){
			console.log(response);
			response.employee_id = parseInt(employeeId);
			console.log(response);
			response.save();
		});
	};
	
	$scope.getEmployeeForTask = function (task) {
		if (!angular.isUndefined(task)) {
			//console.log("Looking up employee for task: " + task.id);
			for (var i=0; i<$scope.employees.length; i++) {
				var e = $scope.employees[i];
				if (task.employee_id == e.id) {
					//console.log("Matched employee: " + e.firstName);
					return e;
				}
			}
		}
		//console.log("No matching employee found");
		return "";
	};
	
	$scope.getRemainingTasksCount = function () {
		console.log("remaining task count");
		
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
