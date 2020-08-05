package com.gigpeople.app.model;

public class AmountSpentModel {
    String amount,date;

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public AmountSpentModel(String amount, String date) {
        this.amount=amount;
        this.date=date;

    }

}
