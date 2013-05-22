using NUnit.Framework;
using Smallhulk.Data.IOC;
using StructureMap;

namespace Smallhulk.Tests.RepositoryFixtures
{
    public abstract class BaseFixture
    {
        [SetUp]
        public  void Setup()
        {
            ObjectFactory.Initialize(x =>
            {
                x.AddRegistry<RepositoryRegistry>();
                

            });
        }

        public abstract void CanSave();
        public abstract void CanGetById();
        public abstract void CanQuery();
    }
}