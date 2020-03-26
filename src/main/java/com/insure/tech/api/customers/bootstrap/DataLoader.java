package com.insure.tech.api.customers.bootstrap;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.insure.tech.api.customers.entity.Customer;
import com.insure.tech.api.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner{
	
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		if (customerRepository.count() == 0) {
			loadCustomerObjects();
			
		}
System.out.println("here"+customerRepository.count());	
	}

	private void loadCustomerObjects() {
		Customer c1 = Customer.builder().id(UUID.randomUUID()).firstName("bob")
				.lastName("dillon").active(Boolean.TRUE)
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastModifiedDate(new Timestamp(System.currentTimeMillis())).email("bob.dillon@email.com")
				.build();
		
		Customer c2 = Customer.builder().id(UUID.randomUUID()).firstName("Rob")
				.lastName("rillon").active(Boolean.TRUE)
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastModifiedDate(new Timestamp(System.currentTimeMillis())).email("Rob.rillon@email.com")
				.build();
		
		System.out.println("UUID ->"+customerRepository.save(c1).getId());
		System.out.println("UUID ->"+customerRepository.save(c2).getId());
		
	}
		

}
