package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;

public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public List<CityDTO> findAll() {
		List<City> list= cityRepository.findAll();
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	}
}
