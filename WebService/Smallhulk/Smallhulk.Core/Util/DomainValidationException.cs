using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Util
{
    public class DomainValidationException : Exception
    {
        public DomainValidationException(ValidationResultInfo vri, string msg)
            : base(msg)
        {
            ValidationResult = vri;
        }
        public ValidationResultInfo ValidationResult { get; set; }
    }
}
