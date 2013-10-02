using System;

using System.Linq;

using System.Web.Mvc;
using TDR.Core.Data.EF;
using TDR.Core.Domain;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Services;
using TDR.Core.Util;
using TDR.EF;


namespace TDR.Controllers
{
     [Authorize(Roles = "Admin")]
    public class HomeController : Controller
    {
        private TDRContext _context;
        private ISafaricomFormService _safaricomFormService;
         private IUserRepository _userRepository;

        public HomeController(TDRContext context, ISafaricomFormService safaricomFormService,IUserRepository userRepository)

        {
            _context = context;
            _safaricomFormService = safaricomFormService;
            _userRepository = userRepository;
            if(!_context.Users.Any(s=>s.Username.ToLower()=="admin"))
            {
                var user1 = new User
                                {

                                    CreatedOn = DateTime.Now,
                                    Email = "",
                                    Fullname = "admin",
                                    Id = Guid.NewGuid(),
                                    IsActive = false,
                                    Password = Md5Hash.GetMd5Hash("1234"),
                                    PhoneNumber = "00000000",
                                    UserType = UserType.Admin,
                                    UpdatedOn = DateTime.Now,
                                    
                                    Username = "admin"

                                };
                _context.Users.Add(user1);
                _context.SaveChanges();
            }
        }

        public ActionResult Index()
        {
            var sdd = _context.FormResult.Take(20).OrderByDescending(s => s.DateInserted).ToList();
            return View(sdd);
        }
        public ActionResult Dashboard()
        {
            return View();
        }
        public ActionResult FormDetails(Guid id)
        {
           
            var sdd = _context.FormResultItem.Where(s => s.FormResultId == id).OrderBy(s => s.FormItem.Order).ToList();
            return View(sdd);
        }
        public ActionResult seed()
        {
            TDRContext context = new TDRContext();
            if(context.Forms.Any(s=>s.Name=="Safaricom"))
            {
                return View();
            }
            //var formid = CreateForm("Safaricom");
            //var formRespondentid1 = CreateFormRespondentType(formid,"Dealer","D");
            //var formRespondentid2 = CreateFormRespondentType(formid, "M-Pesa", "M");
            //var formitem1 = CreateFormItem(formid,"What is your name ?",DformItemType.Text,1,true);
            //CreateItemRespondentType(formitem1,formRespondentid1);
            //CreateItemRespondentType(formitem1, formRespondentid2);

            //var formitem2 = CreateFormItem(formid, "What is your Gender?", DformItemType.DropdownList, 2, true);
            //CreateItemRespondentType(formitem2, formRespondentid1);
            //CreateItemRespondentType(formitem2, formRespondentid2);
            //CreateAnswers(formitem2, "MALE", "M");
            //CreateAnswers(formitem2, "FEMALE", "F");
          

            //var formitem3 = CreateFormItem(formid, "Which of the following Product do you use?", DformItemType.MultiChoice, 3, true);
            //CreateItemRespondentType(formitem3, formRespondentid1);
            //CreateItemRespondentType(formitem3, formRespondentid2);
            //CreateAnswers(formitem3, "BANANA", "B");
            //CreateAnswers(formitem3, "MANGO", "M");
            //CreateAnswers(formitem3, "ORANGE", "O");
            //CreateAnswers(formitem3, "APLE", "A");
            
           

          

            return View();
        }

        public ActionResult seedSafaricom()
        {
            _safaricomFormService.CreateDailyData();
            return View();
        }

       }
}
