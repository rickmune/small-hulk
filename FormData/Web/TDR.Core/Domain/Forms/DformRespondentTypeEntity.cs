using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace TDR.Core.Domain.Forms
{
    [Table("dFormRespondentType")]
    public class DformRespondentTypeEntity : DBase
    {
        
        public Guid FormId { get; set; }
        public virtual DformEntity Form { get; set; }
        public string Name { set; get; }
        public string Code { set; get; }
        public string IdCode { set; get; }
        public ICollection<DformItemRespondentTypeEntity> FormItemRespondentTypes { set; get; }
        public ICollection<DformResultEntity> FormResult { set; get; }
        
        
       
    }
}