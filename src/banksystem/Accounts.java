package banksystem;

import java.util.ArrayList;
import java.util.List;
import banksystem.Transactions.TYPE;

public class Accounts implements BalanceModification {

    // fields
    private int idNumber;
    private String accountNum;
    private double currentBalance;
    private List<Transactions> transactions;

    // constructor
    public Accounts(String line) {
        String[] accountsInfo = line.split(",");
        this.idNumber = validateIdNumber(Integer.parseInt(accountsInfo[0]));
        this.accountNum = validateAccountNum(accountsInfo[1]);
        this.currentBalance = Double.parseDouble(accountsInfo[2]);
        this.transactions = new ArrayList<>();
    }

    // check if the accountNum follows the standard IBAN
    private String validateAccountNum(String accountNum) {
        //split String accountNum
        String countryCode = accountNum.substring(0, 2);
        String checkCode = accountNum.substring(2, 4);
        String alphanumericChar = accountNum.substring(4);

        // check if country code is all letter.
        for (int i = 0; i < countryCode.length(); i++) {
            if (!Character.isUpperCase(countryCode.charAt(i))) {
                throw new IllegalArgumentException("Account Number should contain two "
                        + "upper-case letter country code.");
            }
        }

        // check if check code is all digit.
        for (int i = 0; i < checkCode.length(); i++) {
            if (!Character.isDigit(checkCode.charAt(i))) {
                throw new IllegalArgumentException("Account Number should contain two "
                        + "digits following country code.");
            }
        }

        //check if alphanumeric characteristics are letters or digits and the number of it is less than 36.
        if (alphanumericChar.length() < 36) {
            for (int i = 0; i < alphanumericChar.length(); i++) {
                if (!Character.isDigit(alphanumericChar.charAt(i))) {
                    if (!Character.isLetter(alphanumericChar.charAt(i))) {
                        throw new IllegalArgumentException("Alphanumeric characteristics "
                                + "should be digits or letters");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("The number of characters in alphanumeric"
                    + " characteristics can only up to 35.");
        }
        return accountNum;
    }

    // check if idNumber is valid
    private int validateIdNumber(int idNumber) {
        if (idNumber < 0) {
            throw new IllegalArgumentException("ID number cannot be negative");
        }
        return idNumber;
    }

    // getters
    public int getIdNumber() {
        return idNumber;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    // helper method to check the transactions of the account
    public List<Transactions> checkTransactionsRecord() {
        return transactions;
    }

    // helper method to check if balance is more than or equal to 0
    public boolean checkBalance() {
        if (currentBalance >= 0) {
            return true;
        } else {
            return false;
        }
    }

    // method to check amount of transaction
    private boolean checkAmount(double amount) {
        boolean amountState = false;
        if (amount > 0) {
            amountState = true;
        } else if (amount <= 0) {
            amountState = false;
        }
        return amountState;
    }

    // helper method to withdraw money from account
    public void withdrawal(double amount, String reference) {
        if (amount <= currentBalance && checkAmount(amount) == true) {
            decreaseBalance(amount);
            Transactions withdrawal = new Transactions(reference, TYPE.WITHDRAWAL, -amount);
            transactions.add(withdrawal);
            System.out.println("The withdrawal has completed.");
        } else {
            throw new IllegalArgumentException("The withdrawal amount can not be greater than the balance and the amount should be positive.");
        }
    }

    // helper method to withdraw all the money
    public void withdrawAll(String reference) {
        Transactions withdrawAll = new Transactions(reference, TYPE.WITHDRAWAL, -currentBalance);
        transactions.add(withdrawAll);
        decreaseBalance(currentBalance);
        System.out.println("The withdrawal all has completed.");
    }

    // helper method to make a deposit to account
    public void deposit(double amount, String reference) {
        if (checkAmount(amount) == true) {
            increaseBalance(amount);
            Transactions deposit = new Transactions(reference, TYPE.DEPOSIT, amount);
            transactions.add(deposit);
            System.out.println("The deposit has completed.");
        } else {
            throw new IllegalArgumentException("The amount should be positive.");
        }
    }

    // helper method to transfer money from this account to another account
    public void transfer(double amount, Accounts transferAccount, String reference) {
        if (amount <= currentBalance && checkAmount(amount) == true) {
            decreaseBalance(amount);
            transferAccount.increaseBalance(amount);
            Transactions payTransfer = new Transactions(reference, TYPE.TRANSFER, -amount);
            transactions.add(payTransfer);
            Transactions receiveTransfer = new Transactions(reference, TYPE.TRANSFER, amount);
            transferAccount.transactions.add(receiveTransfer);
            System.out.println("The transfer has completed.");
        } else {
            throw new IllegalArgumentException("The transfer amount can not be greater than the balance and the amount should be positive.");
        }
    }

    // helper method to transfer all the money to another account
    public void transferAll(Accounts transferAccount, String reference) {
        transferAccount.increaseBalance(currentBalance);
        decreaseBalance(currentBalance);
        Transactions payTransfer = new Transactions(reference, TYPE.TRANSFER, -currentBalance);
        transactions.add(payTransfer);
        Transactions receiveTransfer = new Transactions(reference, TYPE.TRANSFER, currentBalance);
        transferAccount.transactions.add(receiveTransfer);
        System.out.println("The transfer all has completed.");
    }

    //helper method to use card transaction
    public void card(double amount, String reference) {
        increaseBalance(amount);
        Transactions card = new Transactions(reference, TYPE.CARD, amount);
        transactions.add(card);
        System.out.println("The card transaction has completed.");
    }

    // to String
    @Override
    public String toString() {
        String c = ",";
        return idNumber + c + accountNum + c + currentBalance;
    }

    // override all the method in the interface - BalanceModification
    @Override
    public void increaseBalance(double amount) {
        currentBalance += amount;
    }

    @Override
    public void decreaseBalance(double amount) {
        currentBalance -= amount;
    }
}
