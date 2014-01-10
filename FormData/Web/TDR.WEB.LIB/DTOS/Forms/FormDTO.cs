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
      public FormItemDTO()
      {
          RespondentTypes = new List<Guid>();
      }

      public int Order { get; set; }
      public string Label { set; get; }
      public string HelpText { set; get; }
      public string Section { set; get; }
     
      public int FormItemTypeId { set; get; }
      public string FormItemType { set; get; }
      
      public Guid FormId { get; set; }
      public string FormName { get; set; }
      public bool IsRequired { set; get; }
      public string ValidationText { set; get; }
      public string ValidationRegex { set; get; }

      
      public string Code { get; set; }
      public List<Guid> RespondentTypes { get; set; }
     

  }
}
