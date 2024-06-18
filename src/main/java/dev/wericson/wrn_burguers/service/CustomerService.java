package dev.wericson.wrn_burguers.service;

import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.dto.CustomerDTO;

public interface CustomerService extends CrudService<Long, Customer> {

	public CustomerDTO convertToDTO(Customer customer);

}
