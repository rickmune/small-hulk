define([
        'jquery',
        'underscore',
        'backbone',
        'jqueryui',
        'apputil',
 'text!templates/addupdateformitemsinglechoice_template.html'

], function ($, _, backbone, jqueryui, apputil, template) {

    // user form view

   
   var respondent = Backbone.Model.extend({});
   var respondentList = Backbone.Collection.extend({
       model: respondent,
       initialize: function (option) {
           this.formid = option.formid;
       },
       url: function () {
          
           return window.app_baseurl + 'api/client/form/GetFormRespodent?formId=' + this.formid;
       },
       parse: function (response) {
           var tags = response.Data;
           return tags;
       },
   });
    var formItemTextModel = Backbone.Model.extend({
        initialize: function (option) {
            this.respondentList = new respondentList({ formid: option.formid });
        }
    });
    var formViewSingleChoice = Backbone.View.extend({
        initialize: function (option) {
            this.$el = $('#itemcontentholder');
            this.cid = "view_addupdateformitemtext";
            this.template = _.template(template);
            this.model = new formItemTextModel({ formid: option.formid });
            if (option != null && option.ItemType != null) {
                this.ItemType = option.ItemType;
            }
            this.PossibleOptions = new Array();
        },
        events: {
            'click #btn-save-formitemtext': 'save',
            'click #btn-add-formitem': 'addanswer',
            'click .btn-deleterow': 'deleteanswer'

        },
        deleteanswer: function (e) {
            e.preventDefault();
            var id = $(e.currentTarget).data('name');
           
            this.PossibleOptions = _.without(this.PossibleOptions, _.findWhere(this.PossibleOptions, { Value: id }));

            this.reloadAnswerOption();
           
        },
        addanswer: function (e) {
            e.preventDefault();
          
            var anstext = $("#itemanstext").val();
            var ansvalue = $("#itemansvalue").val();
            if (!anstext ) {
                alert("Enter possible answer text field");
                return;
            }
            if (!ansvalue) {
                alert("Enter possible answer value field");
                return;
            }
            this.PossibleOptions.push({ Text: anstext, Value: ansvalue });
            $("#itemanstext").val('');
            $("#itemansvalue").val('');
            this.reloadAnswerOption();
        },
        reloadAnswerOption: function () {
          
            var html = '<table class="table table-bordered table-hover "><thead><tr> <th>Text</th> <th>Value</th><th></th></tr></thead><tbody> ';
            _(this.PossibleOptions).each(function (dv) {
                html += '<tr> <td>' + dv.Text + '</td> <td>' + dv.Value + '</td><td><a href="#" data-name=' + dv.Value + ' class="btn btn-xs btn-deleterow" ><i class="glyphicon glyphicon-trash"></i> </a></td></tr>';
               

            });
            html += '</tbody></table>';
            $("#possibleansweroption_holder").html(html);
        },
        save: function (e) {
            e.preventDefault();
            var self = this;
            self.model.validate = this.validate;;
            var id = apputil.Guid();
           
            if (self.model.id == null) {
                self.model.set({ Id: id });
            }
            var itemId = $("#itemid").val();
            if (itemId != null) {
                self.model.set({ Id: itemId });
            }
            this.getdata();
            self.model.set({
                Label: this.label,
                Order: this.itemNo,
                FormItemTypeId: this.formItemType,
                FormId: this.formId,
                IsRequired: this.isRequired,
                ValidationText: this.validationText,
                ValidationRegex: this.validationRegex,
                Section: this.section,
                RespondentTypes: this.checkedRespondentType,
                HelpText: this.helpText,
                PossibleOptions: this.PossibleOptions,
            });
          
            if (!self.model.isValid()) {
               
                apputil.showErrorsAlert(self.model.validationError);
                return;
            }
            self.model.url = window.app_baseurl + "api/client/form/SaveFormItem";
            self.model.sync("create", self.model, {
                success: function (model, response) {
                    if (model.Status == false) {
                        alert(model.Info);
                        return;
                    } else {
                       
                        window.location.replace(window.app_baseurl + "WebForm/FormItems?formId=" + self.formId);
                    }
                },
                error: function (model, response) {

                    alert("fail");
                }
            });
          
        },
        getdata: function () {
            var checkedRespondentType = new Array();
            $('#respondenttype_holder input:checked').each(function () {
                checkedRespondentType.push($(this).attr('value'));
            });
            this.checkedRespondentType = checkedRespondentType;
            this.formItemType = $("#itemtype").val();
            this.formId = $("#formid").val();
            this.label = $("#itemlabel").val();
            this.isRequired = $("#isrequired").is(':checked');
            this.helpText = $("#helptext").val();
            this.validationText = $("#validationtext").val();
            this.validationRegex = $("#validationregex").val();
            this.section = $("#section").val();
            this.itemNo = $("#itemNo").val();
            
            ;
            
        },
        validate: function (attrs, options) {
         
            var errors = [];
            if (!attrs.Label) {
                errors.push({ name: 'itemlabel', message: 'Please fill  label field.' });
            }
            if (!apputil.number_filter.test(attrs.Order)) {
                errors.push({ name: 'itemNo', message: 'Please fill valid No .' });
            }
          
            if (attrs.RespondentTypes.length ==0) {
                errors.push({ name: 'itemlabel', message: 'Make sure you have checked atleast one respondent type.' });
            }
            if (attrs.PossibleOptions.length <2) {
                errors.push({ name: 'psas', message: 'Make sure you have entered atleast two possible answers.' });
            }
            return errors.length > 0 ? errors : false;
        },
        loaddata: function (itemid) {

            var self = this;
            $.get(window.app_baseurl + "api/client/form/GetFormItem?itemId=" + itemid, {},
                function (data) {
                    
                    var checkedRespondentType = new Array();
                   
                    _(data.RespondentTypes).each(function (dv) {
                        $("#" + dv).prop('checked', true);
                    });
                   
                    self.checkedRespondentType = checkedRespondentType;
                    self.PossibleOptions = data.PossibleOptions;
                    self.reloadAnswerOption();
                    self.label = data.Label;
                    $("#itemlabel").val(self.label);
                    self.formItemType = data.FormItemTypeId;
                    $("#itemtype").val(self.formItemType);
                     self.isRequired = data.IsRequired;
                     $("#isrequired").prop('checked', self.isRequired);
                    self.helpText = data.HelpText;
                    $("#helptext").val(self.helpText);
                    self.validationText = data.ValidationText;
                    $("#validationtext").val(self.validationText);
                    self.validationRegex = data.ValidationRegex;
                    $("#validationregex").val(self.validationRegex);
                    self.section = data.Section;
                    $("#section").val(self.section);
                    self.itemNo = data.Order;
                    $("#itemNo").val(self.itemNo);
                    
                   
                }
            );
       

        },
        render: function () {
            var self = this;
            this.$el.html(this.template);
           
            $.ajax({
                url: window.app_baseurl + "api/client/form/GetFormRespodent?formId=" + $("#formid").val(),
                success: function (data) {
                    _(data.Data).each(function (dv) {
                        var r = '<div class="checkbox"><label><input id="' + dv.Id + '" type="checkbox" value="' + dv.Id + '">' + dv.Name + '</label></div>';
                        self.$el.find("#respondenttype_holder").append(r);
                    });
                },
                async: false
            });
            //this.model.respondentList.fetch({
            //    async:false,
            //    success: function () {
            //        _(self.model.respondentList.toJSON()).each(function (dv) {
                       
            //            var r = '<div class="checkbox"><label><input id="' + dv.Id + '" type="checkbox" value="' + dv.Id + '">' + dv.Name + '</label></div>';
            //            self.$el.find("#respondenttype_holder").append(r);
                       
            //        });
                    
            //    }
            //});
           
            return this;
        },

    });
  
   
    return formViewSingleChoice;
});