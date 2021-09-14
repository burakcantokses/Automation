/*
 * @Delta 2019-2021
 */
package io.deltaware;

/**
 * @Author burakcantokses
 */
public class BalanceManager {
    public int balance;
    public int maxBalance;
    public int purchase;
    public int addBalance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(int maxBalance) {
        this.maxBalance = maxBalance;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public int getAddBalance() {
        return addBalance;
    }

    public void setAddBalance(int addBalance) {
        this.addBalance = addBalance;
    }
}
