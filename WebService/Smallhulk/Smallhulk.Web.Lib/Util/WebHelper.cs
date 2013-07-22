using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Util;

namespace Smallhulk.Web.Lib.Util
{
   public static class WebHelper
    {
       public  static string FormatException(this DomainValidationException ex)
       {
           string info = ex.Message.ToUpper();
           foreach (var validationResult in ex.ValidationResult.Results)
           {
               info += "\n\t" + validationResult.ErrorMessage;
           }
           return info;
       }
    }
}
