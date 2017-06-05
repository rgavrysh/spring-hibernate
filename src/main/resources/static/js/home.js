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

        $rootScope.$on('getUserInfo', function(){
            self.me();
        })

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

        self.me = function() {
                    console.log('get information about user ... ');
                    getAboutMe();
                }

        function getAboutMe() {
            $http.get('/me').then(function(response){
                console.log('received data ...');
                console.log(response);
                self.customer = response.data;
                for (var i = 0; i < self.customer.authorities.length; i++){
                	if(self.customer.authorities[i].authority.includes('ADMIN')){
                		$rootScope.admin = true;
                		console.log('admin role');
                    }
                }
                $rootScope.username = self.customer.name;
            })
        }
        self.me();

        self.getUsersData = function getUsers() {
        $http.get('/customers').then(function(response){
            console.log('get all users ...');
            console.log(response);
            self.users = response.data;
        })
        }

        self.getUsersData();
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
        console.log('navigation controller 2 ...');
        var self = this;
            self.data= {
                grant_type:"password",
                username: "",
                password: ""
            };

        var req = {
                    method: 'POST',
                    url: "http://localhost:8080/oauth/token",
                    headers: {
                        "Authorization": "Basic cmVzdDpxd2UxMjM=",
                        "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
                    },
                    data: $httpParamSerializer(self.data)
                }
        console.log('Prepared data: ');
        console.log(req);

        self.login = function(){
            getToken(self.data);
            $rootScope.authenticated = true;
            $rootScope.$emit('getUserInfo', {});
        }

        function getToken(data){
        console.log('updated data');
        console.log(data);
        req = {
            method: 'POST',
            url: "http://localhost:8080/oauth/token",
            headers: {
                "Authorization": "Basic cmVzdDpxd2UxMjM=",
                "Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
            data: $httpParamSerializer(self.data)
            }
        console.log(req);
        $http(req).then(function(data){
            console.log('Response data after POST ...');
            console.log(data);
            $http.defaults.headers.common.Authorization =
                'Bearer ' + data.data.access_token;
                $cookies.put("access_token", data.data.access_token);
                window.location.href="#/index";
        });
    }
});
