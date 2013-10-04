using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace TDR.Core.Domain.Forms
{
    [Table("dFormItems")]
    public class DformItemEntity : DBase
    {
        
        public Guid FormId { get; set; }
        public virtual DformEntity Form { get; set; }
        public int Order { get; set; }
        public string Label { set; get; }
        public string IdCode { set; get; }
        public string HelpText { set; get; }
        public string Section { set; get; }
       
        public DformItemType FormItemType { set; get; }
       
        public virtual ICollection<DformItemAnswerEntity> FormItemAnswers { set; get; }
        public virtual ICollection<DformItemRespondentTypeEntity> FormItemRespondentTypes { set; get; }
        public virtual ICollection<DformResultItemEntity> FormItemResultItems { set; get; }
       
        public bool IsRequired { set; get; }
        public string ValidationText { set; get; }
        public string ValidationRegex { set; get; }
       
    }
}