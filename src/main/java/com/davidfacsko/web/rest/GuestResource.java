package com.davidfacsko.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.davidfacsko.service.GuestService;
import com.davidfacsko.web.rest.errors.BadRequestAlertException;
import com.davidfacsko.web.rest.util.HeaderUtil;
import com.davidfacsko.web.rest.util.PaginationUtil;
import com.davidfacsko.service.dto.GuestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Guest.
 */
@RestController
@RequestMapping("/api")
public class GuestResource {

    private final Logger log = LoggerFactory.getLogger(GuestResource.class);

    private static final String ENTITY_NAME = "guest";

    private final GuestService guestService;

    public GuestResource(GuestService guestService) {
        this.guestService = guestService;
    }

    /**
     * POST  /guests : Create a new guest.
     *
     * @param guestDTO the guestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guestDTO, or with status 400 (Bad Request) if the guest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guests")
    @Timed
    public ResponseEntity<GuestDTO> createGuest(@RequestBody GuestDTO guestDTO) throws URISyntaxException {
        log.debug("REST request to save Guest : {}", guestDTO);
        if (guestDTO.getId() != null) {
            throw new BadRequestAlertException("A new guest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestDTO result = guestService.save(guestDTO);
        return ResponseEntity.created(new URI("/api/guests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /guests : Updates an existing guest.
     *
     * @param guestDTO the guestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated guestDTO,
     * or with status 400 (Bad Request) if the guestDTO is not valid,
     * or with status 500 (Internal Server Error) if the guestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/guests")
    @Timed
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) throws URISyntaxException {
        log.debug("REST request to update Guest : {}", guestDTO);
        if (guestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GuestDTO result = guestService.save(guestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, guestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /guests : get all the guests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of guests in body
     */
    @GetMapping("/guests")
    @Timed
    public ResponseEntity<List<GuestDTO>> getAllGuests(Pageable pageable) {
        log.debug("REST request to get a page of Guests");
        Page<GuestDTO> page = guestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/guests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /guests/:id : get the "id" guest.
     *
     * @param id the id of the guestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the guestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/guests/{id}")
    @Timed
    public ResponseEntity<GuestDTO> getGuest(@PathVariable Long id) {
        log.debug("REST request to get Guest : {}", id);
        Optional<GuestDTO> guestDTO = guestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestDTO);
    }

    /**
     * DELETE  /guests/:id : delete the "id" guest.
     *
     * @param id the id of the guestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/guests/{id}")
    @Timed
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        log.debug("REST request to delete Guest : {}", id);
        guestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
