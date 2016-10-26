(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('UserAttirbutesDialogController', UserAttirbutesDialogController);

    UserAttirbutesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserAttirbutes', 'User'];

    function UserAttirbutesDialogController ($scope, $stateParams, $uibModalInstance, entity, UserAttirbutes, User) {
        var vm = this;
        vm.userAttirbutes = entity;
        vm.users = User.query();
        vm.load = function(id) {
            UserAttirbutes.get({id : id}, function(result) {
                vm.userAttirbutes = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('inDepth2App:userAttirbutesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.userAttirbutes.id !== null) {
                UserAttirbutes.update(vm.userAttirbutes, onSaveSuccess, onSaveError);
            } else {
                UserAttirbutes.save(vm.userAttirbutes, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
