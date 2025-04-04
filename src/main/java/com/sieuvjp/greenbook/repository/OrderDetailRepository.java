package com.sieuvjp.greenbook.repository;

import com.sieuvjp.greenbook.entity.Book;
import com.sieuvjp.greenbook.entity.Order;
import com.sieuvjp.greenbook.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    List<OrderDetail> findByBook(Book book);

    @Query("SELECT od.book.id, SUM(od.quantity) as total FROM OrderDetail od JOIN od.order o WHERE o.status = 'COMPLETED' GROUP BY od.book.id ORDER BY total DESC")
    List<Object[]> findMostSoldBooks(int limit);

    @Query("SELECT SUM(od.quantity * (od.originalPrice - od.discountedPrice)) FROM OrderDetail od JOIN od.order o WHERE o.status = 'COMPLETED'")
    Double getTotalDiscount();
}