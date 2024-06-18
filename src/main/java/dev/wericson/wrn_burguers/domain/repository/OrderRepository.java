package dev.wericson.wrn_burguers.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.wericson.wrn_burguers.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
