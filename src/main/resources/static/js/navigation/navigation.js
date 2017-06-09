angular.module('navigation', ['ngRoute', 'auth'])
    .controller('navigation', navigation);

function navigation($route, auth){
    var self = this;

    self.credentials = {
        grant_type:"password",
        username: "",
        password: ""
    };

    self.tab = function(route){
        return $route.current && route === $route.current.controller;
    }

    self.authenticated = function() {
        return auth.authenticated;
    }

    self.currentUser = auth.getCurrentUser(function(response){
            self.currentUser = response;
        });

    self.admin = function(){
        if(self.authenticated){
            return auth.isAdmin(self.currentUser, function(admin){
                return admin;
            })
        } else {
            return false;
        }
    }

    self.login = function() {
        auth.authenticate(self.credentials, function(authenticated){
            if (authenticated){
                console.log('Login succeeded.');
                self.error = false;
            } else {
                console.log('Login failed.');
                self.error = true;
            }
        });
    };

    self.logout = auth.clear;
}