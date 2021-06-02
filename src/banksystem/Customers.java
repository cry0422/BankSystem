package banksystem;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.RandomStringUtils;

public class Customers implements RandomCharacters {

    // sanner for delete account menu
    static Scanner keyboard;

    //enum data type for account type attribute
    public enum ACCOUNT_TYPE {
        CREDIT, DEBIT, SAVINGS
    };

    // fields
    private String name;
    private int idNumber;
    private final LocalDate dateOfBirth;
    private List<Accounts> accounts;

    //constructor
    public Customers(String line) {
        String[] customerInfo = line.split(",");
        this.name = validateName(customerInfo[0]);
        this.idNumber = validateIdNumber(Integer.parseInt(customerInfo[1]));
        this.dateOfBirth = LocalDate.parse(customerInfo[2]);
        this.accounts = new ArrayList<>();
    }

    // check if name is valid
    private String validateName(String name) {
        String[] names = name.split(" "); //first name, (middle name(s),) last name
        if (names.length < 2) { //a Person should have at least a first name and a last name
            throw new IllegalArgumentException("a Person should have at least a first name and a last name");
        }
        for (String n : names) {
            if (!isAllLetters(n)) { //names should be made up of only letters
                throw new IllegalArgumentException("name can only contain letters");
            }
        }
        return name;
    }

    //check if a String is composed solely of letters
    private boolean isAllLetters(String toCheck) {
        for (int i = 0; i < toCheck.length(); i++) {
            if (!Character.isLetter(toCheck.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // check if idNumber is valid
    private int validateIdNumber(int idNumber) {
        if (idNumber < 0) {
            throw new IllegalArgumentException("ID number cannot be negative");
        }
        return idNumber;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public int getNumOfAccounts() {
        int numOfAccounts = accounts.size();
        return numOfAccounts;
    }

    //heper method to load accounts for the customer
    public void loadAccount(List<CreditAccounts> credits, List<DebitAccounts> debits,
            List<SavingsAccounts> savings) {
        for (CreditAccounts credit : credits) {
            if (credit.getIdNumber() == idNumber) {
                accounts.add(credit);
            }
        }
        for (DebitAccounts debit : debits) {
            if (debit.getIdNumber() == idNumber) {
                accounts.add(debit);
            }
        }
        for (SavingsAccounts saving : savings) {
            if (saving.getIdNumber() == idNumber) {
                accounts.add(saving);
            }
        }
    }

    // method to calculate customer's age
    private int getAge() {
        LocalDate today = LocalDate.now();
        Period age = Period.between(dateOfBirth, today);
        return age.getYears();
    }

    // method to check whether the customer is an adult
    private boolean isAdult() {
        int age = getAge();
        if (age >= 18) {
            return true;
        } else {
            return false;
        }
    }

    // method to randomly generate new account number.
    private String generateAccountNumber() {
        // generate country code
        String countryCode = randomLetters(2).toUpperCase();
        // generate check code
        Long checkCode = randomNumbers(2);
        // generate alphanumeric characters
        String alphanumericChar = randomLetters(4).toUpperCase()
                + RandomStringUtils.randomNumeric(14, 31);
        String accountNum = countryCode + checkCode + alphanumericChar;
        return accountNum;
    }

    // method to generate card number
    private long generateCardNumber() {
        return randomNumbers(16);
    }

    // helper method to open account
    public void openAccount(ACCOUNT_TYPE accountType, double initialBalance) {
        String c = ",";
        String account = idNumber + c + generateAccountNumber() + c + initialBalance + c;
        // check if the number of accounts is more than 5.
        if (accounts.size() < 5) {
            switch (accountType) {
                case CREDIT:
                    if (isAdult()) {
                        String credit = account + generateCardNumber();
                        CreditAccounts newCredit = new CreditAccounts(credit);
                        if (newCredit.checkBalance()) {
                            accounts.add(newCredit);
                            System.out.println("The new credit account has been opened.");
                        } else {
                            throw new IllegalArgumentException("Account can only be "
                                    + "opened with a non-negative initial balance");
                        }
                    } else {
                        throw new IllegalArgumentException("Only an adult can open credit account.");
                    }
                    break;
                case DEBIT:
                    String debit = account + generateCardNumber();
                    DebitAccounts newDebit = new DebitAccounts(debit);
                    if (newDebit.checkBalance()) {
                        accounts.add(newDebit);
                        System.out.println("The new debit account has been opened.");
                    } else {
                        throw new IllegalArgumentException("Account can only be "
                                + "opened with a non-negative initial balance");
                    }
                    break;
                case SAVINGS:
                    if (isAdult() == false) {
                        throw new IllegalArgumentException("Only an adult can open savings account.");
                    }
                    int numOfCredit = 0;
                    for (Accounts acc : accounts) {
                        if (acc instanceof CreditAccounts) {
                            numOfCredit++;
                        }
                    }
                    if (numOfCredit >= 1) {
                        String rate = "0.05";
                        String savings = account + rate;
                        SavingsAccounts newSaving = new SavingsAccounts(savings);
                        if (newSaving.checkBalance()) {
                            accounts.add(newSaving);
                            System.out.println("The new savings account has been opened.");
                        } else {
                            throw new IllegalArgumentException("Account can only be "
                                    + "opened with a non-negative initial balance");
                        }
                    } else {
                        throw new IllegalArgumentException("Customer can only open a savings account if they already have at least one credit account.");
                    }
                    break;
            }

        } else {
            throw new IllegalArgumentException("One person can only have up to five accounts.");
        }
    }

    // method to show the delete account menu
    private int showDeleteMenu() {
        keyboard = new Scanner(System.in);
        String selectedNumber = keyboard.nextLine();
        int number;
        try {
            number = Integer.parseInt(selectedNumber);
            if (number == 1 || number == 2) {
            } else {
                System.out.println("Error, please input a number of 1 or 2.");
                return showDeleteMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error, please input a decimal number.");
            return showDeleteMenu();
        }
        return number;
    }

    // method to get transfer account number and return the transfer account number
    private String showAccountNum() {
        keyboard = new Scanner(System.in);
        String accountNum = keyboard.nextLine();
        return accountNum;
    }

    // helper method to delete an account
    public void deleteAccount(String accountNum) {
        Iterator<Accounts> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Accounts account = iterator.next();
            String acc = account.getAccountNum();
            Double bala = account.getCurrentBalance();
            if (acc.equals(accountNum)) {
                if (bala >= 0) {
                    System.out.println("Delete Account: Here are two choices you can choose:"
                            + "\n1.Withdraw all the money" + "\n2.Transfer all the money"
                            + "\nPlease input your choice: ");
                    int choice = showDeleteMenu();
                    switch (choice) {
                        case 1:
                            account.withdrawAll("withdraw all money");
                            iterator.remove();
                            System.out.println("The account has been deleted.");
                            break;
                        case 2:
                            System.out.println("Please input the tranfer account number:");
                            String transferAccountNum = showAccountNum();
                            int mark = 0;
                            for (Accounts accList : accounts) {
                                if (accList.getAccountNum().equals(transferAccountNum)) {
                                    account.transferAll(accList, "transfer all the money");
                                    mark += 1;
                                }
                            }
                            if (mark == 0) {
                                throw new IllegalArgumentException("The transfer account does not exist.");
                            } else if (mark == 1) {
                                iterator.remove();
                                System.out.println("The account has been deleted.");
                            }
                            break;
                    }
                } else {
                    throw new IllegalArgumentException("You cannot delete an account with a negative balance.");
                }
            }
        }
    }

    // helper method to delete all accounts
    public void deleteAllAccounts() {
        int mark = 0;
        for (Accounts acc : accounts) {
            if (acc.getCurrentBalance() > 0) {
//                acc.withdrawAll("withdraw all the money");
            } 
            // check if the acount has a non-negative balance
            else if (acc.getCurrentBalance() < 0) {
                mark += 1;
            }
        }
        if (mark > 0) {
            throw new IllegalArgumentException("You cannot delete an account with a negative balance.");
        } else {
            accounts.clear();
            System.out.println("All accounts have been deleted.");
        }
    }

    // to String
    @Override
    public String toString() {
        String c = ",";
        return name + c + idNumber + c + dateOfBirth + c + getNumOfAccounts();
    }
    
    // override all method in the interface - RandomCharacters
    @Override
    public String randomLetters(int number) {
        return RandomStringUtils.randomAlphabetic(number);
    }
    
    @Override
    public long randomNumbers(int number) {
        long Number = Long.parseLong(RandomStringUtils.randomNumeric(number));
        long finalNum = 0;
        // check if the lenth of the number is equal to number.
        // this is because the method randomNumeric may have some problems when I was testing.
        if ((Number + "").length() == number) {
            finalNum = Number;
        } else {
            return randomNumbers(number);
        }
        return finalNum;
    }
}
