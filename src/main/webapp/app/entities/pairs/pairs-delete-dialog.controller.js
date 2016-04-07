(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('PairsDeleteController',PairsDeleteController);

    PairsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pairs'];

    function PairsDeleteController($uibModalInstance, entity, Pairs) {
        var vm = this;
        vm.pairs = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Pairs.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
