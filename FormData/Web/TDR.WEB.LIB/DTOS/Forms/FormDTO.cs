using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.WEB.LIB.DTOS.Forms
{
  public  class FormDTO:BaseDTO
    {
      
        [Required]
        public string Name { get; set; }
        [Required]
        public string Code { get; set; }
        [Required]
        public Guid ClientId { get; set; }
        public string ClientName { get; set; }
        
    }
  public class FormRespondentTypeDTO : BaseDTO
  {

      [Required]
      public string Name { get; set; }
      [Required]
      public string Value { get; set; }
      [Required]
      public string Code { get; set; }
      [Required]
      public Guid FormId { get; set; }
      public string FormName { get; set; }

  }
  public class FormItemDTO : BaseDTO
  {
      public int Order { get; set; }
      public string Label { set; get; }
       [Required]
      public int FormItemTypeId { set; get; }
      public string FormItemType { set; get; }
      [Required]
      public Guid FormId { get; set; }
      public string FormName { get; set; }
      public bool IsRequired { set; get; }
      public string ValidationText { set; get; }
      public string ValidationRegex { set; get; }

      [Required]
      public string Code { get; set; }
     

  }
}
