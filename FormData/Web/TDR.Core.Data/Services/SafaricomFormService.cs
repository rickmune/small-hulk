using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Data.EF;
using TDR.Core.Domain;
using TDR.Core.Domain.Clients;
using TDR.Core.Domain.Forms;
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
           

            var formid = CreateForm("Safaricom Daily Data", FormCode, GetClient().Id);
            var delearshop = CreateFormRespondentType(formid, GetRespodentCode(1), "DEALERSHOP", "D");
            var mpesa = CreateFormRespondentType(formid, GetRespodentCode(2), "M-PESA", "M");
            var retailshop = CreateFormRespondentType(formid, GetRespodentCode(3), "RETAIL", "R");
            var digitalvilage = CreateFormRespondentType(formid, GetRespodentCode(4), "DIGITAL VILLAGE", "DV");

            var q1 = new DformItemEntity();
            q1.FormId = formid;
            q1.Label = "What is the name of the outlet ?";
            q1.IsRequired = true;
            q1.FormItemType = DformItemType.Text;
            q1.Order = 1;
            q1.IdCode = GetItemCode(1);
            q1.ValidationRegex = "";
            q1.ValidationText = "";
            q1.HelpText = "Outlet Name";
            q1.Section = "1|-|1|-";
           
            var formitem1 = CreateFormItem(q1); 
            CreateItemRespondentType(formitem1, delearshop);
            CreateItemRespondentType(formitem1, mpesa);
            CreateItemRespondentType(formitem1, retailshop);
            CreateItemRespondentType(formitem1, digitalvilage);

            var q2 = new DformItemEntity();
            q2.FormId = formid;
            q2.Label = "What is the Location of the outlet ?";
            q2.IsRequired = true;
            q2.FormItemType = DformItemType.Text;
            q2.Order = 2;
            q2.IdCode = GetItemCode(2);
            q2.ValidationRegex = "";
            q2.ValidationText = "";
            q2.HelpText = "Location";
            q2.Section =  "1|-|2|-"; ;
            
            var formitem2 = CreateFormItem(q2);

            //var formitem2 = CreateFormItem(formid, GetItemCode(2), "What is the Location of the outlet ?",
            //                               DformItemType.Text, 2, true, "", "", "Outlet", 1);
            CreateItemRespondentType(formitem2, delearshop);
            CreateItemRespondentType(formitem2, mpesa);
            CreateItemRespondentType(formitem2, retailshop);
            CreateItemRespondentType(formitem2, digitalvilage);

            var q3 = new DformItemEntity();
            q3.FormId = formid;
            q3.Label = "What is the M-Pesa Agent Number ?";
            q3.IsRequired = true;
            q3.FormItemType = DformItemType.Text;
            q3.Order = 3;
            q3.IdCode = GetItemCode(3);
            q3.ValidationRegex = @"\d{5,6}";
            q3.ValidationText ="Enter valid Agent No."; ;
            q3.HelpText = "M-PESA AGENT NO.";
            q3.Section = "1|-|3|-"; ;
            
            var formitem3 = CreateFormItem(q3);

            //var formitem3 = CreateFormItem(formid, GetItemCode(3), "What is the M-Pesa Agent Number ?",
            //                               DformItemType.Text, 3, true, "Enter valid Agent No.", @"\d{5,6}", "M-PESA");
            CreateItemRespondentType(formitem3, mpesa);

            var q4 = new DformItemEntity();
            q4.FormId = formid;
            q4.Label = "What is the status of the mpesa outlet?";
            q4.IsRequired = true;
            q4.FormItemType = DformItemType.DropdownList;
            q4.Order = 4;
            q4.IdCode = GetItemCode(4);
            q4.ValidationRegex = "";
            q4.ValidationText ="";
            q4.HelpText = "M-PESA OUTLET STATUS";
            q4.Section = "2|M-PESA DATA|1|M-PESA OUTLETSTATUS"; 
            
            var formitem4 = CreateFormItem(q4);

            //var formitem4 = CreateFormItem(formid, GetItemCode(4), "What is the status of the mpesa outlet?",
            //                               DformItemType.DropdownList, 4, true);
            CreateItemRespondentType(formitem4, mpesa);
            CreateAnswers(formitem4, GetItemAnsCode(4, 1), "Active", "Active");
            CreateAnswers(formitem4, GetItemAnsCode(4, 2), "Closed", "Closed");
            CreateAnswers(formitem4, GetItemAnsCode(4, 3), "Suspended", "Suspended");
            CreateAnswers(formitem4, GetItemAnsCode(4, 4), "Relocated", "Relocated");
            CreateAnswers(formitem4, GetItemAnsCode(4, 5), "MIT", "MIT");

            var q5 = new DformItemEntity();
            q5.FormId = formid;
            q5.Label = "What is the name of the assistant ?";
            q5.IsRequired = true;
            q5.FormItemType = DformItemType.Text;
            q5.Order = 5;
            q5.IdCode = GetItemCode(5);
            q5.ValidationRegex = "";
            q5.ValidationText = "";
            q5.HelpText = "Assistant(s) (Names)";
            q5.Section = "2|M-PESA DATA|2|Assistants on Premise"; ;
           
            var formitem5 = CreateFormItem(q5);

            //var formitem5 = CreateFormItem(formid, GetItemCode(5), "What is the name of the assistant ?",
            //                               DformItemType.Text, 5, true);
            CreateItemRespondentType(formitem5, mpesa);

            var q6 = new DformItemEntity();
            q6.FormId = formid;
            q6.Label = "What is the contact details of the assistant ?";
            q6.IsRequired = true;
            q6.FormItemType = DformItemType.Text;
            q6.Order = 6;
            q6.IdCode = GetItemCode(6);
            q6.ValidationRegex = "";
            q6.ValidationText = "";
            q6.HelpText = "Assistant Tel: Contact";
            q6.Section = "2|M-PESA DATA|2|Assistants on Premise"; ;
            
            var formitem6 = CreateFormItem(q6);
            //var formitem6 = CreateFormItem(formid, GetItemCode(6), "What is the contact details of the assistant ?",
            //                               DformItemType.Text, 6, true);
            CreateItemRespondentType(formitem6, mpesa);

            var q7 = new DformItemEntity();
            q7.FormId = formid;
            q7.Label = "Is the assistant  trained and tested on M-PESA/KYC /AML?";
            q7.IsRequired = true;
            q7.FormItemType = DformItemType.DropdownList;
            q7.Order = 7;
            q7.IdCode = GetItemCode(7);
            q7.ValidationRegex = "";
            q7.ValidationText = "";
            q7.HelpText = "trained and tested on M-PESA/KYC /AML / (yes=1, No=0)";
            q7.Section = "2|M-PESA DATA|2|Assistants on Premise"; ;
           
            var formitem7 = CreateFormItem(q7);
            //var formitem7 = CreateFormItem(formid, GetItemCode(7),
            //                               "Is the assistant  trained and tested on M-PESA/KYC /AML?",
            //                               DformItemType.DropdownList, 7, true);
            CreateItemRespondentType(formitem7, mpesa);
            CreateAnswers(formitem7, GetItemAnsCode(7, 1), "YES", "1");
            CreateAnswers(formitem7, GetItemAnsCode(7, 2), "NO", "0");

            var q8 = new DformItemEntity();
            q8.FormId = formid;
            q8.Label = "Is the target sheet updated?";
            q8.IsRequired = true;
            q8.FormItemType = DformItemType.DropdownList;
            q8.Order = 8;
            q8.IdCode = GetItemCode(8);
            q8.ValidationRegex = "";
            q8.ValidationText = "";
            q8.HelpText = "Target Sheet Updated Yes=1, No=0";
            q8.Section = "2|M-PESA DATA|3|Targets";
            
            var formitem8 = CreateFormItem(q8);
            //var formitem8 = CreateFormItem(formid, GetItemCode(8), "Is the target sheet updated?",
            //                               DformItemType.DropdownList, 8, true);
            CreateItemRespondentType(formitem8, mpesa);
            CreateAnswers(formitem8, GetItemAnsCode(8, 1), "YES", "1");
            CreateAnswers(formitem8, GetItemAnsCode(8, 2), "NO", "0");

            var q9 = new DformItemEntity();
            q9.FormId = formid;
            q9.Label = "What is the current mpesa float ?";
            q9.IsRequired = true;
            q9.FormItemType = DformItemType.Text;
            q9.Order = 9;
            q9.IdCode = GetItemCode(9);
            q9.ValidationRegex = @"\d{1,10}+(\.*\d{0,2})";
            q9.ValidationText = "Enter valid mpesa float amount.";
            q9.HelpText = "E-Float(Ksh.)";
            q9.Section = "2|M-PESA DATA|4|Float Availability";
            
            var formitem9 = CreateFormItem(q9);
            //var formitem9 = CreateFormItem(formid, GetItemCode(9), "What is the current mpesa float ?",
            //                               DformItemType.Text, 9, true, "Enter valid mpesa float amount.",
            //                               @"\d{1,10}+(\.*\d{0,2})");
            CreateItemRespondentType(formitem9, mpesa);
            var q10 = new DformItemEntity();
            q10.FormId = formid;
            q10.Label = "Are SIMEX  available?";
            q10.IsRequired = true;
            q10.FormItemType = DformItemType.DropdownList;
            q10.Order = 10;
            q10.IdCode = GetItemCode(10);
            q10.ValidationRegex = "";
            q10.ValidationText = "";
            q10.HelpText = "Available= 1 Not Available=0";
            q10.Section = "4|SAFARICOM PRODUCT AVAILABILITY|1|Simex";
           
            var formitem10 = CreateFormItem(q10);
            //var formitem10 = CreateFormItem(formid, GetItemCode(10), "Are SIMEX  available?", DformItemType.DropdownList,
            //                                10, true);
            CreateItemRespondentType(formitem10, delearshop);
            CreateItemRespondentType(formitem10, mpesa);
            CreateItemRespondentType(formitem10, retailshop);
            CreateItemRespondentType(formitem10, digitalvilage);
            CreateAnswers(formitem10, GetItemAnsCode(10, 1), "Available", "1");
            CreateAnswers(formitem10, GetItemAnsCode(10, 2), "Not Available", "0");

            var q11 = new DformItemEntity();
            q11.FormId = formid;
            q11.Label = "Are Lines  available?";
            q11.IsRequired = true;
            q11.FormItemType = DformItemType.DropdownList;
            q11.Order = 11;
            q11.IdCode = GetItemCode(11);
            q11.ValidationRegex = "";
            q11.ValidationText = "";
            q11.HelpText = "Available= 1 Not Available=0";
            q11.Section = "4|SAFARICOM PRODUCT AVAILABILITY|2|Lines"; ;
           
            var formitem11 = CreateFormItem(q11);
            //var formitem11 = CreateFormItem(formid, GetItemCode(11), "Are Lines  available?", DformItemType.DropdownList,
            //                                11, true);
            CreateItemRespondentType(formitem11, delearshop);
            CreateItemRespondentType(formitem11, mpesa);
            CreateItemRespondentType(formitem11, retailshop);
            CreateItemRespondentType(formitem11, digitalvilage);
            CreateAnswers(formitem11, GetItemAnsCode(11, 1), "Available", "1");
            CreateAnswers(formitem11, GetItemAnsCode(11, 2), "Not Available", "0");

            var q12 = new DformItemEntity();
            q12.FormId = formid;
            q12.Label = "How many promotion phones available?";
            q12.IsRequired = true;
            q12.FormItemType = DformItemType.Text;
            q12.Order = 12;
            q12.IdCode = GetItemCode(12);
            q12.ValidationRegex = @"\d{1,10}";
            q12.ValidationText = "Enter valid No of Phones.";
            q12.HelpText = "Available= 1 Not Available=0";
            q12.Section = "4|SAFARICOM PRODUCT AVAILABILITY|3| Promotional phones"; ;
           
            var formitem12 = CreateFormItem(q12);
            //var formitem12 = CreateFormItem(formid, GetItemCode(12), "How many promotion phones available?",
            //                                DformItemType.Text, 12, true, "Enter valid No of Phones.", @"\d{1,10}");
            CreateItemRespondentType(formitem12, delearshop);
            CreateItemRespondentType(formitem12, mpesa);
            CreateItemRespondentType(formitem12, retailshop);
            CreateItemRespondentType(formitem12, digitalvilage);

            var q13 = new DformItemEntity();
            q13.FormId = formid;
            q13.Label = "USSD Service Self Reversal ?";
            q13.IsRequired = true;
            q13.FormItemType = DformItemType.DropdownList;
            q13.Order = 13;
            q13.IdCode = GetItemCode(13);
            q13.ValidationRegex = "";
            q13.ValidationText = "";
            q13.HelpText = "Yes=1, No=0";
            q13.Section = "3|USSD|1| Able to do Deposit self reversal (*234*3*1#)"; 
            
            var formitem13 = CreateFormItem(q13);
            //var formitem13 = CreateFormItem(formid, GetItemCode(13), "USSD Service Self Reversal ?",
                   //                         DformItemType.DropdownList, 13, true);
            CreateItemRespondentType(formitem13, delearshop);
            CreateItemRespondentType(formitem13, mpesa);
            CreateItemRespondentType(formitem13, retailshop);
            CreateItemRespondentType(formitem13, digitalvilage);
            CreateAnswers(formitem13, GetItemAnsCode(13, 1), "YES", "1");
            CreateAnswers(formitem13, GetItemAnsCode(13, 2), "NO", "0");

            var q14 = new DformItemEntity();
            q14.FormId = formid;
            q14.Label = "USSD Service Able to Access *180#: ?";
            q14.IsRequired = true;
            q14.FormItemType = DformItemType.DropdownList;
            q14.Order = 14;
            q14.IdCode = GetItemCode(14);
            q14.ValidationRegex = "";
            q14.ValidationText = "";
            q14.HelpText = "Yes=1, No=0";
            q14.Section = "3|USSD|2| Able to do subscriber registration (*180*2#)";
            
            var formitem14 = CreateFormItem(q14);
            //var formitem14 = CreateFormItem(formid, GetItemCode(14), "USSD Service Able to Access *180#: ?",
            //                                DformItemType.DropdownList, 14, true);
            CreateItemRespondentType(formitem14, delearshop);
            CreateItemRespondentType(formitem14, mpesa);
            CreateItemRespondentType(formitem14, retailshop);
            CreateItemRespondentType(formitem14, digitalvilage);
            CreateAnswers(formitem14, GetItemAnsCode(14, 1), "YES", "1");
            CreateAnswers(formitem14, GetItemAnsCode(14, 2), "NO", "0");

            var q15 = new DformItemEntity();
            q15.FormId = formid;
            q15.Label = "How many modem  available?";
            q15.IsRequired = true;
            q15.FormItemType = DformItemType.Text;
            q15.Order = 15;
            q15.IdCode = GetItemCode(15);
            q15.ValidationRegex = @"\d{1,10}";
            q15.ValidationText = "Enter valid No of Modem.";
            q15.HelpText = "Available= 1 Not Available=0";
            q15.Section = "4|SAFARICOM PRODUCT AVAILABILITY|4| Modems";
            
            var formitem15 = CreateFormItem(q15);
            //var formitem15 = CreateFormItem(formid, GetItemCode(15), "How many modem  available?", DformItemType.Text,
            //                                15, true, "Enter valid No of Modem.", @"\d{1,10}");
            CreateItemRespondentType(formitem15, delearshop);
            CreateItemRespondentType(formitem15, mpesa);
            CreateItemRespondentType(formitem15, retailshop);
            CreateItemRespondentType(formitem15, digitalvilage);

            var q16 = new DformItemEntity();
            q16.FormId = formid;
            q16.Label = "Is Safaricom 5 Shilling airtime available?";
            q16.IsRequired = true;
            q16.FormItemType = DformItemType.DropdownList;
            q16.Order = 16;
            q16.IdCode = GetItemCode(16);
            q16.ValidationRegex = "";
            q16.ValidationText = "";
            q16.HelpText = "5";
            q16.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
           
            var formitem16 = CreateFormItem(q16);
            //var formitem16 = CreateFormItem(formid, GetItemCode(16), "Is Safaricom 5 Shilling airtime available?",
            //                                DformItemType.DropdownList, 16, true);
            CreateItemRespondentType(formitem16, delearshop);
            CreateItemRespondentType(formitem16, mpesa);
            CreateItemRespondentType(formitem16, retailshop);
            CreateItemRespondentType(formitem16, digitalvilage);
            CreateAnswers(formitem16, GetItemAnsCode(16, 1), "YES", "1");
            CreateAnswers(formitem16, GetItemAnsCode(16, 2), "NO", "0");

            var q17 = new DformItemEntity();
            q17.FormId = formid;
            q17.Label = "Is Safaricom 10 Shilling airtime available?";
            q17.IsRequired = true;
            q17.FormItemType = DformItemType.DropdownList;
            q17.Order = 17;
            q17.IdCode = GetItemCode(17);
            q17.ValidationRegex = "";
            q17.ValidationText = "";
            q17.HelpText = "10";
            q17.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
            
            var formitem17 = CreateFormItem(q17);
            //var formitem17 = CreateFormItem(formid, GetItemCode(17), "Is Safaricom 10 Shilling airtime available?",
            //                                DformItemType.DropdownList, 17, true);
            CreateItemRespondentType(formitem17, delearshop);
            CreateItemRespondentType(formitem17, mpesa);
            CreateItemRespondentType(formitem17, retailshop);
            CreateItemRespondentType(formitem17, digitalvilage);
            CreateAnswers(formitem17, GetItemAnsCode(17, 1), "YES", "1");
            CreateAnswers(formitem17, GetItemAnsCode(17, 2), "NO", "0");

            var q18 = new DformItemEntity();
            q18.FormId = formid;
            q18.Label = "Is Safaricom 20 Shilling airtime available?";
            q18.IsRequired = true;
            q18.FormItemType = DformItemType.DropdownList;
            q18.Order = 18;
            q18.IdCode = GetItemCode(18);
            q18.ValidationRegex = "";
            q18.ValidationText = "";
            q18.HelpText = "20";
            q18.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
           
            var formitem18 = CreateFormItem(q18);
            //var formitem18 = CreateFormItem(formid, GetItemCode(18), "Is Safaricom 20 Shilling airtime available?",
            //                                DformItemType.DropdownList, 18, true);
            CreateItemRespondentType(formitem18, delearshop);
            CreateItemRespondentType(formitem18, mpesa);
            CreateItemRespondentType(formitem18, retailshop);
            CreateItemRespondentType(formitem18, digitalvilage);
            CreateAnswers(formitem18, GetItemAnsCode(18, 1), "YES", "1");
            CreateAnswers(formitem18, GetItemAnsCode(18, 2), "NO", "0");

            var q19 = new DformItemEntity();
            q19.FormId = formid;
            q19.Label = "Is Safaricom 50 Shilling airtime available?";
            q19.IsRequired = true;
            q19.FormItemType = DformItemType.DropdownList;
            q19.Order = 19;
            q19.IdCode = GetItemCode(19);
            q19.ValidationRegex = "";
            q19.ValidationText = "";
            q19.HelpText = "50";
            q19.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
            
            var formitem19 = CreateFormItem(q19);
            //var formitem19 = CreateFormItem(formid, GetItemCode(19), "Is Safaricom 50 Shilling airtime available?",
            //                                DformItemType.DropdownList, 19, true);
            CreateItemRespondentType(formitem19, delearshop);
            CreateItemRespondentType(formitem19, mpesa);
            CreateItemRespondentType(formitem19, retailshop);
            CreateItemRespondentType(formitem19, digitalvilage);
            CreateAnswers(formitem19, GetItemAnsCode(19, 1), "YES", "1");
            CreateAnswers(formitem19, GetItemAnsCode(19, 2), "NO", "0");

            var q20 = new DformItemEntity();
            q20.FormId = formid;
            q20.Label = "Is Safaricom 100 Shilling airtime available?";
            q20.IsRequired = true;
            q20.FormItemType = DformItemType.DropdownList;
            q20.Order = 20;
            q20.IdCode = GetItemCode(20);
            q20.ValidationRegex = "";
            q20.ValidationText = "";
            q20.HelpText = "100";
            q20.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
           
            var formitem20 = CreateFormItem(q20);
            //var formitem20 = CreateFormItem(formid, GetItemCode(20), "Is Safaricom 100 Shilling airtime available?",
            //                                DformItemType.DropdownList, 20, true);
            CreateItemRespondentType(formitem20, delearshop);
            CreateItemRespondentType(formitem20, mpesa);
            CreateItemRespondentType(formitem20, retailshop);
            CreateItemRespondentType(formitem20, digitalvilage);
            CreateAnswers(formitem20, GetItemAnsCode(20, 1), "YES", "1");
            CreateAnswers(formitem20, GetItemAnsCode(20, 2), "NO", "0");

            var q21 = new DformItemEntity();
            q21.FormId = formid;
            q21.Label = "Is Safaricom 250 Shilling airtime available?";
            q21.IsRequired = true;
            q21.FormItemType = DformItemType.DropdownList;
            q21.Order = 21;
            q21.IdCode = GetItemCode(21);
            q21.ValidationRegex = "";
            q21.ValidationText = "";
            q21.HelpText = "250";
            q21.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
            
            var formitem21 = CreateFormItem(q21);
            //var formitem21 = CreateFormItem(formid, GetItemCode(21), "Is Safaricom 250 Shilling airtime available?",
            //                                DformItemType.DropdownList, 21, true);
            CreateItemRespondentType(formitem21, delearshop);
            CreateItemRespondentType(formitem21, mpesa);
            CreateItemRespondentType(formitem21, retailshop);
            CreateItemRespondentType(formitem21, digitalvilage);
            CreateAnswers(formitem21, GetItemAnsCode(21, 1), "YES", "1");
            CreateAnswers(formitem21, GetItemAnsCode(21, 2), "NO", "0");

            var q22 = new DformItemEntity();
            q22.FormId = formid;
            q22.Label = "Is Safaricom Other HDV airtime available?";
            q22.IsRequired = true;
            q22.FormItemType = DformItemType.DropdownList;
            q22.Order = 22;
            q22.IdCode = GetItemCode(22);
            q22.ValidationRegex = "";
            q22.ValidationText = "";
            q22.HelpText = "Other HDV";
            q22.Section = "4|SAFARICOM PRODUCT AVAILABILITY|5| Safaricom airtime availability";
           
            var formitem22 = CreateFormItem(q22);
            //var formitem22 = CreateFormItem(formid, GetItemCode(22), "Is Safaricom Other HDV airtime available?",
            //                                DformItemType.DropdownList, 22, true);
            CreateItemRespondentType(formitem22, delearshop);
            CreateItemRespondentType(formitem22, mpesa);
            CreateItemRespondentType(formitem22, retailshop);
            CreateItemRespondentType(formitem22, digitalvilage);
            CreateAnswers(formitem22, GetItemAnsCode(22, 1), "YES", "1");
            CreateAnswers(formitem22, GetItemAnsCode(22, 2), "NO", "0");

            var q23 = new DformItemEntity();
            q23.FormId = formid;
            q23.Label = "Is ORANGE aritime available?";
            q23.IsRequired = true;
            q23.FormItemType = DformItemType.DropdownList;
            q23.Order = 23;
            q23.IdCode = GetItemCode(23);
            q23.ValidationRegex = "";
            q23.ValidationText = "";
            q23.HelpText = "Orange";
            q23.Section = "5|COMPETITION|1| Airtime availability";
           
            var formitem23 = CreateFormItem(q23);
            //var formitem23 = CreateFormItem(formid, GetItemCode(23), "Is ORANGE aritime available?",
            //                                DformItemType.DropdownList, 23, true);
            CreateItemRespondentType(formitem23, delearshop);
            CreateItemRespondentType(formitem23, mpesa);
            CreateItemRespondentType(formitem23, retailshop);
            CreateItemRespondentType(formitem23, digitalvilage);
            CreateAnswers(formitem23, GetItemAnsCode(23, 1), "YES", "1");
            CreateAnswers(formitem23, GetItemAnsCode(23, 2), "NO", "0");

            var q24 = new DformItemEntity();
            q24.FormId = formid;
            q24.Label = "Is YU aritime available?";
            q24.IsRequired = true;
            q24.FormItemType = DformItemType.DropdownList;
            q24.Order = 24;
            q24.IdCode = GetItemCode(24);
            q24.ValidationRegex = "";
            q24.ValidationText = "";
            q24.HelpText = "YU";
            q24.Section = "5|COMPETITION|1| Airtime availability";
           
            var formitem24 = CreateFormItem(q24);
            //var formitem24 = CreateFormItem(formid, GetItemCode(24), "Is YU aritime available?",
            //                                DformItemType.DropdownList, 24, true);
            CreateItemRespondentType(formitem24, delearshop);
            CreateItemRespondentType(formitem24, mpesa);
            CreateItemRespondentType(formitem24, retailshop);
            CreateItemRespondentType(formitem24, digitalvilage);
            CreateAnswers(formitem24, GetItemAnsCode(24, 1), "YES", "1");
            CreateAnswers(formitem24, GetItemAnsCode(24, 2), "NO", "0");

            var q25 = new DformItemEntity();
            q25.FormId = formid;
            q25.Label = "Is AIRTEL aritime available?";
            q25.IsRequired = true;
            q25.FormItemType = DformItemType.DropdownList;
            q25.Order = 25;
            q25.IdCode = GetItemCode(25);
            q25.ValidationRegex = "";
            q25.ValidationText = "";
            q25.HelpText = "AIRTEL";
            q25.Section = "5|COMPETITION|1| Airtime availability";
           
            var formitem25 = CreateFormItem(q25);
            //var formitem25 = CreateFormItem(formid, GetItemCode(25), "Is AIRTEL aritime available?",
            //                                DformItemType.DropdownList, 25, true);
            CreateItemRespondentType(formitem25, delearshop);
            CreateItemRespondentType(formitem25, mpesa);
            CreateItemRespondentType(formitem25, retailshop);
            CreateItemRespondentType(formitem25, digitalvilage);
            CreateAnswers(formitem25, GetItemAnsCode(25, 1), "YES", "1");
            CreateAnswers(formitem25, GetItemAnsCode(25, 2), "NO", "0");

            //  visibility Vs all other companies' materials (approx %)

            var q26 = new DformItemEntity();
            q26.FormId = formid;
            q26.Label = "Visibility Vs all other companies' materials (approx %)?";
            q26.IsRequired = true;
            q26.FormItemType = DformItemType.Text;
            q26.Order = 26;
            q26.IdCode = GetItemCode(26);
            q26.ValidationRegex = @"\d{1,2}";
            q26.ValidationText = "Enter valid  %";
            q26.HelpText = "visibility Vs all other companies' materials (approx %)";
            q26.Section = "6|BRANDING|1| BRANDING";
           
            var formitem26 = CreateFormItem(q26);
            //var formitem26 = CreateFormItem(formid, GetItemCode(26),
            //                                "Visibility Vs all other companies' materials (approx %)?",
            //                                DformItemType.Text, 26, true, "Enter valid  %", @"\d{1,2}");
            CreateItemRespondentType(formitem26, delearshop);
            CreateItemRespondentType(formitem26, mpesa);
            CreateItemRespondentType(formitem26, retailshop);
            CreateItemRespondentType(formitem26, digitalvilage);

            //missing Merchandising materials
            var q27 = new DformItemEntity();
            q27.FormId = formid;
            q27.Label = "Missing Merchandising materials?";
            q27.IsRequired = true;
            q27.FormItemType = DformItemType.Text;
            q27.Order = 27;
            q27.IdCode = GetItemCode(27);
            q27.ValidationRegex = "";
            q27.ValidationText = "";
            q27.HelpText = "Missing Merchandising materials";
            q27.Section = "6|BRANDING|1| BRANDING";
           
            var formitem27 = CreateFormItem(q27);
            //var formitem27 = CreateFormItem(formid, GetItemCode(27), "Missing Merchandising materials?",
            //                                DformItemType.Text, 27, true);
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
