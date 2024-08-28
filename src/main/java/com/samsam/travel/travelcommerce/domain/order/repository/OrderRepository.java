package com.samsam.travel.travelcommerce.domain.order.repository;

import com.samsam.travel.travelcommerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, String> {

    @Query("SELECT o FROM Orders o WHERE o.user.userId = :userId")
    List<Orders> findOrdersByUserId(@Param("userId") String userId);

    @Query("SELECT o FROM Orders o WHERE o.status = 'P'")
    List<Orders> findOrdersByMaster();

    /**
     * 주문 상태를 'C'(Complete)으로 업데이트합니다.
     *     *
     * @param orderId 취소할 주문의 고유 ID.
     *
     * @throws IllegalArgumentException orderId가 null이거나 빈 경우.
     *
     * @see Orders
     */
    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.status = 'C' WHERE o.orderId = :orderId")
    void completeOrderById(String orderId);
}
