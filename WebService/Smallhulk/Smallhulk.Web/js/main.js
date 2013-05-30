require.config({
    shim: {
        'underscore': {
            exports: '_'
        },
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'backbone-pageable': {
            deps: ['underscore', 'backbone'],
            exports: 'PageableCollection'
        },
        'bootstrap': {
            deps: ['jquery'],
            exports: 'bootstrap'
        },
        'jRating': {
            deps: ['jquery'],
            exports: 'jRating'
        },
        "backgrid": {
            deps: ['jquery', 'backbone', 'underscore'],
            exports: "Backgrid"
        },
        "backgrid-paginator": {
            deps: ['backgrid', 'backbone-pageable']
        },
        "backbone-stickit": {
            deps: ['backbone']
        },
        'maps': { exports: "Microsoft.Maps" }
    },
    paths: {
        jquery: 'libs/jquery/jquery-1.8.2.min',
        underscore: 'libs/underscore/underscore',
        backbone: 'libs/backbone/backbone',
        bootstrap: 'libs/bootstrap/bootstrap.min',
        text: 'libs/text/text',
        app: 'app',
        router: 'router',
       // models
        usermodel: 'models/user',
        //collections
        usercollection: 'collections/users',
        
        //views
        userlistviewmodel: 'views/userslistview',
        
    }

});

require(['app'], function (App) {
    // The "app" dependency is passed in as "App"
    App.initialize();
    window.pageload();
});