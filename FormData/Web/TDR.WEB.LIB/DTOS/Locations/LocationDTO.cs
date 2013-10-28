using System.ComponentModel.DataAnnotations;

namespace TDR.WEB.LIB.DTOS.Locations
{
    public class LocationDTO : BaseDTO
    {
         [Required]
        public string Name { get; set; }
        public string Description { get; set; }
         [Required]
        public string Code { get; set; }
        public string LocationStructureName { get; set; }
         [Required]
        public int LocationStructureId { get; set; }
       
       
    }
}
