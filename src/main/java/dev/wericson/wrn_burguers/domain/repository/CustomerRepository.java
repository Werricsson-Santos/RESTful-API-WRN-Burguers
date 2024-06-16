package dev.wericson.wrn_burguers.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.wericson.wrn_burguers.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	boolean existsByCpfNumber(String CPF);

}
