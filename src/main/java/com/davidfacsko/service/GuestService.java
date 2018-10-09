package com.davidfacsko.service;

import com.davidfacsko.service.dto.GuestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Guest.
 */
public interface GuestService {

    /**
     * Save a guest.
     *
     * @param guestDTO the entity to save
     * @return the persisted entity
     */
    GuestDTO save(GuestDTO guestDTO);

    /**
     * Get all the guests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GuestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" guest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GuestDTO> findOne(Long id);

    /**
     * Delete the "id" guest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
