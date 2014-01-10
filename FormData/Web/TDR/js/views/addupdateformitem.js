define([
        'jquery',
        'underscore',
        'backbone',
        'jqueryui',
        'apputil',
        'text!templates/addupdateformitem_template.html',
         'text!templates/addupdateformitemtext_template.html',
 'text!templates/addupdateformitemsinglechoice_template.html'

], function ($, _, backbone, jqueryui,apputil, template, texttemplate, singlettemplate) {

    // user form view

    var Model = Backbone.Model.extend({});
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
    var formViewText = Backbone.View.extend({
        initialize: function (option) {
            this.$el = $('#itemcontentholder');
            this.cid = "view_addupdateformitemtext";
            this.template = _.template(texttemplate);
            this.model = new formItemTextModel({ formid: option.formid });
            if (option != null && option.ItemType != null) {
                this.ItemType = option.ItemType;
            }
        },
        events: {
            'click #btn-save-formitemtext': 'save',

        },
        save: function () {
            var self = this;
           
            ;
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
                HelpText:this.helpText
            });
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
            this.label = $("#label").val();
            this.isRequired = $("#isrequired").is(':checked');
            this.helpText = $("#helptext").val();
            this.validationText = $("#validationtext").val();
            this.validationRegex = $("#validationregex").val();
            this.section = $("#section").val();
            this.itemNo = $("#itemNo").val();
            
            ;
            
        },
        loaddata: function (itemid) {
            

            $.get(window.app_baseurl + "api/client/form/GetFormItem?itemId=" + itemid, {},
                function (data) {
                    
                    var checkedRespondentType = new Array();
                   // $('#respondenttype_holder input:checked').each(function () {
                    //    checkedRespondentType.push($(this).attr('value'));
                    //});
                    _(data.RespondentTypes).each(function (dv) {
                      
                        $("#" + dv).prop('checked', true);
                    });
                    this.checkedRespondentType = checkedRespondentType;
                    this.label = data.Label;
                    $("#label").val(this.label);
                    this.formItemType = data.FormItemTypeId;
                     $("#itemtype").val(this.formItemType);
                    this.isRequired = data.IsRequired;
                    $("#isrequired").prop('checked',this.isRequired);
                    this.helpText = data.HelpText;
                    $("#helptext").val(this.helpText);
                    this.validationText = data.ValidationText;
                    $("#validationtext").val(this.validationText);
                    this.validationRegex = data.ValidationRegex;
                    $("#validationregex").val(this.validationRegex);
                    this.section = data.Section;
                    $("#section").val(this.section);
                    this.itemNo = data.Order;
                    $("#itemNo").val(this.itemNo);
                    
                   
                }
            );
       

        },
        render: function () {
            var self = this;
            this.$el.html(this.template);
            this.model.respondentList.fetch({
                success: function () {
                    _(self.model.respondentList.toJSON()).each(function (dv) {
                       
                        var r = '<div class="checkbox"><label><input id="' + dv.Id + '" type="checkbox" value="' + dv.Id + '">' + dv.Name + '</label></div>';
                        self.$el.find("#respondenttype_holder").append(r);
                    });
                }
            });
           
            return this;
        },

    });
    var formViewSingleChoice = Backbone.View.extend({
        initialize: function (option) {
            this.$el = $('#itemcontentholder');
            this.cid = "view_addupdateformitemsinglechoice";
            this.template = _.template(singlettemplate);
            this.model = new Model();
            if (option != null && option.ItemType != null) {
                this.ItemType = option.ItemType;
            }
            this.model.url = window.app_baseurl + "api/client/casecode/save";
        },
        render: function () {
            var self = this;
            this.$el.html(this.template);
            return this;
        },

    });
    var formView = Backbone.View.extend({
        initialize: function (option) {
            this.$el = $('#pagecontentholder');
            this.cid = "view_addupdateformitem";
            this.template = _.template(template);
            this.model = new Model();
        },
        events: {
            'change #itemtype': 'showForm',

        },
        showForm: function (e) {
           
            e.preventDefault();
            var selected = $("#itemtype").val();
            var formid = $("#formid").val();
            if (selected==1) {
                var textview = new formViewText({ ItemType: selected, formid: formid });
                textview.render();
            }else if (selected == 2) {
                var view = new formViewSingleChoice({ ItemType: selected, formid: formid });
                view.render();
            } else {
                this.$el.find("#itemcontentholder").html("");
            }
        },
        
        bindings: {
            '#label': 'Label',
            
        },


        render: function () {
            //;
            var self = this;
            this.$el.html(this.template);
            var itemId = $("#itemid").val();
            if (itemId != null) {
                var selected = $("#itemtypeid").val();
                $('#itemtype').val(selected);
                var formid = $("#formid").val();
                if (selected == 1) {
                    var textview = new formViewText({ ItemType: selected, formid: formid });
                    textview.render();
                    textview.loaddata(itemId);
                } else if (selected == 2) {
                    var view = new formViewSingleChoice({ ItemType: selected, formid: formid });
                    view.render();
                }
            }
           
           // this.stickit();
            return this;
        },

    });
    return formView;
});