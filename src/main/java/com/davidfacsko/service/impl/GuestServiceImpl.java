package com.davidfacsko.service.impl;

import com.davidfacsko.service.GuestService;
import com.davidfacsko.domain.Guest;
import com.davidfacsko.repository.GuestRepository;
import com.davidfacsko.service.dto.GuestDTO;
import com.davidfacsko.service.mapper.GuestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Guest.
 */
@Service
@Transactional
public class GuestServiceImpl implements GuestService {

    private final Logger log = LoggerFactory.getLogger(GuestServiceImpl.class);

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestServiceImpl(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    /**
     * Save a guest.
     *
     * @param guestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GuestDTO save(GuestDTO guestDTO) {
        log.debug("Request to save Guest : {}", guestDTO);
        Guest guest = guestMapper.toEntity(guestDTO);
        guest = guestRepository.save(guest);
        return guestMapper.toDto(guest);
    }

    /**
     * Get all the guests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GuestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Guests");
        return guestRepository.findAll(pageable)
            .map(guestMapper::toDto);
    }


    /**
     * Get one guest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GuestDTO> findOne(Long id) {
        log.debug("Request to get Guest : {}", id);
        return guestRepository.findById(id)
            .map(guestMapper::toDto);
    }

    /**
     * Delete the guest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Guest : {}", id);
        guestRepository.deleteById(id);
    }
}
