using System;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Forms;
using TDR.Core.Domain.Users;
using TDR.Core.Util;

namespace TDR.Core.Repository
{
    public interface IFormRepository
    {
        FormQueryResult QueryForm(QueryBase query);
        RespondentTypeQueryResult QueryFormRespondentType(QueryBase query);
        FormItemQueryResult QueryFormItem(QueryBase query);
        void SaveForm(DformEntity entity);
        void SaveFormRespondentType(DformRespondentTypeEntity entity);
        void SaveFormItem(DformItemEntity entity);
        void SaveFormItemRespondent(DformItemRespondentTypeEntity entity);
        void DeleteFormItemRespondent(Guid itemid);
        void DeleteFormItemAnswers(Guid itemid);
        void SaveFormItemAnswers(DformItemAnswerEntity entity);
    }
}
