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
    }).otherwise('/');

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
            if (confirm('Are you sure to delete user ' + userInfo.name + '?')){
                $http.delete('/customer/' + userInfo.id + '/delete').then(function(response){
                    console.log(userInfo + ' has been successfully removed.');
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
            })
        }
    })
    .controller('home', function($scope, $rootScope, $http, $cookies, $location){
        console.log('home controller 1 ...');
        var self = this;


        var isLoginPage = window.location.href.indexOf("login") != -1;
        if(isLoginPage){
            if($cookies.get("access_token")){
                window.location.href = "index";
            }
        } else{
            if($cookies.get("access_token")){
                $http.defaults.headers.common.Authorization =
                  'Bearer ' + $cookies.get("access_token");
            } else{
            }
        }


        console.log(self);
        self.getData = function() {
            if(!$rootScope.authenticated){
                alert('Please login');
            } else {
                console.log('call getData()...');
                getHomeData();
            }
        }
        function getHomeData() {

        $http.get('/greeting').then(function(response){
            console.log('response:');
            console.log(response);
            self.greeting = response.data;
            })
        }

        self.logout = function() {
                loggingout();
        	}

        function loggingout() {
        				$http.post('logout', {}).finally(function() {
        				$cookies.remove("access_token");
        					$rootScope.authenticated = false;
        					$location.path("/login");
                            $rootScope.authenticated = false;
                            window.location.href="#/index";
        					})
        }
    })
    .controller('navigation', function($scope, $rootScope, $http, $location, $httpParamSerializer, $cookies){
        var self = this;
        self.data= {
            grant_type:"password",
            username: "",
            password: ""
        };

        self.login = function(){
            req = {
                method: 'POST',
                url: "http://localhost:8080/oauth/token",
                headers: {
                    "Authorization": "Basic cmVzdDpxd2UxMjM=",
                    "Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                data: $httpParamSerializer(self.data)
            }
            $http(req).then(function(response){
                $http.defaults.headers.common.Authorization =
                    'Bearer ' + response.data.access_token;
                    $cookies.put("access_token", response.data.access_token);
                    $rootScope.authenticated = true;
                    $http.get('/me').then(function(response){
                                    $rootScope.currentUser = response.data;
                                    for (var i = 0; i < $rootScope.currentUser.authorities.length; i++){
                                        if($rootScope.currentUser.authorities[i].authority.includes('ADMIN')){
                                            $rootScope.admin = true;
                                        }
                                    }
                                })
                    window.location.href="#/index";
            });
        }
});
