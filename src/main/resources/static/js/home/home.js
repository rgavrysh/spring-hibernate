angular.module('home', ['auth'])
    .controller('home', home);

function home($http, auth){
    var self = this;

    self.currentUser = auth.getCurrentUser(function(response){
        self.currentUser = response;
    });
}