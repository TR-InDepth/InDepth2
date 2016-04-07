(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .controller('PairsController', PairsController);

    PairsController.$inject = ['$scope', '$state', 'Pairs'];

    function PairsController ($scope, $state, Pairs) {
        var vm = this;
        vm.pairs = [];
        vm.loadAll = function() {
            Pairs.query(function(result) {
                vm.pairs = result;
            });
        };

        vm.loadAll();
        
    }
})();
