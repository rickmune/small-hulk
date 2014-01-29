require.config({
    //baseUrl: '<%= @Url.Content("~")  %>',
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
            exports: 'Backbone.PageableCollection'
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
        'maps': { exports: "Microsoft.Maps" },
        "backgrid-filter": {
            deps: ['backgrid', "lunr"]
        },
       
       
        
        
    },
    paths: {
        jquery: 'libs/jquery/jquery-1.9.1',
        underscore: 'libs/underscore/underscore',
        backbone: 'libs/backbone/backbone',
        bootstrap: 'libs/bootstrap/bootstrap.min',
        backgrid: 'libs/backgrid/backgrid',
        'backbone-pageable': 'libs/backbone/backbone-pageable',
        'backgrid-paginator': 'libs/backgrid/backgrid-paginator',
        'backgrid-filter': 'libs/backgrid/backgrid-filter',
        'lunr': 'libs/lunr/lunr',
        'backbone-stickit': 'libs/backbone/backbone.stickit',
        'md5': 'libs/util/md5',
        //jqueryui: 'libs/jquery/jquery-ui-1.8.24',
        jqueryui: 'libs/jquery/jquery-ui-1.10.3.custom.min',
        
        text: 'libs/text/text',
        app: 'app',
        apputil: 'apputil',
        router: 'router',
       
        addupdateformitemviewmodel: 'views/addupdateformitem',
        addupdateformitemsinglechoice: 'views/addupdateformitemsinglechoice',

        
    }

});

require(['app'], function (App) {
    // The "app" dependency is passed in as "App"
    App.initialize();
    //var Common = App;
    console.log("Application started.....");
    window.pageload();
});
