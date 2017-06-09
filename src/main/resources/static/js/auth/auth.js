angular.module('auth', ['ngCookies'])
    .factory('auth',
        function($rootScope, $location, $http, $httpParamSerializer, $cookies){

            enter = function() {
                if($location.path() != auth.loginPath){
                    auth.path = $location.path();
                    if(!auth.authenticated){
                        $location.path(auth.loginPath);
                    }
                }
            }

            var auth = {

                authenticated: false,

                loginPath: '/login',
                logoutPath: '/logout',
                homePath: '/',
                path: $location.path(),

                currentUser: {},

                authenticate: function(credentials, callback){
                    req = {
                        method: 'POST',
                        url: "http://localhost:8080/oauth/token",
                        headers: {
                            "Authorization": "Basic cmVzdDpxd2UxMjM=",
                            "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
                        },
                        data: $httpParamSerializer(credentials)
                    }

                    $http(req).then(function(response){
                       if(response.data.access_token){
                            auth.authenticated = true;
                            $http.defaults.headers.common.Authorization =
                                'Bearer ' + response.data.access_token;
                            $cookies.put("access_token", response.data.access_token);
                            auth.getCurrentUser(function(response){
                                auth.currentUser = response;
                            });
                       } else {
                            auth.authenticated = false;
                       }
                       callback && callback(auth.authenticated);

                       $location.path(auth.path == auth.loginPath ? auth.homePath : auth.path);
                    }, function(){
                        auth.authenticated = false;
                        callback && callback(false);
                    });
                },

                getCurrentUser: function(callback){
                    if(auth.authenticated){
                        $http.get('/me').then(function(response){
                            if(response.data){
                                callback && callback(response.data);
                            }
                        })
                    }
                },

                isAdmin: function(user, callback){
                    if(auth.authenticated){
                        for (var i = 0; i < user.authorities.length; i++){
                            if(user.authorities[i].authority.includes('ADMIN')){
                                callback && callback(true);
                                break;
                            } else {
                                callback && callback(false);
                            }
                        }
                    }
                },

                clear: function(){
                    $location.path(auth.loginPath);
                    auth.authenticated = false;
                    //todo: oauth logout or remove cookies ?
                },

                init: function(homePath, loginPath, logoutPath){
                    auth.homePath = homePath;
                    auth.loginPath = loginPath;
                    auth.logoutPath = logoutPath;

                    auth.authenticate({}, function(authenticated){
                        if(authenticated){
                            $location.path(auth.path);
                        }
                    });

                    $rootScope.$on('$routeChangeStart', function(){
                        enter();
                    });
                }

            }

            return auth;
        });