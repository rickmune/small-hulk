define([
  'jquery',
  'underscore',
  'backbone',
  // Using the Require.js text! plugin, we are loaded raw text
  // which will be used as our views primary template
  'text!templates/userslist_template.html'
], function ($, _, Backbone, userListTemplate) {
    var UserListView = Backbone.View.extend({
        el: $('#page_container'),
        render: function () {
            // Using Underscore we can compile our template with data
            var data = {};
            var compiledTemplate = _.template(userListTemplate, data);
            // Append our compiled template to this Views "el"
            this.$el.append(compiledTemplate);
        },
        display: function () {
            alert("sdsdsd");
            this.render();
        }
    });
    // Our module now returns our view
    return UserListView;
});