using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using StructureMap;

namespace Smallhulk.Tests
{
    public class IocHelper
    {
       

        public static T Using<T>() where T : class
        {
            return ObjectFactory.GetInstance<T>();
        }
    }
}
