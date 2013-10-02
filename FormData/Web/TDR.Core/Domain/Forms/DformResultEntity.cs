using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Spatial;

namespace TDR.Core.Domain.Forms
{
    /// <summary>
    ///  Form Result
    /// </summary>
    [Table("dFormResult")]
    public class DformResultEntity : DBase
    {

        public Guid RespondentTypeId { set; get; }
        public   DformRespondentTypeEntity  RespondentType{ set; get; }
        public Guid FormId { set; get; }
        public virtual DformEntity Form { set; get; }
        public DateTime DateInserted { set; get; }
       
        public double? Longitude { get; set; }
        public double? Latitude { get; set; }
        public virtual ICollection<DformResultItemEntity> FormItemResultItems { set; get; }
    }
}