package com.vv.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long>{
	List<Expense> findByExpenseName(String lastName);
	@Override
	List<Expense> findAll();
}