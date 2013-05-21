using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace Smallhulk.Web.Lib.IOC
{
  public  class StructureMapDependencyResolver: IDependencyResolver
    {
        public StructureMapDependencyResolver(StructureMap.IContainer container)
        {
            _container = container;
        }

        public object GetService(Type serviceType)
        {
            if (serviceType.IsAbstract || serviceType.IsInterface)
            {
                return _container.TryGetInstance(serviceType);
            }
            else
            {
                return _container.GetInstance(serviceType);
            }
        }

        public IEnumerable<object> GetServices(Type serviceType)
        {
            return _container.GetAllInstances<object>()

                .Where(s => s.GetType() == serviceType);
        }

        private readonly StructureMap.IContainer _container;
    }
}
