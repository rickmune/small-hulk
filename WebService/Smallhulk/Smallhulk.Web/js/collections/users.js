define([
  'underscore',
  'backbone',
  // Pull in the Model module from above
  'userModel'
], function (_, Backbone, userModel) {
    var UserCollection = Backbone.Collection.extend({
        model: userModel
    });
    // You don't usually return a collection instantiated
    return UserCollection;
});