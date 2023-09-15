package com.crio.starter.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSharingApplication {

    private List<User> users;
    private List<Expense> expenses;
}
