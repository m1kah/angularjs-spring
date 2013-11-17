
// http://stackoverflow.com/questions/202605/repeat-string-javascript
String.prototype.repeat = function(count) {
    if (count < 1) return '';
    var result = '', pattern = this.valueOf();
    while (count > 0) {
        if (count & 1) result += pattern;
        count >>= 1, pattern += pattern;
    }
    return result;
};

restaurantApp.controller('restaurantListCtrl', ['$scope', 'Restaurant', function($scope, Restaurant) {
    var restaurants = Restaurant.query(function(restaurants) {
        for (i in restaurants) {
            restaurant = restaurants[i];
            if (restaurant.rating) {
                restaurant.ratingSymbols = "★".repeat(restaurant.rating);
            } else {
                restaurant.ratingSymbols = "☆";
            }
        }
    });

    $scope.restaurants = restaurants;
}]);
