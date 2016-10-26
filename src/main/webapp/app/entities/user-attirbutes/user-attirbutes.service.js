(function() {
    'use strict';
    angular
        .module('inDepth2App')
        .factory('UserAttirbutes', UserAttirbutes);

    UserAttirbutes.$inject = ['$resource'];

    function UserAttirbutes ($resource) {
        var resourceUrl =  'api/user-attirbutes/:id';

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
