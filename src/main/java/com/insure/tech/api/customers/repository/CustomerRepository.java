package com.insure.tech.api.customers.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insure.tech.api.customers.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,UUID>{

}
