package com.davidfacsko.service.mapper;

import com.davidfacsko.domain.*;
import com.davidfacsko.service.dto.GuestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Guest and its DTO GuestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GuestMapper extends EntityMapper<GuestDTO, Guest> {



    default Guest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Guest guest = new Guest();
        guest.setId(id);
        return guest;
    }
}
