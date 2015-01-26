package com.maina.formdata.enums;

import com.maina.formdata.entity.*;

public enum Entities {

	DFORM(DformE.class),
	DFORMITEMANSWER(DformItemAnswerE.class),
	DFORMITEM(DformItemE.class),
	DFORMITEMRESPONDENTTYPE(DformItemRespondentTypeE.class),
	DFORMRESPONDENTTYPE(DformRespondentTypeE.class),
	DFORMRESULT(DformResultE.class),
	DFORMRESULTITEM(DformResultItemE.class),
	DUSERE(DUserE.class),
	CONFIG(PhonConfig.class),
	SECQUESTION(QuestionList.class),
	MESSAGES(DFormMessages.class),
	TOWN(DTownE.class),
    IMAGEPATH(ImagePath.class);
	
	@SuppressWarnings("rawtypes")
	public final Class Value;

	private <T> Entities(Class<T> value) {
		Value = value;
	}
}
