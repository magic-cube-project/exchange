package com.bean;

/**
 * Created by szc on 2018/8/23.
 */
public class AccountBalance {
    private String Address;
    private String Balance;
    private String Currency;
    private String Pending;
    private String Tag;

    public String getPending() {
        return Pending;
    }

    public void setPending(String pending) {
        Pending = pending;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }


    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
