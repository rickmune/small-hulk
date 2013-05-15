using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Util
{
    public interface IValidation<T> where T : class
    {
        ValidationResultInfo Validate(T itemToValidate);
    }
}
