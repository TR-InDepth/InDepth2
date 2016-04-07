(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('PairsDetailController', PairsDetailController);

    PairsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Pairs'];

    function PairsDetailController($scope, $rootScope, $stateParams, entity, Pairs) {
        var vm = this;
        vm.pairs = entity;
        vm.load = function (id) {
            Pairs.get({id: id}, function(result) {
                vm.pairs = result;
            });
        };
        var unsubscribe = $rootScope.$on('inDepth2App:pairsUpdate', function(event, result) {
            vm.pairs = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
