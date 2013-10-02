define([
  // These are path alias that we configured in our bootstrap
  'jquery',     // lib/jquery/jquery
  'underscore', // lib/underscore/underscore
  'backbone' ,   // lib/backbone/backbone
  'text!templates/customerlist_template.html'
], function ($, _, Backbone,homeTemplate) {
    var categoryViewModel = Backbone.View.extend({
        el: $('#page_container'),
        render: function () {
            // Using Underscore we can compile our template with data
            var data = {};
            var compiledTemplate = _.template(homeTemplate, data);
            // Append our compiled template to this Views "el"
            this.$el.html(compiledTemplate);
        },
       
    });
    // Our module now returns our view
    return categoryViewModel;
});