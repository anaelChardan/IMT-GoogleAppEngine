'use strict';

var zenContactApp = angular.module('zenContact.app', ['ngRoute', 'ngFileUpload', 'zenContact.services']);

zenContactApp.config(function ($routeProvider) {
    $routeProvider.when('/list',            {templateUrl: 'view/list.html',     controller: 'ContactListController'});
    $routeProvider.when('/edit',            {templateUrl: 'view/edit.html',     controller: 'ContactEditController'});
    $routeProvider.when('/edit/:id',        {templateUrl: 'view/edit.html',     controller: 'ContactEditController'});
    $routeProvider.when('/clear', 			{templateUrl: 'view/list.html', 	controller: 'ContactClearController'});
    $routeProvider.otherwise({redirectTo: '/list'});
});

zenContactApp.run(function ($rootScope, $location) {
    $rootScope.location = $location;
    $rootScope.logout = function() { return localStorage.getItem("logout") };
    $rootScope.username = function() { return localStorage.getItem("username") };
});

