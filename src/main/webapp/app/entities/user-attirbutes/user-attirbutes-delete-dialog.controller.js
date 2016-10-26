(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('UserAttirbutesDeleteController',UserAttirbutesDeleteController);

    UserAttirbutesDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserAttirbutes'];

    function UserAttirbutesDeleteController($uibModalInstance, entity, UserAttirbutes) {
        var vm = this;
        vm.userAttirbutes = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            UserAttirbutes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
