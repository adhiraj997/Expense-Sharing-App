package com.crio.starter.service;

import com.crio.starter.data.Expense;
import com.crio.starter.data.User;
import com.crio.starter.enums.SplitType;
import com.crio.starter.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

import static java.util.Objects.nonNull;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void addExpense(Expense expense) {
        if (nonNull(expense)) {
            expenseRepository.addExpense(expense);
        }
    }

    public Map<Integer, Long> showExpenses(String userId) {
        if (nonNull(userId)) {
            Map<Integer, Expense> expenseMap = expenseRepository.fetchAllExpenses();
            Set<Integer> uniqueUsers = new HashSet<>();
            for (Map.Entry<Integer, Expense> entry : expenseMap.entrySet()) {
                if (nonNull(entry)) {
                    if (nonNull(entry.getValue()) && nonNull(entry.getValue().getParticipants())) {
                        for (User user : entry.getValue().getParticipants()) {
                            if (nonNull(user)) {
                                uniqueUsers.add(user.getUserId());
                            }
                        }
                    }
                }
            }
            Map<Integer, Long> owingMap = new HashMap<>();
            for (Integer user : uniqueUsers) {
                owingMap.put(user, 0L);
            }

            for (Map.Entry<Integer, Expense> entry : expenseMap.entrySet()) {
                if (nonNull(entry) && nonNull(entry.getValue()) && entry.getValue().getPaidBy().equals(userId)) {
                    Expense expense = entry.getValue();
                    if (nonNull(expense.getSplitType()) && expense.getSplitType().equals(SplitType.EQUAL)) {
                        splitForEqual(entry, userId, owingMap);
                    }

                    if (nonNull(expense.getSplitType()) && expense.getSplitType().equals(SplitType.EXACT)) {
                        splitForExact(entry, userId, owingMap);
                    }

                }
                return owingMap;
            }
        }

        return null;
    }

    private void splitForEqual(Map.Entry<Integer, Expense> entry, String userId, Map<Integer, Long> owingMap) {
        if (nonNull(entry) && nonNull(entry.getValue()) && entry.getValue().getPaidBy().equals(userId)) {
            Expense expense = entry.getValue();
            if (nonNull(expense.getSplitType()) && expense.getSplitType().equals(SplitType.EQUAL)) {
                int totalExpense = expense.getAmountPaid();
                double eachSplit = totalExpense / (double) expense.getParticipants().size();
                long split = Math.round(eachSplit);

                for (User user : expense.getParticipants()) {
                    if (nonNull(user)) {
                        owingMap.put(user.getUserId(), split);
                    }

                }
            }
        }
    }


    private void splitForExact(Map.Entry<Integer, Expense> entry, String userId, Map<Integer, Long> owingMap) {
        if (nonNull(entry) && nonNull(entry.getValue()) && entry.getValue().getPaidBy().equals(userId)) {
            Expense expense = entry.getValue();
            if (nonNull(expense.getSplitType()) && expense.getSplitType().equals(SplitType.EXACT)) {
                int totalExpense = expense.getAmountPaid();
                for (int i = 0; i < expense.getParticipants().size(); i++) {
                    owingMap.put(expense.getParticipants().get(i).getUserId(), expense.getSplits().get(i));
                }

            }

        }
    }
}
