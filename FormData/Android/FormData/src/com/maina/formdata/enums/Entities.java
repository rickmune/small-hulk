package com.maina.formdata.enums;

import com.maina.formdata.entity.DUserE;
import com.maina.formdata.entity.DformE;
import com.maina.formdata.entity.DformItemAnswerE;
import com.maina.formdata.entity.DformItemE;
import com.maina.formdata.entity.DformItemRespondentTypeE;
import com.maina.formdata.entity.DformRespondentTypeE;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.entity.DformResultItemE;
import com.maina.formdata.entity.PhonConfig;

public enum Entities {

	DFORM(DformE.class),
	DFORMITEMANSWER(DformItemAnswerE.class),
	DFORMITEM(DformItemE.class),
	DFORMITEMRESPONDENTTYPE(DformItemRespondentTypeE.class),
	DFORMRESPONDENTTYPE(DformRespondentTypeE.class),
	DFORMRESULT(DformResultE.class),
	DFORMRESULTITEM(DformResultItemE.class),
	DUSERE(DUserE.class),
	CONFIG(PhonConfig.class);
	
	@SuppressWarnings("rawtypes")
	public final Class Value;

	private <T> Entities(Class<T> value) {
		Value = value;
	}
}
