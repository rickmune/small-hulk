define([
  'jquery',
  'underscore',
  'backbone',
  'backgrid',
  'backgrid-paginator',
  'usercollection',
  'jqueryui',
  'text!templates/userslist_template.html',
  'backbone-stickit',
  'backgrid-filter',
  'userformview'
], function ($, _, Backbone, backgrid, backgridpaginator, userCollection, jqueryui, userListTemplate, stickit, backgridfilter, userFormView) {
    var model = Backbone.Model.extend({
        idAttribute: "Id",
        defaults: {
            name: "Harry Potter",
            
        },
        urlRoot: "/api/supervisorinfo/locationrespondent"
    });
    var userListView = Backbone.View.extend({
        el: $('#page_container'),
        initialize: function () {
            this.template = _.template(userListTemplate);
        },
       
        events: {
            'click #btn-create': 'openPopUp',
            
        },
        refresh: function (e) {
            debugger;
            this.render();
        },
        
        openPopUp: function (e) {
            e.preventDefault();
            var userform = new userFormView({ parent: this });
           // refresh
            this.listenTo(userform, 'refreshevent', this.refresh);
            userform.render();
        },
        bindings: {
            '#name': 'name',
           }
        ,
        render: function () {
            this.model = new model();
            this.$el.html(this.template);
            this.showView();
            this.stickit();
            return this;
              
           
        },
        showView: function () {
            var self = this;
            self.pagedcollection = new userCollection();
            var grid = self.setupGrid().grid;
            self.$el.find("#grid").html(grid.render().$el);
            self.$el.find("#grid-paginator").html(self.setupGrid().paginator.render().$el);
            self.$el.find("#list-toolbar").prepend(self.setupGrid().filter.render().$el);
            self.pagedcollection.fetch({ reset: true });
        },
        
        setupGrid: function () {
           var self = this;
            var columns = [{
                name: "Username",
                label: "Username",
                cell: "string",
                editable: false,
            }, {
                name: "Fullname",
                label: "Fullname",
                cell: "string",
                editable: false,
            },
             {
                 name: "Email",
                 label: "Email",
                 cell: "string",
                 editable: false,
             },
            {
                name: "PhoneNumber",
                label: "PhoneNumber",
            cell: "string",
            editable: false,
            }
            ];
            var serverSideFilter = new backgrid.Extension.ServerSideFilter({
                collection: self.pagedcollection,
                name: "search",
                placeholder: "Search ...",
            });
            serverSideFilter.$el.css({ float: "right" });
            var paginator = new  backgrid.Extension.Paginator({
                collection: self.pagedcollection
            });
            var grid = new backgrid.Grid({
                columns: columns,
                collection: self.pagedcollection
            });
            return {
                grid: grid,
                paginator: paginator,
                filter:serverSideFilter
            };
        }
        
    });
    // Our module now returns our view
    return userListView;
});