define([
  'jquery',
  'underscore',
  'backbone',
    'apputil',
   'homeviewmodel',
  'userlistviewmodel',
  'registerformview',
  'categorylistviewmodel',
   'productlistviewmodel',
  'routelistviewmodel',
  'customerlistviewmodel'
], function ($,
    _, Backbone,
    appUtil,
    HomeViewModel,
    UserListViewModel,
    RegisterFormView,
    CategoryListview,
    ProductListView,
    RouteListView,
    CustomerListView
) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'index',
            'users': 'showUsers',
            'register': 'register',
            'categories': 'categories',
            'products': 'products',
            'listroutes': 'listroutes',
            'customers':'customers'

           
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
           // debugger;
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
        
        appRouter.on('route:categories', function () {
            
            appRouter.getLoggedInUser();
            var categoryListview = new CategoryListview();
            categoryListview.render();
            console.log('Category list render');
        });
        appRouter.on('route:products', function () {
            
            appRouter.getLoggedInUser();
            var productListView = new ProductListView();
            productListView.render();
            console.log('Product list render');
        });
        appRouter.on('route:listroutes', function () {
            debugger;
            appRouter.getLoggedInUser();
            var routeListView = new RouteListView();
            routeListView.render();
            console.log('routes list render');
        });
        appRouter.on('route:customers', function () {
            debugger;
            appRouter.getLoggedInUser();
            var customerListView = new CustomerListView();
            customerListView.render();
            console.log('customers list render');
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