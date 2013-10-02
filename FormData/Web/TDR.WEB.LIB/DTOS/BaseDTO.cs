using System;

namespace TDR.WEB.LIB.DTOS
{
    public abstract  class BaseDTO
    {

        public Guid Id { get; set; }
        public bool IsActive { get; set; }
    }
    public  class IDDTO : BaseDTO
    {

        
    }
}