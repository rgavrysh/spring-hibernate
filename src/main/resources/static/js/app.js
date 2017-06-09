angular.module('app', ['ngRoute', 'auth', 'navigation', 'admin', 'home'])
    .config(config).run(function(auth){
        auth.init('/', '/login', '/logout');
    });

function config($routeProvider, $httpProvider, $locationProvider){
    $locationProvider.html5Mode(true);

    $routeProvider.when('/', {
        templateUrl: 'js/home/home.html',
        controller: 'home',
        controllerAs: 'home'
    }).when('/login', {
        templateUrl: 'js/navigation/login.html',
        controller: 'navigation',
        controllerAs: 'nav'
    }).when('/admin', {
        templateUrl: 'js/admin/admin.html',
        controller: 'admin',
        controllerAs: 'admin'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}