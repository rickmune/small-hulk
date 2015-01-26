package com.maina.formdata.enums;

import com.maina.formdata.repository.*;
import com.maina.formdata.repository.impl.*;

public enum RepositoryEnum {

	DFormItemAnswer(IDFormItemAnswerRepository.class, DFormItemAnswerRepository.class),
	DFormItem(IDFormItemRepository.class, DFormItemRepository.class),
	DFormRespondentType(IDFormRespondentTypeRepository.class, DFormRespondentTypeRepository.class),
	DFormItemRespondentType(IDFormItemRespondentTypeRepository.class, DFormItemRespondentTypeRepository.class),
	DForm(IDFormRepository.class, DFormRepository.class),
	DFormResultItem(IDFormResultItemRepository.class, DFormResultItemRepository.class),
	DFormResult(IDFormResultRepository.class, DFormResultRepository.class),
	DUserRepository(IDUserRepository.class, DUserRepository.class),
	PhonConfig(IPhonConfig.class, PhoneConfigRepository.class),
	SecurityQuestionRepository(ISecurityQuestionRepository.class, SecurityQuestionRepository.class),
	MessagesRepository(IMessageRepository.class, MessageRepository.class),
	DTownRepository(IDTownRepository.class, DTownRepository.class),
    ImagePathRepo(IImagePathRepository.class, ImagePathRepository.class);
	
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
