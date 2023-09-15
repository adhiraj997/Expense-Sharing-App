package com.crio.starter.repository;

import com.crio.starter.data.Expense;

import java.util.Map;

public interface ExpenseRepository {

    void addExpense(Expense expense);

    Map<Integer, Expense> fetchAllExpenses();
}
