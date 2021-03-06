﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NUnit.Framework;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;

namespace Smallhulk.Tests.RepositoryFixtures
{
    [TestFixture]
    class CountryRepositoryFixture:BaseFixture
    {
        [Test]
        public override void CanSave()
        {
            var country = AddCountry();
            Assert.IsNotNull(country);
        }
         [Test]
        public override void CanGetById()
        {
            var country = AddCountry();
             var country2 = IocHelper.Using<ICountryRepository>().GetById(country.Id);
             Assert.IsNotNull(country);
             Assert.AreEqual(country.Name, country2.Name);

        }
         [Test]
        public override void CanQuery()
        {
            var country = AddCountry();
            var queryResult = IocHelper.Using<ICountryRepository>().Query(new QueryMasterData{Name = country.Name});
            Assert.IsNotNull(country);
            Assert.IsTrue(queryResult.Count>0);
        }
    }
}
