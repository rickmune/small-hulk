﻿define([
  'jquery',
  'underscore',
  'md5'
], function ($, _, Md5) {
    var User = {};
   var guid= function() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    },
    getUser = function() {
        return this.User;
    },
    setUser = function (user) {
        return this.User=user;
    };
    
       
    return {
        Guid: guid,
        md5: CryptoJS.MD5,
        getUser: getUser,
        setUser: setUser,
    };
    
});