define([
  // These are path alias that we configured in our bootstrap
  'jquery',     // lib/jquery/jquery
  'underscore', // lib/underscore/underscore
  'backbone',   // lib/backbone/backbone
   'backbone-stickit',
    'apputil',
  'text!templates/register_template.html'
], function ($, _, Backbone, stickit,apputil, registerTemplate) {
    var registerModel = Backbone.Model.extend({
        idAttribute: "Id",
        defaults: {
            Username: "",
            Fullname: '',
            Email: '',
            PhoneNumber: '',
            AccountId: '',
            UserTypeId: 1,
          RegistrationTypeId :'2'
        },
        validate: function (attrs, options) {
            var errors = [];
            var email_filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            var phone_filter=/^(\d{4}-\d{3}-\d{3})+$/;
           
            if (!email_filter.test(attrs.Email) && attrs.RegistrationTypeId=='2') {
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
            if (!attrs.Password) {
                errors.push({ name: 'password', message: 'Please fill Password field.' });
            }
            if (attrs.Password != attrs.ConfirmPassword) {
                errors.push({ name: 'confirmpassword', message: 'Password not matching.' });
            }
            if (attrs.Username) {
                $.ajax({
                    url: '/api/phone/user/checkuseravailabilty/' + attrs.Username,
                    async: false,
                    success: function (data) {
                        if (data)
                            errors.push({ name: 'username', message: 'Username already taken.' });
                    }
                });
            }
           
            return errors.length > 0 ? errors : false;
        },
        url: "/api/phone/masterdata/register",

    });

    var registerViewModel = Backbone.View.extend({
        initialize: function () {
            var self = this;
          this.model = new registerModel();
          this.model.on('change', function () {
              apputil.hideErrors(self);
            });
        },
        el: $('#page_container'),
        events: {
            'click #btn-submit': 'register',
            
        },
        bindings: {
            '#username': 'Username',
            '#fullname': 'Fullname',
            '#password': 'Password',
            '#email': 'Email',
            '#phonenumber': 'PhoneNumber',
            '#accountId': 'AccountId',
            '#usertypeId': 'UserTypeId',
            '#confirmpassword': 'ConfirmPassword',
            '#registrationTypeId':'RegistrationTypeId'
        },
        
        register: function (e) {
            e.preventDefault();
            if (!this.model.isValid()) {
                apputil.showErrors(this, this.model.validationError);
                return;
            }
            var password = this.model.get('Password');
            this.model.set({ Id: apputil.Guid() });
            this.model.set({ AccountId: apputil.Guid() });
            this.model.set({ Password: apputil.md5(password).toString() });
            var model = this.model;
            this.model.sync("create", this.model, {
                success: function (model, response) {
                    
                    if (model.Status == false) {
                        alert(model.Info);
                        this.model.set({ Password: '' });
                        return;
                    } else {
                        alert('You Register successfully');
                        window.location.replace("/account/login");
                       // Backbone.history.navigate("/home/inge",true);
                    }
                },
                error: function (model, response) {
                    debugger;
                    
                    alert("fail");
                }
            });
            

            console.log('Register');
        },
        render: function () {
            
            // Using Underscore we can compile our template with data
            var data = {};
            var compiledTemplate = _.template(registerTemplate, data);
            // Append our compiled template to this Views "el"
            this.$el.html(compiledTemplate);
            this.stickit();
        },
       
    });
    // Our module now returns our view
    return registerViewModel;
});