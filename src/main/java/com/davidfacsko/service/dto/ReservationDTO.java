package com.davidfacsko.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long roomId;

    private Long guestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (reservationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            ", room=" + getRoomId() +
            ", guest=" + getGuestId() +
            "}";
    }
}