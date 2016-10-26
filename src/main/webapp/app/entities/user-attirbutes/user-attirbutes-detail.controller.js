(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('UserAttirbutesDetailController', UserAttirbutesDetailController);

    UserAttirbutesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'UserAttirbutes', 'User'];

    function UserAttirbutesDetailController($scope, $rootScope, $stateParams, entity, UserAttirbutes, User) {
        var vm = this;
        vm.userAttirbutes = entity;
        vm.load = function (id) {
            UserAttirbutes.get({id: id}, function(result) {
                vm.userAttirbutes = result;
            });
        };
        var unsubscribe = $rootScope.$on('inDepth2App:userAttirbutesUpdate', function(event, result) {
            vm.userAttirbutes = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
