'use strict';

var zenContactServices = angular.module('zenContact.services', []);

zenContactServices.constant("zenVersion", "v0");
zenContactServices.service('contactService', function ($http, zenVersion) {
    this.getAllContacts = function (callback) {
        $http.get('/api/' + zenVersion + '/users').success(function (contacts, status, headers) {
            callback(contacts, status, headers);
        });
    };

    this.getContactById = function (id, callback, errorCb) {
        $http.get('/api/' + zenVersion + '/users/' + id)
            .success(function (contact, status, headers) {
                callback(contact, status, headers);
            }).error(function (data, status, headers, config) {
                errorCb(data, status, headers, config);
            });
    };

    this.saveContact = function (contact, callback) {
        if (contact.id) {
            $http.put('/api/' + zenVersion + '/users/' + contact.id, contact)
                .success(function () {
                    callback(null);
                }).error(function () {
                    callback("Error");
                });
        } else {
            $http.post('/api/' + zenVersion + '/users', contact)
                .success(function () {
                    callback(null);
                }).error(function () {
                    callback("Error");
                });
        }
    };

    this.deleteContact = function (contact, callback, errorCb) {
        $http.delete('/api/' + zenVersion + '/users/' + contact.id)
            .success(function (data, status, headers, config) {
                callback(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                errorCb(data, status, headers, config);
            });
    };
    
    this.sendEmail = function (email, callback, errorCb) {
        $http.post('/api/' + zenVersion + '/email', email)
            .success(function (data, status, headers, config) {
                callback(data, status, headers, config);
            }).error(function (data, status, headers, config) {
                errorCb(data, status, headers, config);
            });
    };

});