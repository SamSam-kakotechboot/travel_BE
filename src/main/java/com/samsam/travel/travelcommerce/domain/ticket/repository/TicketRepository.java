package com.samsam.travel.travelcommerce.domain.ticket.repository;

import com.samsam.travel.travelcommerce.entity.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query("SELECT COUNT(t) FROM Ticket t")
    long countTickets();

    @Modifying
    @Transactional
    @Query(
            "UPDATE Ticket t " +
                    "SET " +
                    "t.title        =   :#{#ticket.title}, " +
                    "t.contents     =   :#{#ticket.contents}, " +
                    "t.place        =   :#{#ticket.place}, " +
                    "t.price        =   :#{#ticket.price}, " +
                    "t.startDate    =   :#{#ticket.startDate}, " +
                    "t.endDate      =   :#{#ticket.endDate} " +
                    "WHERE t.ticketId   =   :#{#ticket.ticketId} " +
                    "AND t.user.userId  =   :#{#ticket.user.userId}"
    )
    int updateTicket(Ticket ticket);

    @Query("SELECT " +
            "t.ticketId, " +
            "t.user, " +
            "t.title, " +
            "t.place, " +
            "t.price, " +
            "t.startDate, " +
            "t.endDate, " +
            "t.deleteYn, " +
            "t.registDate, " +
            "t.updateDate, " +
            "COALESCE(AVG(CASE WHEN r.deleteYn = 'N' THEN r.rating ELSE null END), 0) as avgRating " +
            "FROM Ticket t " +
            "LEFT JOIN t.reviews r ON r.deleteYn = 'N' " +
            "GROUP BY t.ticketId " +
            "ORDER BY " +
            "CASE WHEN LOWER(:keyword) = 'price' THEN t.price END ASC, " +
            "CASE WHEN LOWER(:keyword) = 'rating' THEN AVG(CASE WHEN r.deleteYn = 'N' THEN r.rating ELSE null END) END DESC, " +
            "CASE WHEN LOWER(:keyword) = 'latest' THEN t.registDate END DESC"
    )
    List<Object[]> findAll(@Param("keyword") String keyword, Pageable pageable);


    @Modifying
    @Transactional
    @Query(
            "UPDATE Ticket t " +
                    "SET t.deleteYn         =   'Y' " +
                    "WHERE t.ticketId       =   :#{#ticket.ticketId} " +
                    "AND t.user.userId  =   :#{#ticket.user.userId} "
    )
    void deleteTicket(Ticket ticket);
}
