using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using TDR.Core.Domain.Clients;

namespace TDR.Core.Domain.Forms
{


    [Table("dForms")]
    public class DformEntity : DBase
    {
        public string Name { set; get; }
        public virtual ICollection<DformRespondentTypeEntity> RespondentTypes { set; get; }
        public virtual ICollection<DformItemEntity> FormItems { set; get; }
        public virtual ICollection<DformResultEntity> FormResult { set; get; }
        public string IdCode { set; get; }

        public virtual Client Client { get; set; }

        public Guid ClientId { get; set; }
      
    }
}