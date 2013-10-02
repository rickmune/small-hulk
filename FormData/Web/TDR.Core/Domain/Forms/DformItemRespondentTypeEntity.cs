using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace TDR.Core.Domain.Forms
{
    [Table("dFormItemsRespondentType")]
    public class DformItemRespondentTypeEntity : DBase
    {
       
        public Guid FormItemId { get; set; }

        public virtual DformItemEntity FormItem { get; set; }

        // [ForeignKey("FormRespondentType")]
        public Guid FormRespondentTypeId { get; set; }

        public virtual DformRespondentTypeEntity FormRespondentType { get; set; }
       
    }
}