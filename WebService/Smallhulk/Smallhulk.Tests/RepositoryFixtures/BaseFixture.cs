using System;
using NUnit.Framework;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Data.IOC;
using StructureMap;

namespace Smallhulk.Tests.RepositoryFixtures
{
    public abstract class BaseFixture
    {
        protected BaseFixture()
        {
        }

        [SetUp]
        public  void Setup()
        {
            ObjectFactory.Initialize(x =>
            {
                x.AddRegistry<RepositoryRegistry>();
                

            });
        }
        protected  string TestString()
        {
            return Guid.NewGuid().ToString();
        }

        protected Account AddAccount()
        {
            var accountRepository = IocHelper.Using<IAccountRepository>();
            Account entity = new Account
                                 {
                                     IsActive = true,
                                     CreatedOn = DateTime.Now,
                                     Id = Guid.NewGuid(),
                                     Name = TestString()
                                 };
            accountRepository.Save(entity);
            return accountRepository.GetById(entity.Id);
        }
        protected Country AddCountry()
        {
            var countryRepository = IocHelper.Using<ICountryRepository>();
            Country entity = new Country
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Name = TestString(),
                Code = TestString(),
                UpdatedOn = DateTime.Now,
                ZipCode = TestString(),
                
            };
            countryRepository.Save(entity);
            return countryRepository.GetById(entity.Id);
        }
        protected User AddUser()
        {
            var account = AddAccount();
             var country = AddCountry();
            var userRepository = IocHelper.Using<IUserRepository>();
            User entity = new User
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Username = TestString(),
                Password = TestString(),
                UpdatedOn = DateTime.Now,
                PhoneNumber = TestString(),
                Account = account,
              
                AccountId = account.Id,
              
                Email = TestString(),
                Fullname = TestString(),
                UserType = UserType.Email,

            };
            userRepository.Save(entity);
            return userRepository.GetById(entity.Id);
        }

        protected Category AddCategory()
        {
            var categoryRepository = IocHelper.Using<ICategoryRepository>();
            var account = AddAccount();
            Category entity = new Category()
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Name = TestString(),
                Account = account,
                AccountId = account.Id,
                Description = TestString(),
                UpdatedOn = DateTime.Now,
                
            };
            categoryRepository.Save(entity);
            return categoryRepository.GetById(entity.Id);
        }
        protected Product AddProduct()
        {
            var productRepository = IocHelper.Using<IProductRepository>();
            var account = AddAccount();
            var category = AddCategory();
            Product entity = new Product()
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Name = TestString(),
                Account = account,
                AccountId = account.Id,
                Description = TestString(),
                UpdatedOn = DateTime.Now,
                BuyingPrice = 300,
                Category = category,
                CategoryId = category.Id,
                SellingPrice = 400,
                

            };
            productRepository.Save(entity);
            return productRepository.GetById(entity.Id);
        }

        protected Route AddRoute()
        {
            var routeRepository = IocHelper.Using<IRouteRepository>();
            var account = AddAccount();
            Route entity = new Route()
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Name = TestString(),
                Account = account,
                AccountId = account.Id,
                UpdatedOn = DateTime.Now,
                Code = TestString(),
            };
            routeRepository.Save(entity);
            return routeRepository.GetById(entity.Id);
        }

        protected Outlet AddOutlet()
        {
            var outletRepository = IocHelper.Using<IOutletRepository>();
            var account = AddAccount();
            var route = AddRoute();
            Outlet entity = new Outlet()
            {
                IsActive = true,
                CreatedOn = DateTime.Now,
                Id = Guid.NewGuid(),
                Name = TestString(),
                Account = account,
                AccountId = account.Id,
                UpdatedOn = DateTime.Now,
                Code = TestString(),
                Latitude = 31.2,
                Longitude = 1.12,
                Route = route,
                RouteId = route.Id
            };
            outletRepository.Save(entity);
            return outletRepository.GetById(entity.Id);
        }

        public abstract void CanSave();
        public abstract void CanGetById();
        public abstract void CanQuery();
    }
}