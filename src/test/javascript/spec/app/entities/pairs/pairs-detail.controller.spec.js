'use strict';

describe('Controller Tests', function() {

    describe('Pairs Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPairs;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPairs = jasmine.createSpy('MockPairs');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pairs': MockPairs
            };
            createController = function() {
                $injector.get('$controller')("PairsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'inDepth2App:pairsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
