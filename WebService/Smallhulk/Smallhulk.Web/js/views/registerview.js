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
        },
        validate: function (attrs, options) {
            if (attrs.Username == null || attrs.Username == '') {
                return "enter username";
            }
        },
        url: "/api/phone/masterdata/register",

    });

    var registerViewModel = Backbone.View.extend({
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
            '#confirmpassword':'ConfirmPassword'
        },
        register: function (e) {
            e.preventDefault();
           
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
            this.model = new registerModel();
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