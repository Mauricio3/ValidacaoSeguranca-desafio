package com.devsuperior.bds04.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;

public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPageable(Pageable pageable) {
		Page<Event> list= eventRepository.findAll(pageable);
		return list.map(x -> new EventDTO(x));
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		copyToDto(dto, entity);
		entity = eventRepository.save(entity);
		return new EventDTO(entity);
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = eventRepository.getOne(id);
			copyToDto(dto, entity);
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	private void copyToDto(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		entity.setCity(new City(dto.getCityId(), null));
	}
}
