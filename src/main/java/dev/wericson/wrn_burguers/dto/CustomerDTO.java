package dev.wericson.wrn_burguers.dto;

import java.util.List;

import dev.wericson.wrn_burguers.domain.model.Order;

public class CustomerDTO {
    private Long id;
    private String name;
    private List<Order> orders;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
