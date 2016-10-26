(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('UserAttirbutesController', UserAttirbutesController);

    UserAttirbutesController.$inject = ['$scope', '$state', 'UserAttirbutes'];

    function UserAttirbutesController ($scope, $state, UserAttirbutes) {
        var vm = this;
        vm.userAttirbutes = [];
        vm.loadAll = function() {
            UserAttirbutes.query(function(result) {
                vm.userAttirbutes = result;
            });
        };

        vm.loadAll();
        
    }
})();
