define([
  'jquery',
  'underscore',
  'backbone',
  'userlistviewmodel'
], function ($, _, Backbone, Session, UserListView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '/users': 'showUsers',

            // Default
            '*actions': 'defaultAction'
        }
    });

    var initialize = function () {
        var app_router = new AppRouter;
        // 'views/users/list'
        app_router.on('showUsers', function () {
            var userListView = new UserListView();
            userListView.render();
        });
        app_router.on('defaultAction', function (actions) {
            // We have no matching route, lets just log what the URL was
            console.log('No route:', actions);
        });
        Backbone.history.start();
    };
    return {
        initialize: initialize
    };
});