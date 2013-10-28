using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.WEB.LIB.DTOS.Configurations
{
   public class ReportServerConfigDTO
    {
       [Required]
       public string ReportServerUri { get; set; }
       [Required]
       public string ReportServerUsername { get; set; }
       [Required]
       public string ReportServerPassword { get; set; }
    }
}
