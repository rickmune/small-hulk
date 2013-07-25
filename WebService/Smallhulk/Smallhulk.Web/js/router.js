define([
  'jquery',
  'underscore',
  'backbone',
    'apputil',
   'homeviewmodel',
  'userlistviewmodel',
  'registerformview'
  
], function ($, _, Backbone,appUtil, HomeViewModel, UserListViewModel, RegisterFormView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'index',
            'users': 'showUsers',
            'register': 'register',


            // Default
            '*actions': 'defaultAction'
        },
        initialize: function () {
            
            this.user = null;
        },
        getLoggedInUser: function () {
            var router = this;
            if (router.user == null) {
                var login = new User();
                login.fetch({
                    success: function (result, response) {
                        
                        router.user = response;
                        appUtil.setUser(router.user);
                        
                    },
                    error: function (model, response) {
                        appUtil.setUser(null);
                        router.user = null;
                        if (window.location.pathname != "/account/login")
                            window.location.replace("/account/login");
                    }                    
                });
            }
        },
        
    });
    var User = Backbone.Model.extend({
        url: "/api/phone/user/login",
    });

    var initialize = function () {
       
        var appRouter = new AppRouter;
       
        // 'views/users/list'
      
        appRouter.on("route:index", function () {
            appRouter.getLoggedInUser();
            var homeViewModel = new HomeViewModel();
            homeViewModel.render();
            console.log('Home render');
        });
        appRouter.on('route:showUsers', function () {
            appRouter.getLoggedInUser();
            var userListView = new UserListViewModel();
            userListView.render();
            //userListView.showView();
            console.log('user render');
        });
        appRouter.on('route:register', function () {
            var registerformView = new RegisterFormView();
            registerformView.render();
            console.log('Register render');
        });
        
        appRouter.on('defaultAction', function (actions) {
            appRouter.getLoggedInUser();
            // We have no matching route, lets just log what the URL was
            console.log('No route:', actions);
        });
       // appRouter.getLoggedInUser();
        Backbone.history.start();
        console.log('Router initialized');
        return appRouter;
    };
    return {
        initialize: initialize
    };
});