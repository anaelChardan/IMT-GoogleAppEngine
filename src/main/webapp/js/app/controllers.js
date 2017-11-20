'use strict';

zenContactApp.controller('ContactListController', ['$scope', 'contactService', function ($scope, contactService) {
    contactService.getAllContacts(function (contacts, status, headers) {
        $scope.contacts = contacts;
        if(headers("Username")) localStorage.setItem("username", headers("Username"));
        if(headers("Logout")) localStorage.setItem("logout", headers("Logout"));
    });
}]);

zenContactApp.controller('ContactClearController', ['$location', function($location) {
	localStorage.clear();
	$location.path("/list");
}]);

zenContactApp.controller('ContactEditController', ['$scope', 'contactService', '$routeParams', '$location', 'Upload', function ($scope, contactService, $routeParams, $location, Upload) {
    if ($routeParams.id) {
    contactService.getContactById($routeParams.id, function (contact, status, headers) {
        $scope.contact = contact;
        $scope.email = {
        		to: contact.email,
        		toName: contact.firstName + " " + contact.lastName
        };
    }, function error(data, status, headers, config) {
    	if(status === 401) {
    		localStorage.setItem("logout", headers("Logout"));
    		window.location = headers("Location");
    	}
    });

    } else {
        $scope.contact = {};
    }

    $scope.saveContact = function (contact) {
        contactService.saveContact(contact, function (err) {
            if (!err) {
                $location.path("/list");
            } else {
                console.log(err);
            }
        });
    }
    $scope.deleteContact = function (contact) {
        contactService.deleteContact(contact, function (data, status, headers, config) {
                $location.path("/list");
        }, function error(data, status, headers, config) {
        	if(status === 403) {
        		window.alert("You are not an admin.")
        	}
        });
    }
    
    $scope.uploadContactFile = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                Upload.upload({
                    url: $scope.contact.uploadURL,
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                    //reload the contact
                    contactService.getContactById($routeParams.id, function (contact) {
                        $scope.contact = contact;
                    });
                });
            }
        }
    };
    
    $scope.$watch('files', function () {
        $scope.uploadContactFile($scope.files);
    });

    $scope.sendEmail = function (contact) {
    	contactService.sendEmail($scope.email, function (data, status, headers, config) {
    		window.alert("Email sent!")
        }, function error(data, status, headers, config) {
        	console.log(status);
        });
    }
}]);

zenContactApp.directive('myHeroUnit', function() {
    return {
        restrict: 'EA',
        transclude: true,
        template: '<div class="hero-unit">'+
            '<h1>ZenContacts</h1>'+
            '<h2 ng-transclude />'+
            '</div>'
      };
});