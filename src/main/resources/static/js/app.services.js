angular.module('get-pitch')
.factory('loginService', loginService);

function loginService($http, $cookies, $httpParamSerializer){
    return {
        login: login
    };
    function login (data){
        req = {
            method: 'POST',
            url: "http://localhost:8080/oauth/token",
            headers: {
                "Authorization": "Basic cmVzdDpxd2UxMjM=",
                "Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                data: $httpParamSerializer(data)
            }
            return $http(req).then(function(response){
                $http.defaults.headers.common.Authorization =
                'Bearer ' + response.data.access_token;
                $cookies.put("access_token", response.data.access_token);
                self.authenticated = true;
                return $http.get('/me');
            });
        };
    };