package com.crio.starter.repository;

import com.crio.starter.data.Expense;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


import static java.util.Objects.nonNull;
@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private static int id = 1;

    private Map<Integer, Expense> expenses = new HashMap<>();
    @Override
    public void addExpense(Expense expense) {
        if (nonNull(expense)) {
            expense.setExpenseId(++id);
            expenses.put(id, expense);
        }
    }

    @Override
    public Map<Integer, Expense> fetchAllExpenses() {
        return expenses;
    }
}
