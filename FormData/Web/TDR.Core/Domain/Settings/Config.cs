using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.Core.Domain.Settings
{
    public enum ConfigType
    {
        ReportServer=1,
        ReportServerPassword=2,
        ReportServerUsername=3,
        
    }
     [Table("tblConfig")]
   public class Config
    {
         public Guid Id { get; set; }
       public ConfigType  ConfigType { get; set; }
       public string Value { get; set; }
       public string Description { get; set; }
       public Guid? ClientId { get; set; }
    }
}
