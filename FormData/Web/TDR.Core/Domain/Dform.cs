using System;
using System.Collections.Generic;

namespace TDR.Core.Domain
{
    public enum DformItemType
    {
        Text=1,
        DropdownList=2,
        MultiChoice=3,
    }
   
    public class Dform : DBase
    {
        public Dform()
        {
            RespondentTypes=new List<DformRespondentType>();
            FormItems= new List<DformItem>();
        }

        public string Name { set; get; }
        public List<DformRespondentType> RespondentTypes { set; get; }
        public List<DformItem> FormItems { set; get; }
      
    }
    public class DformItem : DBase
    {
        public DformItem()
        {
            FormItemRespondentTypes= new List<DformItemRespondentType>();
            FormItemAnswer= new List<DformItemAnswer>();
        }
        public int Order { get; set; }
        public string Label { set; get; }
        public DformItemType FormItemType { set; get; }
        public List<DformItemRespondentType> FormItemRespondentTypes { set; get; }
        public List<DformItemAnswer> FormItemAnswer { set; get; }
        public bool IsRequired { set; get; }
        public string ValidationText { set; get; }
        public string ValidationRegex { set; get; }

       
    }
    public class DformItemAnswer : DBase
    {
        public string Text { set; get; }
        public string Value { set; get; }
    }
    public class DformRespondentType : DBase
    {
        public string Name { set; get; }
        public string Code { set; get; }
    }
    public class DformItemRespondentType : DBase
    {
        public Guid RespondentTypeId { set; get; }
        public Guid FormItemId { set; get; }
       
    }

    /// <summary>
    ///  Form Result
    /// </summary>
    public class DformResult : DBase
    {
        public Guid RespondentTypeId { set; get; }
        public Guid FormId { set; get; }
        public Guid? LocationId { set; get; }
        public string Username { set; get; }
        public List<DformResultItem> FormResultItem { set; get; }
        public double? Longitude { get; set; }
        public double? Latitude { get; set; }
    }
    public class DformResultItem : DBase
    {

        public Guid FormItemId { set; get; }
        public List<string> FormItemAnswer { set; get; }
    }
}