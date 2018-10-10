package com.davidfacsko.service.mapper;

import com.davidfacsko.domain.*;
import com.davidfacsko.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "user.id", target = "userId")
    ReviewDTO toDto(Review review);

    @Mapping(source = "userId", target = "user")
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
