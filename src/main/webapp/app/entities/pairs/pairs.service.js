(function() {
    'use strict';
    angular
        .module('inDepth2App')
        .factory('Pairs', Pairs);

    Pairs.$inject = ['$resource'];

    function Pairs ($resource) {
        var resourceUrl =  'api/pairs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
