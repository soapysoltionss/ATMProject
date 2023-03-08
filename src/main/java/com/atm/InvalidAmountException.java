package com.atm;

import java.math.BigDecimal;

public class InvalidAmountException extends Exception {
    private double amount;
    public InvalidAmountException(double amount) { 
        this.amount = amount; 
    }

    public void errorMessage() {
        if (this.amount < 0) {
            System.out.println("Amount must be greater than zero.");
        } else if ((BigDecimal.valueOf(this.amount).scale() > 2)) {
            System.out.println("Amount must not have more than 2dp.");
        } else if (this.amount == 0) {
            System.out.println("Amount to deposit can't be 0");
        }
    }

}