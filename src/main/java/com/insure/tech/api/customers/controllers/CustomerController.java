package com.insure.tech.api.customers.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insure.tech.api.customers.common.HelperUtility;
import com.insure.tech.api.customers.entity.Customer;
import com.insure.tech.api.customers.model.CustomerDto;
import com.insure.tech.api.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	
	@GetMapping("/")
	public List<Customer> getCustomers() {
		return (List<Customer>) customerRepository.findAll();
	}
	
	@GetMapping("/{id}")
	ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
		return new ResponseEntity<>(customerToCustomerDto(customerRepository.findById(id).get()), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Validated CustomerDto customerDto) {
		CustomerDto newCustomerDto = customerToCustomerDto(customerRepository.save(customerDtoToCustomer(customerDto)));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/customer/" + newCustomerDto.getId().toString());

		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public void updateCustomer(@PathVariable UUID id, CustomerDto customerDto) {
		Customer customerEntity = customerRepository.findById(id).get(); // TODO: need to create service and mapper
		customerEntity.setFirstName(customerDto.getFirstName()); // TODO: need to handle not found exception
		customerEntity.setLastName(customerDto.getLastName());
		customerEntity.setLastModifiedDate(new Timestamp(System.currentTimeMillis())); // TODO: need to handle with pre
																						// @anotation
		customerEntity.setEmail(customerDto.getEmail());
		customerRepository.save(customerEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable UUID id) {

		Customer customerEntity = customerRepository.findById(id).get();
		customerRepository.delete(customerEntity);

	}
	
	private Customer customerDtoToCustomer(CustomerDto customerDto) {
		return Customer.builder().id(UUID.randomUUID()).firstName(customerDto.getFirstName())
				.lastName(customerDto.getLastName()).active(Boolean.TRUE)
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastModifiedDate(new Timestamp(System.currentTimeMillis())).email(customerDto.getEmail())
				.build();

	}

	private CustomerDto customerToCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setEmail(customer.getEmail());
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setLastName(customer.getLastName());
		customerDto.setCreatedDate(HelperUtility.asOffsetDateTime(customer.getCreatedDate()));
		customerDto.setLastModifiedDate(HelperUtility.asOffsetDateTime(customer.getLastModifiedDate()));

		return customerDto;
	}

}
