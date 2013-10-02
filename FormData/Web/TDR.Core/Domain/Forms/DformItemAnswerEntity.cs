using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace TDR.Core.Domain.Forms
{
    [Table("dFormItemsAnswers")]
    public class DformItemAnswerEntity : DBase
    {
       
        public Guid FormItemId { get; set; }
        public virtual DformItemEntity FormItem { get; set; }
        public string Text { set; get; }
        public string Value { set; get; }
        public string IdCode { set; get; }
    }
}