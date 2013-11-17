
var restaurantServices = angular.module('restaurantServices', ['ngResource']);

restaurantServices.factory('Restaurant', ['$resource',
    function($resource) {
        return $resource('api/restaurants.json', {}, {
            query: { method: 'GET', params: {}, isArray:true }
        });
    }]);
