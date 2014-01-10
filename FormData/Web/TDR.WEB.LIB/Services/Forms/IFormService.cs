using System;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Forms;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Forms
{
    public interface IFormService
    {
        void Save(FormDTO dto);
        void Save(FormRespondentTypeDTO dto);
        FormDTO GetById(Guid id);
        FormRespondentTypeDTO GetRespondentTypeById(Guid id);
        FormItemDTO GetItemById(Guid id);
        TranferResponse<FormDTO> Query(QueryBase query);
        TranferResponse<FormRespondentTypeDTO> QueryRespondentType(QueryBase query);
        TranferResponse<FormItemDTO> QueryItem(QueryBase query);
        BasicResponse Save(FormItemDTO dto);

        
        

    }
}
