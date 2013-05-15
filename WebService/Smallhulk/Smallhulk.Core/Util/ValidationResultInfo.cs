using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Util
{
    public class ValidationResultInfo
    {
        public ValidationResultInfo()
        {
            Results = new List<ValidationResult>();
        }

        public bool IsValid
        {
            get { return Results.Count() == 0; }
        }

        public List<ValidationResult> Results { get; set; }
    }
}
