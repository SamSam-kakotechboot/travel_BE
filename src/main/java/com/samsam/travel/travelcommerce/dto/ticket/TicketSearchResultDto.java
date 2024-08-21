package com.samsam.travel.travelcommerce.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchResultDto {
    private List<TicketSearchResponseDto> tickets;
    private long totalItems;
}