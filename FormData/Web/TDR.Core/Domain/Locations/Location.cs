using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Users;

namespace TDR.Core.Domain.Locations
{
     [Table("tblLocation")]
    public class Location : BaseEntity
    {
        [Required]
        public string Name { get; set; }
        public string Description { get; set; }
        public string Code { get; set; }
        public LocationStructure StructureId { set; get; }
        public Guid? ParentId { get; set; }
        public virtual ICollection<User> Users { set; get; }
       
    }

    public enum LocationStructure 
    {
        Country=1,
        Region=2,
        County=3,
        Town=4,
        Ward=5,
    }
}
