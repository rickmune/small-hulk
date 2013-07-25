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
                RegistrationTypeId: '2'
                
            },
            validate: function (attrs, options) {
                var errors = [];
                var email_filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                var phone_filter = /^(\d{4}-\d{3}-\d{3})+$/;

                if (!email_filter.test(attrs.Email) && attrs.RegistrationTypeId == '2') {
                    errors.push({ name: 'email', message: 'Please fill valid email field.' });
                }
                if (!phone_filter.test(attrs.PhoneNumber) && attrs.RegistrationTypeId == '1') {
                    errors.push({ name: 'phonenumber', message: 'Please fill valid mobile  number field (xxxx-xxx-xxx).' });
                }
                if (!attrs.Fullname) {
                    errors.push({ name: 'fullname', message: 'Please fill Fullname field.' });
                }
                if (!attrs.Username) {
                    errors.push({ name: 'username', message: 'Please fill username field.' });
                }
                if (!attrs.RegistrationTypeId) {
                    errors.push({ name: 'registrationTypeId', message: 'Please Select contact type' });
                }
               
                if (attrs.Username) {
                    $.ajax({
                        url: '/api/phone/user/checkuseravailabilty',
                        async: false,
                        type: "post",
                        data: { username: attrs.Username },
                        success: function (data) {
                            if (data)
                                errors.push({ name: 'username', message: 'Username already taken.' });
                        }
                    });
                }

                return errors.length > 0 ? errors : false;
            },
            urlRoot: "api/phone/masterdata/adduser",
            url: "api/phone/masterdata/adduser",
          
        });
       
        var userFormView = Backbone.View.extend({
           
            initialize: function (option) {
                this.$el = $('#modal-userform');
                this.template = _.template(userformtemplate);
                this.model = new Model();
                var self = this;
                this.model.on('change', function () {
                    appUtil.hideErrors(self);
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
                '#registrationTypeId': 'RegistrationTypeId'
                
            },
            refresh:function () {
                debugger;
            },
            
            render: function () {
                var self = this;
                this.$el.html(this.template()).dialog({
                    resizable: false,
                    height: 500,
                    width: 600,
                    modal: true,
                    title:'User Form Dialog',
                    buttons: {
                        "Save": function () {
                            debugger;
                            if (!self.model.isValid()) {
                                appUtil.showErrors(self, self.model.validationError);
                                return;
                            }
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