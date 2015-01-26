package com.maina.formdatav2.enums;

import com.maina.formdatav2.entity.DFormMessages;
import com.maina.formdatav2.entity.DTownE;
import com.maina.formdatav2.entity.DUserE;
import com.maina.formdatav2.entity.DformE;
import com.maina.formdatav2.entity.DformItemAnswerE;
import com.maina.formdatav2.entity.DformItemE;
import com.maina.formdatav2.entity.DformItemRespondentTypeE;
import com.maina.formdatav2.entity.DformRespondentTypeE;
import com.maina.formdatav2.entity.DformResultE;
import com.maina.formdatav2.entity.DformResultItemE;
import com.maina.formdatav2.entity.ImagePath;
import com.maina.formdatav2.entity.PhonConfig;
import com.maina.formdatav2.entity.QuestionList;

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
