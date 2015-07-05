// Define the application
app = angular.module('tasks', ['restangular', 'ngResource']);

// Configure the application
app.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/api');
    // Note that we run everything on the localhost
});

//(function() {
//	 angular.module("tasks").factory("Employees",
//	 ["Restangular", function(Restangular) {
//	 var service = Restangular.service("employee");
//	                        // I can add custom methods to my Employee service
//	                        // by adding functions here service
//	                        service.validateData = function(employee) {
//	                           //validate employee data
//	                        }
//	 return service;
//	 }]);
//	}());

// Define the controller
app.controller('mainCtrl', function($scope, Restangular, $resource, $http) {

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
	
	$scope.showCategoryInput = false;
	$scope.newCategory = '';
	
	$scope.showProjectInput = {};
	$scope.newProject = {};
	
	$scope.selectedProjectId = '';
	$scope.tasks = '';

	$scope.taskDataChanged = function () {
		$scope.reloadProjectTaskCounts();
	};
	
	$scope.projectDataChanged = function () {
		$scope.reloadProjectTaskCounts();
	};

	// CRUD Services
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

	$scope.loadAllCategories = function () {
		$scope.categoryService.doGET().then(function(response){
			console.log('Loading all categories');
			$scope.categories = response;
		});
		
	};
	$scope.loadAllCategories();
	
	$scope.loadAllProjects = function () {
		$scope.projectService.doGET().then(function(response){
	    	console.log('Loading all projects');
			$scope.projects = response;

	    	$scope.reloadProjectTaskCounts();
		});
	};
	$scope.loadAllProjects();
	
	$scope.loadTasks = function (id) {
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
	
	$scope.addCategoryClick = function () {
		console.log('addCategoryClicked');
		console.log($scope.showCategoryInput);
	};
	
	$scope.addCategory = function () {
		var newCategoryName = $scope.newCategory.trim();
		if (!newCategoryName) {
			return;
		}

		console.log('Adding category ' + newCategoryName);
		$scope.saving = true;

		$http.post('/api' + '/categories', {
            name: newCategoryName
        }).
	   success(function(data, status, headers) {
	   		console.log('category added');
   			$scope.newCategory = '';
   			$scope.loadAllCategories();
	     }).
	     finally(function() {
	    	 $scope.saving = false;
	     });
		
		$scope.showCategoryInput = false;
		
	};
	
	$scope.addProject = function (categoryId) {
		
		var newProject = {
			category_id: categoryId,
			name: $scope.newProject[categoryId].trim(),
			description: ''
		};
		
		var newProjectName = $scope.newProject[categoryId].trim();
		
		if (!newProjectName) {
			return;
		}
		
		console.log('Adding project ' + newProjectName);
		$scope.saving = true;

		$http.post('/api' + '/projects', {
            category_id: categoryId,
			name: $scope.newProject[categoryId].trim(),
			description: ''
        }).
	   success(function(data, status, headers) {
	   		console.log('project added');
	   		
   			$scope.newProject[categoryId] = '';
   			$scope.loadAllProjects();
	     }).
	     finally(function() {
	    	 $scope.saving = false;
	     });
	};
	

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
	};
	
	$scope.addTask = function () {

		var newTaskName = $scope.newTask.trim();
		if (!newTaskName) {
			return;
		}

		console.log('Adding task ' + newTaskName);
		$scope.saving = true;

		$http.post('/api' + '/tasks', {
            name: newTaskName,
            description: 'test description',
            done: false,
            project_id: $scope.selectedProjectId
        }).
	   success(function(data, status, headers) {
	   		console.log('task added');
   			$scope.newTask = '';
   			$scope.loadTasks($scope.selectedProjectId);
   			$scope.taskDataChanged();
	     }).
	     finally(function() {
	    	 $scope.saving = false;
	     });
	};

	$scope.toggleTaskDone = function (task) {
		console.log('Toggling task: ' + task.name + " " + task.done);

		$scope.taskService.get(task.id).then(function(response){
			response.done = task.done;
			response.save();
			$scope.taskDataChanged();
		});
	};
	
	$scope.editedTask = null;
	$scope.startEditingTask = function(task) {
		console.log('start editing task: ' + task.id + ' ' + task.name);
		
		task.editing=true;
        $scope.editedTask = task;
    }
        
    $scope.doneEditingTask = function(task) {
    	if (!angular.isUndefined(task)) {
    		console.log('done editing task: ' + task.id + ' ' + task.name);
        	
    		// push to server
    		$scope.taskService.get(task.id).then(function(response){
    			response.name = task.name;
    			response.save();
    			$scope.taskDataChanged();
    		}).
    		finally(function() {
	   	    	task.editing=false;
	   	     });
    			
    	}
    	
        $scope.editedTask = null;

    }

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
	};

	$scope.selectedItem = {};
	$scope.changeEmployeeOnTask = function (task, employeeId) {
		console.log("employee change on task: " + task.id + " ==>" + employeeId);

		$scope.taskService.get(task.id).then(function(response){
			response.employee_id = parseInt(employeeId);
			response.save();
		});
	};

	$scope.getEmployeeForTask = function (task) {
		if (!angular.isUndefined(task)) {
			for (var i=0; i<$scope.employees.length; i++) {
				var e = $scope.employees[i];
				if (task.employee_id == e.id) {
					return e;
				}
			}
		}
		return "";
	};

	$scope.$watch('$viewContentLoaded', function(){
	    // do something
		console.log('Page has finished loading..');
	 });
});
