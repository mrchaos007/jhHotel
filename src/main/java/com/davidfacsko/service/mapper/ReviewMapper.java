package com.davidfacsko.service.mapper;

import com.davidfacsko.domain.*;
import com.davidfacsko.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {GuestMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "guest.id", target = "guestId")
    ReviewDTO toDto(Review review);

    @Mapping(source = "guestId", target = "guest")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
