package com.maina.formdata.enums;

import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.IDFormResultItemRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.impl.DFormItemAnswerRepository;
import com.maina.formdata.repository.impl.DFormItemRepository;
import com.maina.formdata.repository.impl.DFormItemRespondentTypeRepository;
import com.maina.formdata.repository.impl.DFormRepository;
import com.maina.formdata.repository.impl.DFormRespondentTypeRepository;
import com.maina.formdata.repository.impl.DFormResultItemRepository;
import com.maina.formdata.repository.impl.DFormResultRepository;
import com.maina.formdata.repository.impl.DUserRepository;
import com.maina.formdata.repository.impl.PhoneConfigRepository;

public enum RepositoryEnum {

	DFormItemAnswer(IDFormItemAnswerRepository.class, DFormItemAnswerRepository.class),
	DFormItem(IDFormItemRepository.class, DFormItemRepository.class),
	DFormRespondentType(IDFormRespondentTypeRepository.class, DFormRespondentTypeRepository.class),
	DFormItemRespondentType(IDFormItemRespondentTypeRepository.class, DFormItemRespondentTypeRepository.class),
	DForm(IDFormRepository.class, DFormRepository.class),
	DFormResultItem(IDFormResultItemRepository.class, DFormResultItemRepository.class),
	DFormResult(IDFormResultRepository.class, DFormResultRepository.class),
	DUserRepository(IDUserRepository.class, DUserRepository.class),
	PhonConfig(IPhonConfig.class, PhoneConfigRepository.class);
	
	@SuppressWarnings("rawtypes")
	public final Class IRepo;
	@SuppressWarnings("rawtypes")
	public final Class RealRepo;
	@SuppressWarnings("rawtypes")
	private RepositoryEnum(Class IRepo, Class RealRepo){
		this.IRepo = IRepo;
		this.RealRepo = RealRepo;
	}
}
