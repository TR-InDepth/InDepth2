(function() {
    'use strict';

    angular
        .module('inDepth2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pairs', {
            parent: 'entity',
            url: '/pairs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'inDepth2App.pairs.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pairs/pairs.html',
                    controller: 'PairsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pairs');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pairs-detail', {
            parent: 'entity',
            url: '/pairs/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'inDepth2App.pairs.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pairs/pairs-detail.html',
                    controller: 'PairsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pairs');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pairs', function($stateParams, Pairs) {
                    return Pairs.get({id : $stateParams.id});
                }]
            }
        })
        .state('pairs.new', {
            parent: 'pairs',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pairs/pairs-dialog.html',
                    controller: 'PairsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pairs', null, { reload: true });
                }, function() {
                    $state.go('pairs');
                });
            }]
        })
        .state('pairs.edit', {
            parent: 'pairs',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pairs/pairs-dialog.html',
                    controller: 'PairsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pairs', function(Pairs) {
                            return Pairs.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pairs', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pairs.delete', {
            parent: 'pairs',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pairs/pairs-delete-dialog.html',
                    controller: 'PairsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pairs', function(Pairs) {
                            return Pairs.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pairs', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
