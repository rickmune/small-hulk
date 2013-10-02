define([
  // These are path alias that we configured in our bootstrap
  'jquery',     // lib/jquery/jquery
  'underscore', // lib/underscore/underscore
  'backbone' ,   // lib/backbone/backbone
  'text!templates/categorylist_template.html',
   'backbone-pageable',
   'backgrid',
  'backgrid-paginator',
   'jqueryui',
   'backgrid-filter',
   'apputil',
   'categoryformview'
], function ($, _, Backbone, homeTemplate, backbonepageable, backgrid, backgridpaginator, jqueryui, backgridfilter, apputil,categoryFormView) {
    var model = Backbone.Model.extend({
        idAttribute: "Id",
        defaults: {
            Name: "",
            Description:""
        },
        url: function () {
          return  "/api/phone/masterdata/addcategory";
        }
    });
    Backbone.PageableCollection = backbonepageable;
    var collection = Backbone.PageableCollection.extend({
        model: model,
        initialize: function (options) {
            this.accountId = options.accountId;
        },
        url: function () {
            return '/api/phone/masterdata/categories/' + this.accountId;
        },
        state: {
            pageSize: 10,
            firstPage: 1,
        },
        parse: function (response) {
            var tags = response.Data;
            var newState = _.clone(this.state);
            newState.totalRecords = response.RecordCount;
            this.state = this._checkState(newState);
            return tags;
        },
    });

    var categoryViewModel = Backbone.View.extend({
        el: $('#page_container'),
        initialize: function () {
            var data = {};
            this.template = _.template(homeTemplate, data);
        },
        events: {
            'click #btn-create-category': 'openNewPopUp',

        },
        openNewPopUp: function (e) {
            e.preventDefault();
            var dialog = new categoryFormView();
            this.listenTo(dialog, 'refreshcategories', this.showView);
            dialog.render();
        },
        openEditPopUp: function (option) {
            var dialog = new categoryFormView({ model: option.model });
            this.listenTo(dialog, 'refreshcategories', this.showView);
            dialog.render();
        },
        render: function () {
            this.$el.html(this.template);
            this.showView();
            return this;
        },
        deleteCategory:function (option) {
            alert("Record deleted successfully");
        },
        showView: function () {
            
            var self = this;
            var user = apputil.getUser();
            self.pagedcollection = new collection({ accountId: user.AccountId });
            var grid = self.setupGrid().grid;
            self.$el.find("#grid").html(grid.render().$el);
            self.$el.find("#grid-paginator").html(self.setupGrid().paginator.render().$el);
            self.$el.find("#grid-filter").html(self.setupGrid().filter.render().$el);
            self.pagedcollection.fetch({reset: true});
        },
        setupGrid: function () {
            var self = this;
            var columns = [
                { name: "Name", label: "Name", cell: "string", editable: false, },
                { name: "Description", label: "Description", cell: "string", editable: false, },
                { 
                    name: '',
                    label: 'Action',
                    cell: Backgrid.Cell.extend({
                        template: _.template('<a href="#" class="btn btn-small editrow" ><i class="icon-pencil"></i> </a><a href="#" class="btn btn-small deleterow" ><i class="icon-trash"></i> </a>'),
                        events: {
                            "click .editrow": "editRow",
                            "click .deleterow": "deleteRow"
                        },
                        deleteRow: function (e) {
                            debugger;
                            e.preventDefault();
                            self.deleteCategory({ model: this.model });
                        },
                        editRow: function (e) {
                            e.preventDefault();
                            self.openEditPopUp({ model: this.model });
                        },
                        render: function () {
                            this.$el.html(this.template());
                            this.delegateEvents();
                            return this;
                        }
                    })
                }
            ];
            var serverSideFilter = new backgrid.Extension.ServerSideFilter({
                collection: self.pagedcollection,
                name: "search",
                placeholder: "Search ...",
            });
            serverSideFilter.$el.css({ float: "right" });
            var paginator = new backgrid.Extension.Paginator({
                collection: self.pagedcollection
            });
            var grid = new backgrid.Grid({
                columns: columns,
                collection: self.pagedcollection
            });
            return {
                grid: grid,
                paginator: paginator,
                filter: serverSideFilter
            };
        }
       
    });
    // Our module now returns our view
    return categoryViewModel;
});