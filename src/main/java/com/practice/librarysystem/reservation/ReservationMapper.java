package com.practice.librarysystem.reservation;

import com.practice.librarysystem.reservation.dto.CreateReservationDTO;
import com.practice.librarysystem.reservation.dto.ReservationDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setCreatedAt(reservation.getCreatedAt());
        dto.setStartDate(reservation.getStartDate());
        dto.setDueDate(reservation.getDueDate());
        dto.setReturnDate(reservation.getReturnDate());
        dto.setStatus(reservation.getStatus());
        dto.setReserverId(reservation.getReserver().getId());
        dto.setBookId(reservation.getBook().getId());
        dto.setOverdue(reservation.isOverdue());
        dto.setReserverName(reservation.getReserver().getEmail());
        dto.setBookTitle(reservation.getBook().getTitle());

        return dto;
    }

    public Reservation toEntity(CreateReservationDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setStartDate(createDTO.getStartDate());
        reservation.setDueDate(createDTO.getDueDate());
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservation;
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setCreatedAt(dto.getCreatedAt());
        reservation.setStartDate(dto.getStartDate());
        reservation.setDueDate(dto.getDueDate());
        reservation.setReturnDate(dto.getReturnDate());
        reservation.setStatus(dto.getStatus());

        return reservation;
    }

    public List<ReservationDTO> toDTOList(List<Reservation> reservations) {
        if (reservations == null) {
            return null;
        }

        return reservations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Reservation> toEntityList(List<ReservationDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(Reservation reservation, ReservationDTO dto) {
        if (reservation == null || dto == null) {
            return;
        }

        reservation.setStartDate(dto.getStartDate());
        reservation.setDueDate(dto.getDueDate());
        reservation.setReturnDate(dto.getReturnDate());
        reservation.setStatus(dto.getStatus());
    }
}