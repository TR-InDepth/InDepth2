(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-attirbutes', {
            parent: 'entity',
            url: '/user-attirbutes',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'inDepth2App.userAttirbutes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-attirbutes/user-attirbutes.html',
                    controller: 'UserAttirbutesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userAttirbutes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-attirbutes-detail', {
            parent: 'entity',
            url: '/user-attirbutes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'inDepth2App.userAttirbutes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-attirbutes/user-attirbutes-detail.html',
                    controller: 'UserAttirbutesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userAttirbutes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserAttirbutes', function($stateParams, UserAttirbutes) {
                    return UserAttirbutes.get({id : $stateParams.id});
                }]
            }
        })
        .state('user-attirbutes.new', {
            parent: 'user-attirbutes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-attirbutes/user-attirbutes-dialog.html',
                    controller: 'UserAttirbutesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lat: null,
                                longitude: null,
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-attirbutes', null, { reload: true });
                }, function() {
                    $state.go('user-attirbutes');
                });
            }]
        })
        .state('user-attirbutes.edit', {
            parent: 'user-attirbutes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-attirbutes/user-attirbutes-dialog.html',
                    controller: 'UserAttirbutesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserAttirbutes', function(UserAttirbutes) {
                            return UserAttirbutes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-attirbutes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-attirbutes.delete', {
            parent: 'user-attirbutes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-attirbutes/user-attirbutes-delete-dialog.html',
                    controller: 'UserAttirbutesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserAttirbutes', function(UserAttirbutes) {
                            return UserAttirbutes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-attirbutes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
