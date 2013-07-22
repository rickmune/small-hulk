define([
  'underscore',
  'backbone',
   'backbone-pageable',
  // Pull in the Model module from above
  'usermodel'
], function (_, Backbone, Backbonepageable, userModel) {
   
    Backbone.PageableCollection = Backbonepageable;
    var userCollection = Backbone.PageableCollection.extend({
        model: userModel,
        url: function () {
            return 'api/phone/masterdata/users';
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
   
    // You don't usually return a collection instantiated
    return userCollection;
});