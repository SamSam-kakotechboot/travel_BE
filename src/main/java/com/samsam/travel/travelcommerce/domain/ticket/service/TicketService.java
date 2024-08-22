package com.samsam.travel.travelcommerce.domain.ticket.service;

import com.samsam.travel.travelcommerce.dto.ticket.SearchDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketResponseDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketSearchResponseDto;

import java.util.List;

public interface TicketService {

    public List<TicketSearchResponseDto> getAllTicket(SearchDto searchDto);

    public TicketResponseDto getTicketDetail(String ticketId);

    public TicketResponseDto addTicket(TicketDto ticketDto);

    public int updateTicket(TicketDto ticketDto);

    public void removeTicket(TicketDto ticketDto);

    public long getTotalTicketCount(SearchDto searchDto);
}
