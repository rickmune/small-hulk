package com.maina.formdatav2.enums;


import com.maina.formdatav2.repository.IDFormItemAnswerRepository;
import com.maina.formdatav2.repository.IDFormItemRepository;
import com.maina.formdatav2.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdatav2.repository.IDFormRepository;
import com.maina.formdatav2.repository.IDFormRespondentTypeRepository;
import com.maina.formdatav2.repository.IDFormResultItemRepository;
import com.maina.formdatav2.repository.IDFormResultRepository;
import com.maina.formdatav2.repository.IDTownRepository;
import com.maina.formdatav2.repository.IDUserRepository;
import com.maina.formdatav2.repository.IImagePathRepository;
import com.maina.formdatav2.repository.IMessageRepository;
import com.maina.formdatav2.repository.IPhonConfig;
import com.maina.formdatav2.repository.ISecurityQuestionRepository;
import com.maina.formdatav2.repository.impl.DFormItemAnswerRepository;
import com.maina.formdatav2.repository.impl.DFormItemRepository;
import com.maina.formdatav2.repository.impl.DFormItemRespondentTypeRepository;
import com.maina.formdatav2.repository.impl.DFormRepository;
import com.maina.formdatav2.repository.impl.DFormRespondentTypeRepository;
import com.maina.formdatav2.repository.impl.DFormResultItemRepository;
import com.maina.formdatav2.repository.impl.DFormResultRepository;
import com.maina.formdatav2.repository.impl.ImagePathRepository;
import com.maina.formdatav2.repository.impl.MessageRepository;
import com.maina.formdatav2.repository.impl.PhoneConfigRepository;
import com.maina.formdatav2.repository.impl.DUserRepository;
import com.maina.formdatav2.repository.impl.SecurityQuestionRepository;
import com.maina.formdatav2.repository.impl.DTownRepository;

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
