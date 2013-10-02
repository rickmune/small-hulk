using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace TDR.Core.Domain.Forms
{
    [Table("dFormResultItem")]
    public class DformResultItemEntity : DBase
    {

        public Guid FormResultId { set; get; }
        public DformResultEntity FormResult { set; get; }

        public Guid FormItemId { set; get; }
        public virtual DformItemEntity FormItem { set; get; }
        public string FormItemAnswer { set; get; }
    }
}