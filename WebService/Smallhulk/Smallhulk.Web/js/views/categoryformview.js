define([
        'jquery',
        'underscore',
        'backbone',
        'jqueryui',
        'apputil',
        'text!templates/categoryform_template.html'
        
], function($, _, backbone, jqueryui,appUtil, userformtemplate) {

    // user form view
    
        var Model = Backbone.Model.extend({
           
            defaults: {
                Name: "",
                Description: '',
                AccountId: '',
                UserTypeId: 1,
            },
            validate: function (attrs, options) {
                var errors = [];
                if (!attrs.Name) {
                    errors.push({ name: 'name', message: 'Please fill Name field.' });
                }
                return errors.length > 0 ? errors : false;
            },
            url: "/api/phone/masterdata/addcategory",
          
        });
       
        var categoryFormView = Backbone.View.extend({
            initialize: function (option) {
                this.$el = $('#modal-categoryform');
                this.template = _.template(userformtemplate);
                this.model = new Model();
                
                if (option!=null && option.model != null) {
                    this.model = option.model;
                }
                this.model.url = "/api/phone/masterdata/addcategory";
                var self = this;
                this.model.on('change', function () {
                    appUtil.hideErrors(self);
                });
                
                
            },
            events: {
                'refreshcategories': 'refresh'
            },
            bindings: {
                '#name': 'Name',
                '#description': 'Description',
            },
            
            
            render: function () {
                
                var self = this;
                this.$el.html(this.template()).dialog({
                    resizable: false,
                    height: 300,
                    width: 500,
                    modal: true,
                    title:'Category Form Dialog',
                    buttons: {
                        "Save": function () {
                          
                            if (!self.model.isValid()) {
                                appUtil.showErrors(self, self.model.validationError);
                                return;
                            }
                            var id = appUtil.Guid();
                            if(self.model.id==null) {
                                self.model.set({ Id: id });
                            }
                            var loggedinuser = appUtil.getUser();
                            self.model.set({ AccountId: loggedinuser.AccountId });
                            var dialog = $(this);
                            self.model.sync("create", self.model, {
                                success: function (model, response) {
                                    if (model.Status == false) {
                                        alert(model.Info);
                                        return;
                                    } else {
                                        dialog.dialog("close");
                                        self.trigger('refreshcategories');
                                       
                                    }
                                },
                                error: function (model, response) {
                                   
                                    alert("fail");
                                }
                            });
                           
                        },
                        "Cancel": function () {
                            $(this).dialog("close");
                        }
                    }
                });
                this.stickit();
                return this;
            },

        });
        return categoryFormView;
    });