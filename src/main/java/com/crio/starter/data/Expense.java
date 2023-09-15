package com.crio.starter.data;


import com.crio.starter.enums.SplitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Integer expenseId;
    private Integer paidBy;
    private Integer amountPaid;
    private List<User> participants = new ArrayList<>();
    private SplitType splitType;
    private List<Long> splits;
}
