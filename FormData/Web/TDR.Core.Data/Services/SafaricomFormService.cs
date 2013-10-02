using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Data.EF;
using TDR.Core.Domain;
using TDR.Core.Domain.Clients;
using TDR.Core.Domain.Users;
using TDR.Core.Services;
using TDR.Core.Util;
using TDR.EF;

namespace TDR.Core.Data.Services
{
    public class SafaricomFormService :FormServiceBase, ISafaricomFormService
    {
        private const string FormCode = "SAF_D_D_F";
        public SafaricomFormService(TDRContext context) : base(context)
        {
        }
        private Client GetClient()
        {
            var client = _context.Clients.FirstOrDefault(s => s.Code == "SAF");
            var date = DateTime.Now;
            if(client==null)
            {
                client= new Client();
                client.Id = Guid.NewGuid();
                _context.Clients.Add(client);
                client.Code = "SAF";
                client.Name = "SAFARICOM";
                client.IsActive = true;
                client.CreatedOn = date;
                client.UpdatedOn = date;
                var user1 = new User
                                {
                                    ClientId = client.Id,
                                    CreatedOn = date,
                                    Email = "",
                                    Fullname = "safadmin",
                                    Id = Guid.NewGuid(),
                                    IsActive = false,
                                    Password = Md5Hash.GetMd5Hash("1234"),
                                    PhoneNumber = "00000000",
                                    UserType = UserType.Admin,
                                    UpdatedOn = date,
                                    Username = "safadmin"

                                };
                _context.Users.Add(user1);
                var user2 = new User
                {
                    ClientId = client.Id,
                    CreatedOn = date,
                    Email = "",
                    Fullname = "saftdr",
                    Id = Guid.NewGuid(),
                    IsActive = false,
                    Password = Md5Hash.GetMd5Hash("1234"),
                    PhoneNumber = "00000000",
                    UserType = UserType.TDR,
                    UpdatedOn = date,
                    Username = "saftdr"

                };
                _context.Users.Add(user2);

                _context.SaveChanges();
            }
           
            return client;

        }
        private string GetRespodentCode(int index)
        {
            return FormCode + "_R_" + index.ToString();
        }
        private string GetItemCode(int formItemNo)
        {
            return FormCode + "_I_" + formItemNo.ToString();
        }
        private string GetItemAnsCode(int forItemNo,int itemAnsNo)
        {
            return string.Format(FormCode + "_I{0}_A{1}" , forItemNo, itemAnsNo);
        }

        public void CreateDailyData()
        {
           
            var formid = CreateForm("Safaricom Daily Data", FormCode,GetClient().Id);
            var delearshop = CreateFormRespondentType(formid, GetRespodentCode(1), "DEALERSHOP", "D");
            var mpesa = CreateFormRespondentType(formid, GetRespodentCode(2), "M-PESA", "M");
            var retailshop = CreateFormRespondentType(formid, GetRespodentCode(3), "RETAIL", "R");
            var digitalvilage = CreateFormRespondentType(formid, GetRespodentCode(4), "DIGITAL VILLAGE", "DV");

            var formitem1 = CreateFormItem(formid,GetItemCode(1), "What is the name of the outlet ?", DformItemType.Text, 1, true);
            CreateItemRespondentType(formitem1, delearshop);
            CreateItemRespondentType(formitem1, mpesa);
            CreateItemRespondentType(formitem1, retailshop);
            CreateItemRespondentType(formitem1, digitalvilage);
            
            var formitem2 = CreateFormItem(formid, GetItemCode(2), "What is the Location of the outlet ?", DformItemType.Text, 2, true);
            CreateItemRespondentType(formitem2, delearshop);
            CreateItemRespondentType(formitem2, mpesa);
            CreateItemRespondentType(formitem2, retailshop);
            CreateItemRespondentType(formitem2, digitalvilage);

            var formitem3 = CreateFormItem(formid, GetItemCode(3), "What is the M-Pesa Agent Number ?", DformItemType.Text, 3, true,"Enter valid Agent No.",@"\d{5,6}");
            CreateItemRespondentType(formitem3, mpesa);

            var formitem4 = CreateFormItem(formid, GetItemCode(4), "What is the status of the mpesa outlet?", DformItemType.DropdownList, 4, true);
            CreateItemRespondentType(formitem4, mpesa);
            CreateAnswers(formitem4,GetItemAnsCode(4,1), "Active", "Active");
            CreateAnswers(formitem4, GetItemAnsCode(4, 2), "Closed", "Closed");
            CreateAnswers(formitem4, GetItemAnsCode(4, 3), "Suspended", "Suspended");
            CreateAnswers(formitem4, GetItemAnsCode(4, 4), "Relocated", "Relocated");
            CreateAnswers(formitem4, GetItemAnsCode(4, 5), "MIT", "MIT");

            var formitem5 = CreateFormItem(formid, GetItemCode(5), "What is the name of the assistant ?", DformItemType.Text, 5, true);
            CreateItemRespondentType(formitem5, mpesa);

            var formitem6 = CreateFormItem(formid, GetItemCode(6), "What is the contact details of the assistant ?", DformItemType.Text, 6, true);
            CreateItemRespondentType(formitem6, mpesa);

            var formitem7 = CreateFormItem(formid, GetItemCode(7), "Is the assistant  trained and tested on M-PESA/KYC /AML?", DformItemType.DropdownList, 7, true);
            CreateItemRespondentType(formitem7, mpesa);
            CreateAnswers(formitem7, GetItemAnsCode(7, 1), "YES", "1");
            CreateAnswers(formitem7,GetItemAnsCode(7, 2), "NO", "0");

            var formitem8 = CreateFormItem(formid, GetItemCode(8), "Is the target sheet updated?", DformItemType.DropdownList, 8, true);
            CreateItemRespondentType(formitem8, mpesa);
            CreateAnswers(formitem8,GetItemAnsCode(8, 1), "YES", "1");
            CreateAnswers(formitem8, GetItemAnsCode(8, 2), "NO", "0");

            var formitem9 = CreateFormItem(formid, GetItemCode(9), "What is the current mpesa float ?", DformItemType.Text, 9, true, "Enter valid mpesa float amount.", @"\d{1,10}+(\.*\d{0,2})");
            CreateItemRespondentType(formitem9, mpesa);

            var formitem10 = CreateFormItem(formid, GetItemCode(10), "Are SIMEX  available?", DformItemType.DropdownList, 10, true);
            CreateItemRespondentType(formitem10, delearshop);
            CreateItemRespondentType(formitem10, mpesa);
            CreateItemRespondentType(formitem10, retailshop);
            CreateItemRespondentType(formitem10, digitalvilage);
            CreateAnswers(formitem10, GetItemAnsCode(10, 1), "Available", "1");
            CreateAnswers(formitem10, GetItemAnsCode(10, 2), "Not Available", "0");

            var formitem11 = CreateFormItem(formid, GetItemCode(11), "Are Lines  available?", DformItemType.DropdownList, 11, true);
            CreateItemRespondentType(formitem11, delearshop);
            CreateItemRespondentType(formitem11, mpesa);
            CreateItemRespondentType(formitem11, retailshop);
            CreateItemRespondentType(formitem11, digitalvilage);
            CreateAnswers(formitem11, GetItemAnsCode(11, 1), "Available", "1");
            CreateAnswers(formitem11, GetItemAnsCode(11, 2), "Not Available", "0");

            var formitem12 = CreateFormItem(formid, GetItemCode(12), "How many promotion phones available?", DformItemType.Text, 12, true, "Enter valid No of Phones.", @"\d{1,10}");
            CreateItemRespondentType(formitem12, delearshop);
            CreateItemRespondentType(formitem12, mpesa);
            CreateItemRespondentType(formitem12, retailshop);
            CreateItemRespondentType(formitem12, digitalvilage);

            var formitem13 = CreateFormItem(formid, GetItemCode(13), "USSD Service Self Reversal ?", DformItemType.DropdownList, 13, true);
            CreateItemRespondentType(formitem13, delearshop);
            CreateItemRespondentType(formitem13, mpesa);
            CreateItemRespondentType(formitem13, retailshop);
            CreateItemRespondentType(formitem13, digitalvilage);
            CreateAnswers(formitem13, GetItemAnsCode(13, 1), "YES", "1");
            CreateAnswers(formitem13, GetItemAnsCode(13, 2), "NO", "0");

            var formitem14 = CreateFormItem(formid, GetItemCode(14), "USSD Service Able to Access *180#: ?", DformItemType.DropdownList, 14, true);
            CreateItemRespondentType(formitem14, delearshop);
            CreateItemRespondentType(formitem14, mpesa);
            CreateItemRespondentType(formitem14, retailshop);
            CreateItemRespondentType(formitem14, digitalvilage);
            CreateAnswers(formitem14, GetItemAnsCode(14, 1), "YES", "1");
            CreateAnswers(formitem14, GetItemAnsCode(14, 2), "NO", "0");

            var formitem15 = CreateFormItem(formid, GetItemCode(15), "How many modem  available?", DformItemType.Text, 15, true, "Enter valid No of Modem.", @"\d{1,10}");
            CreateItemRespondentType(formitem15, delearshop);
            CreateItemRespondentType(formitem15, mpesa);
            CreateItemRespondentType(formitem15, retailshop);
            CreateItemRespondentType(formitem15, digitalvilage);

            var formitem16 = CreateFormItem(formid, GetItemCode(16), "Is Safaricom 5 Shilling airtime available?", DformItemType.DropdownList, 16, true);
            CreateItemRespondentType(formitem16, delearshop);
            CreateItemRespondentType(formitem16, mpesa);
            CreateItemRespondentType(formitem16, retailshop);
            CreateItemRespondentType(formitem16, digitalvilage);
            CreateAnswers(formitem16, GetItemAnsCode(16, 1), "YES", "1");
            CreateAnswers(formitem16, GetItemAnsCode(16, 2), "NO", "0");

            var formitem17 = CreateFormItem(formid, GetItemCode(17), "Is Safaricom 10 Shilling airtime available?", DformItemType.DropdownList, 17, true);
            CreateItemRespondentType(formitem17, delearshop);
            CreateItemRespondentType(formitem17, mpesa);
            CreateItemRespondentType(formitem17, retailshop);
            CreateItemRespondentType(formitem17, digitalvilage);
            CreateAnswers(formitem17, GetItemAnsCode(17, 1), "YES", "1");
            CreateAnswers(formitem17, GetItemAnsCode(17, 2), "NO", "0");

            var formitem18 = CreateFormItem(formid, GetItemCode(18), "Is Safaricom 20 Shilling airtime available?", DformItemType.DropdownList, 18, true);
            CreateItemRespondentType(formitem18, delearshop);
            CreateItemRespondentType(formitem18, mpesa);
            CreateItemRespondentType(formitem18, retailshop);
            CreateItemRespondentType(formitem18, digitalvilage);
            CreateAnswers(formitem18, GetItemAnsCode(18, 1), "YES", "1");
            CreateAnswers(formitem18, GetItemAnsCode(18, 2), "NO", "0");

            var formitem19 = CreateFormItem(formid, GetItemCode(19), "Is Safaricom 50 Shilling airtime available?", DformItemType.DropdownList, 19, true);
            CreateItemRespondentType(formitem19, delearshop);
            CreateItemRespondentType(formitem19, mpesa);
            CreateItemRespondentType(formitem19, retailshop);
            CreateItemRespondentType(formitem19, digitalvilage);
            CreateAnswers(formitem19, GetItemAnsCode(19, 1), "YES", "1");
            CreateAnswers(formitem19, GetItemAnsCode(19, 2), "NO", "0");

            var formitem20 = CreateFormItem(formid, GetItemCode(20), "Is Safaricom 100 Shilling airtime available?", DformItemType.DropdownList, 20, true);
            CreateItemRespondentType(formitem20, delearshop);
            CreateItemRespondentType(formitem20, mpesa);
            CreateItemRespondentType(formitem20, retailshop);
            CreateItemRespondentType(formitem20, digitalvilage);
            CreateAnswers(formitem20, GetItemAnsCode(20, 1), "YES", "1");
            CreateAnswers(formitem20, GetItemAnsCode(20, 2), "NO", "0");

            var formitem21 = CreateFormItem(formid, GetItemCode(21), "Is Safaricom 250 Shilling airtime available?", DformItemType.DropdownList, 21, true);
            CreateItemRespondentType(formitem21, delearshop);
            CreateItemRespondentType(formitem21, mpesa);
            CreateItemRespondentType(formitem21, retailshop);
            CreateItemRespondentType(formitem21, digitalvilage);
            CreateAnswers(formitem21, GetItemAnsCode(21, 1), "YES", "1");
            CreateAnswers(formitem21, GetItemAnsCode(21, 2), "NO", "0");

            var formitem22 = CreateFormItem(formid, GetItemCode(22), "Is Safaricom Other HDV airtime available?", DformItemType.DropdownList, 22, true);
            CreateItemRespondentType(formitem22, delearshop);
            CreateItemRespondentType(formitem22, mpesa);
            CreateItemRespondentType(formitem22, retailshop);
            CreateItemRespondentType(formitem22, digitalvilage);
            CreateAnswers(formitem22, GetItemAnsCode(22, 1), "YES", "1");
            CreateAnswers(formitem22, GetItemAnsCode(22, 2), "NO", "0");


            var formitem23 = CreateFormItem(formid, GetItemCode(23), "Is ORANGE aritime available?", DformItemType.DropdownList, 23, true);
            CreateItemRespondentType(formitem23, delearshop);
            CreateItemRespondentType(formitem23, mpesa);
            CreateItemRespondentType(formitem23, retailshop);
            CreateItemRespondentType(formitem23, digitalvilage);
            CreateAnswers(formitem23, GetItemAnsCode(23, 1), "YES", "1");
            CreateAnswers(formitem23, GetItemAnsCode(23, 2), "NO", "0");

            var formitem24 = CreateFormItem(formid, GetItemCode(24), "Is YU aritime available?", DformItemType.DropdownList, 24, true);
            CreateItemRespondentType(formitem24, delearshop);
            CreateItemRespondentType(formitem24, mpesa);
            CreateItemRespondentType(formitem24, retailshop);
            CreateItemRespondentType(formitem24, digitalvilage);
            CreateAnswers(formitem24, GetItemAnsCode(24, 1), "YES", "1");
            CreateAnswers(formitem24, GetItemAnsCode(24, 2), "NO", "0");

            var formitem25 = CreateFormItem(formid, GetItemCode(25), "Is AIRTEL aritime available?", DformItemType.DropdownList, 25, true);
            CreateItemRespondentType(formitem25, delearshop);
            CreateItemRespondentType(formitem25, mpesa);
            CreateItemRespondentType(formitem25, retailshop);
            CreateItemRespondentType(formitem25, digitalvilage);
            CreateAnswers(formitem25, GetItemAnsCode(25, 1), "YES", "1");
            CreateAnswers(formitem25, GetItemAnsCode(25, 2), "NO", "0");

            //  visibility Vs all other companies' materials (approx %)

            var formitem26 = CreateFormItem(formid, GetItemCode(26), "Visibility Vs all other companies' materials (approx %)?", DformItemType.Text, 26, true, "Enter valid  %", @"\d{1,2}");
            CreateItemRespondentType(formitem26, delearshop);
            CreateItemRespondentType(formitem26, mpesa);
            CreateItemRespondentType(formitem26, retailshop);
            CreateItemRespondentType(formitem26, digitalvilage);

            //missing Merchandising materials
            var formitem27 = CreateFormItem(formid, GetItemCode(27), "Missing Merchandising materials?", DformItemType.Text, 27, true);
            CreateItemRespondentType(formitem27, delearshop);
            CreateItemRespondentType(formitem27, mpesa);
            CreateItemRespondentType(formitem27, retailshop);
            CreateItemRespondentType(formitem27, digitalvilage);
            //var formitem3 = CreateFormItem(formid, "Which of the following Product do you use?", DformItemType.MultiChoice, 3, true);
            //CreateItemRespondentType(formitem3, delearshop);
            //CreateItemRespondentType(formitem3, mpesa);
            //CreateItemRespondentType(formitem3, retailshop);
            //CreateItemRespondentType(formitem3, digitalvilage);
            //CreateAnswers(formitem3, "BANANA", "B");
            //CreateAnswers(formitem3, "MANGO", "M");
            //CreateAnswers(formitem3, "ORANGE", "O");
            //CreateAnswers(formitem3, "APLE", "A");




        }

        public void CreateDailySale()
        {
          
        }

        public void CreateDailyTraining()
        {
           
        }
    }
}
