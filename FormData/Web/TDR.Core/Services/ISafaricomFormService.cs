using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.Core.Services
{
  public  interface ISafaricomFormService
  {
      void CreateDailyData();
      void CreateDailySale();
      void CreateDailyTraining();
  }
}
