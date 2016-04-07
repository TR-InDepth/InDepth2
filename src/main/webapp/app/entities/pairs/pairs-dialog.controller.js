(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('PairsDialogController', PairsDialogController);

    PairsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pairs'];

    function PairsDialogController ($scope, $stateParams, $uibModalInstance, entity, Pairs) {
        var vm = this;
        vm.pairs = entity;
        vm.load = function(id) {
            Pairs.get({id : id}, function(result) {
                vm.pairs = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('inDepth2App:pairsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.pairs.id !== null) {
                Pairs.update(vm.pairs, onSaveSuccess, onSaveError);
            } else {
                Pairs.save(vm.pairs, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
