package com.crio.starter.service;

import com.crio.starter.data.Expense;

import java.util.Map;

public interface ExpenseService {

    void addExpense(Expense expense);

    Map<Integer, Long> showExpenses(String userId);
}
