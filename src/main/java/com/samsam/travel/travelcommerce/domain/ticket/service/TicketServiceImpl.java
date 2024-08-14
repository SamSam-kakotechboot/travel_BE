package com.samsam.travel.travelcommerce.domain.ticket.service;

import com.samsam.travel.travelcommerce.domain.ticket.repository.TicketRepository;
import com.samsam.travel.travelcommerce.dto.ticket.SearchDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketResponseDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketSearchResponseDto;
import com.samsam.travel.travelcommerce.entity.Ticket;
import com.samsam.travel.travelcommerce.global.error.exception.UserNotFoundException;
import com.samsam.travel.travelcommerce.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.samsam.travel.travelcommerce.global.status.ErrorCode.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repository;

    private final Common common;

    @Override
    public List<TicketSearchResponseDto> getAllTicket(SearchDto searchDto) {
        Pageable pageable = PageRequest.of(searchDto.getPageNumber() - 1, searchDto.getPageSize());
        List<Object[]> result = repository.findAll(searchDto.getKeyword(), pageable);
        return result.stream()
                .map(TicketSearchResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto getTicketDetail(String ticketId) {
        Ticket ticket = repository.findById(ticketId)
                .orElseThrow(() -> new UserNotFoundException(NOT_EXIST_USER));
        return new TicketResponseDto().convertEntityToDto(ticket);
    }

    @Override
    public TicketResponseDto addTicket(TicketDto ticketDto) {
        String ticketId = common.getTargetUuid("ticket");
        ticketDto.setTicketId(ticketId);

        return new TicketResponseDto().convertEntityToDto(repository.save(Ticket.convertDtoToEntity(ticketDto)));
    }

    @Override
    public int updateTicket(TicketDto ticketDto) {
        return repository.updateTicket(Ticket.convertDtoToEntity(ticketDto));
    }

    @Override
    public void removeTicket(TicketDto ticketDto) {
        repository.deleteTicket(Ticket.convertDtoToEntity(ticketDto));
    }
}