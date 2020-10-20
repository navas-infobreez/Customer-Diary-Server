package com.planet.customer.diary.customer_diary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planet.customer.diary.customer_diary.entity.BaseEntity;
import com.planet.customer.diary.customer_diary.model.dto.BaseDTO;

@Service(value = "basicService")
public class BasicServiceImpl {
	
	@Autowired
	private ModelMapper modelMapper;
	
	protected BaseEntity mapDTOToEntity(final BaseDTO baseDTO , BaseEntity baseEntity){
		if (baseDTO != null) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			baseEntity = modelMapper.map(baseDTO, baseEntity.getClass());
		}
		return baseEntity;
	}

	protected List<? extends BaseDTO> mapEntitiesToDTOs(final List<? extends BaseEntity> baseEntityList,
			final Class<? extends BaseDTO> dtoClass) {
		List<? extends BaseDTO> tempDTOs = null;
		if (!baseEntityList.isEmpty()) {
			tempDTOs = baseEntityList.stream().map(beList -> mapEntityToDTO(beList, dtoClass))
					.collect(Collectors.toList());
		}
		return tempDTOs;
	}

	protected <E extends BaseDTO> E mapEntityToDTO(final BaseEntity entity, final Class<E> dtoClass) {
		E tempDTO = null;
		try {
			tempDTO = dtoClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (entity != null) {
			tempDTO = (E) modelMapper.map(entity, tempDTO.getClass());
			// tempUserDTO.setPassword(null);
			// tempUserDTO.mapUserContact(user);
			// tempUserDTO.mapUserRoles(user);
		}
		return tempDTO;
	}

}
