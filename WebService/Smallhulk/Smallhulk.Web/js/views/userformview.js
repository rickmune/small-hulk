define([
        'jquery',
        'underscore',
        'backbone',
        'jqueryui',
        'apputil',
        'text!templates/userform_template.html'
        
], function($, _, backbone, jqueryui,appUtil, userformtemplate) {

    // user form view
    
        var Model = Backbone.Model.extend({
           
            defaults: {
                Username: "",
                Fullname: '',
                Email: '',
                PhoneNumber: '',
                AccountId: '',
                UserTypeId: 1,
               
                
            },
            validate: function (attrs, options) {
                if (attrs.Username == null || attrs.Username=='') {
                    return "enter username";
                }
            },
            urlRoot: "api/phone/masterdata/adduser",
            url: "api/phone/masterdata/adduser",
          
        });
       
        var userFormView = Backbone.View.extend({
           
            initialize: function (option) {
                this.$el = $('#modal-userform');
                this.template = _.template(userformtemplate);
                this.model = new Model();
                this.model.on("invalid", function (model, error) {
                    alert(model.get("username") + " " + error);
                });
                this.parent = option.parent;
                
            },
            events: {
                'refreshevent': 'refresh'
            },
            bindings: {
                '#username': 'Username',
                '#fullname': 'Fullname',
                '#email': 'Email',
                '#phonenumber': 'PhoneNumber',
                '#accountId': 'AccountId',
                '#usertypeId': 'UserTypeId',
                
            },
            refresh:function () {
                debugger;
            },
            
            render: function () {
                var self = this;
                this.$el.html(this.template()).dialog({
                    resizable: false,
                    height: 400,
                    width: 600,
                    modal: true,
                    title:'User Form Dialog',
                    buttons: {
                        "Save": function () {
                            var id = appUtil.Guid();
                            if(self.model.isNew) {
                                self.model.set({ Id: id });
                            }
                           
                            var password = appUtil.md5("1234").toString();
                            debugger;
                            var loggedinuser = appUtil.getUser();
                            self.model.set({ Password: password, AccountId: loggedinuser.AccountId });
                            var dialog = $(this);
                            self.model.sync("create", self.model, {
                                success: function (model, response) {
                                    if (model.Status == false) {
                                        alert(model.Info);
                                        return;
                                    } else {
                                        dialog.dialog("close");
                                        self.trigger('refreshevent');
                                       
                                    }
                                },
                                error: function (model, response) {
                                    debugger;
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
        return userFormView;
    });