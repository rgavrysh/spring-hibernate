angular.module('get-pitch', [ 'ngRoute', 'ngCookies'])
.config(function($routeProvider, $httpProvider){

  $routeProvider.when('/home', {
    templateUrl : 'home',
    controller : 'home',
    controllerAs : 'controller'
  }).when('/login', {
    templateUrl : 'login',
    controller : 'navigation',
    controllerAs : 'controller'
  }).when('/about', {
    templateUrl : 'about',
    controller : 'about',
    controllerAs : 'controller'
  });

  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

})
.controller('about', function($scope, $rootScope, $http, $cookies){
  var self = this;
  self.getUsersData = function() {
    if ($rootScope.admin){
      $http.get('/customers').then(function(response){
        self.users = response.data;
        document.getElementById('progressbar').style.display='none';
      })
    } else {

    }
  }
  self.deleteUser = function (userInfo){
    if (confirm('Are you sure you want to delete user ' + userInfo.name + '?')){
      $http.delete('/customer/' + userInfo.id + '/delete').then(function(response){
        self.isRemoved = true;
        self.getUsersData();
      })
    }
  }
})
.controller('userForm', function($scope, $http){
  var self = this;
  self.isExpanded = false;
  self.userInfo = {
    name: "",
    phone: "",
    email: ""
  }

  self.showForm = function(){
    self.isExpanded = !self.isExpanded;
  }

  self.save = function(userInfo){
    $http.post('/customer', userInfo).then(function(response){
      self.isUserAddedSuccessfully = true;
    })
  }
})
.controller('home', function($scope, $rootScope, $http, $cookies, $location){
  var self = this;
  self.getHomeData = function () {
    $http.get('/greeting').then(function(response){
      self.greeting = response.data;
    })
  }
})
.controller('navigation', function($scope, $rootScope, $http, $location,
  $httpParamSerializer, $cookies, loginService){
  var self = this;
  self.data= {
    grant_type:"password",
    username: "",
    password: ""
  };

  self.login = function(){
    loginService.login(self.data).then(function(response){
      self.currentUser = response.data;
      if(self.currentUser){
        self.authenticated = true;
        for (var i = 0; i < self.currentUser.authorities.length; i++){
          if(self.currentUser.authorities[i].authority.includes('ADMIN')){
            self.admin = true;
          }
        }
      }
      window.location.href="#/index";
    });
    console.log(self.currentUser);
  }

  $scope.$watch('$scope.currentUser', function(current, original){
    console.log(current);
    console.log(original);
  })
  self.logout = function() {
    $http.post('logout', {}).finally(function() {
      $cookies.remove("access_token");
      $rootScope.authenticated = false;
      $location.path("/login");
      window.location.href="#/index";
    })
  }
});
