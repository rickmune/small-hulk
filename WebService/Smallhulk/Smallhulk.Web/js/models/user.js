define([
  'underscore',
  'backbone'
], function (_, Backbone) {
    var UserModel = Backbone.Model.extend({
        idAttribute: "Id",
        defaults: {
            name: "Harry Potter",
        }
    });
    // Return the model for the module
    return UserModel;
});