'use strict';

describe('Controller Tests', function() {

    describe('UserAttirbutes Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUserAttirbutes, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUserAttirbutes = jasmine.createSpy('MockUserAttirbutes');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UserAttirbutes': MockUserAttirbutes,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("UserAttirbutesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'inDepth2App:userAttirbutesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
