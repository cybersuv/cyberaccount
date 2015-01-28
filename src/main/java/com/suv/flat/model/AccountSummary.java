package com.suv.flat.model;

public class AccountSummary {
	double currentBalance;
	double currentMonthDeposit;
	double currentMonthExpense;
	double previousBalance;
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public double getCurrentMonthDeposit() {
		return currentMonthDeposit;
	}
	public void setCurrentMonthDeposit(double currentMonthDeposit) {
		this.currentMonthDeposit = currentMonthDeposit;
	}
	public double getCurrentMonthExpense() {
		return currentMonthExpense;
	}
	public void setCurrentMonthExpense(double currentMonthExpense) {
		this.currentMonthExpense = currentMonthExpense;
	}
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	@Override
	public String toString() {
		return "AccountSummary [currentBalance=" + currentBalance
				+ ", currentMonthDeposit=" + currentMonthDeposit
				+ ", currentMonthExpense=" + currentMonthExpense
				+ ", previousBalance=" + previousBalance + "]";
	}
	
	
}
