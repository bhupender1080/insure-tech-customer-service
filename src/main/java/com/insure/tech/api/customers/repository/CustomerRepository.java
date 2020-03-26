package com.insure.tech.api.customers.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.insure.tech.api.customers.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer,UUID>{

}
