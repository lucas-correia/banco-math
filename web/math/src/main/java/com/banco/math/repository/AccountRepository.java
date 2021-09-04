package com.banco.math.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.math.model.Account;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AccountRepository extends CrudRepository<Account, Integer> {

}