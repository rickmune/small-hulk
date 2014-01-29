define([
        'jquery',
        'underscore',
        'md5',
        'backbone',
        'backbone-pageable',
        'backgrid',
        'backgrid-paginator',
        'jqueryui',
        'backgrid-filter'
], function ($, _, Md5, backbone, backbonepageable, backgrid, backgridpaginator, jqueryui, backgridfilter) {
    Backbone.PageableCollection = backbonepageable;
    
    var guid = function() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    },
        showErrors = function(view, errors) {
           
            _.each(errors, function(error) {
               
                var controlGroup = view.$('.' + error.name);
                controlGroup.addClass('has-error');
                controlGroup.find('.help-block').text(error.message);
            }, this);
        },
        hideErrors = function(view) {
            view.$('.col-lg-8').removeClass('has-error');
            view.$('.help-block').text('');
        },
        showErrorsAlert = function (errors) {
            var msg = "";
            _.each(errors, function (error) {
                msg += error.message+"\n";
            }, this);
            alert(msg);
        };
    
        
        
    
        return {
            Guid: guid,
            showErrors: showErrors,
            hideErrors: hideErrors,
            showErrorsAlert:showErrorsAlert,
            date_filter: /^(([0-9])|([0-2][0-9])|([3][0-1]))\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\-\d{4}$/,//dd-MMM-yyyy
            email_filter: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
            phone_filter: /^(\d{4}-\d{3}-\d{3})+$/,
            time_filter: /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/,
            number_filter: /^\d{1,4}$/,
           
        };

    });