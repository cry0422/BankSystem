package banksystem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import banksystem.Customers.ACCOUNT_TYPE;
import banksystem.Transactions.TYPE;

public class Controller {

    public static void main(String[] args) {
        // load Customers
        String filename = "resource/customers.csv";
        List<Customers> customersInfo = new ArrayList<>();
        customersInfo = FileUtils.loadCustomersFile(filename);

        // load savings accounts
        String filename1 = "resource/savings_accounts.csv";
        List<SavingsAccounts> saving = new ArrayList<>();
        double rate = 0.05;
        saving = FileUtils.loadSavingFile(filename1, rate);

        // load credit accounts
        String filename2 = "resource/credit_accounts.csv";
        List<CreditAccounts> credit = new ArrayList<>();
        credit = FileUtils.loadCreditFile(filename2);

        // load debit accounts
        String filename3 = "resource/debit_accounts.csv";
        List<DebitAccounts> debit = new ArrayList<>();
        debit = FileUtils.loadDebitFile(filename3);

        // load the Accounts into Customer
        for (Customers cus : customersInfo) {
            cus.loadAccount(credit, debit, saving);
        }

        // GUI
        JFrame frame = new JFrame("Bank Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.add(new BankTable(customersInfo));
        frame.setVisible(true);

        // test for opening account
//        int n = 1;
//        int m = 1;
//        Customers Swift = customersInfo.get(3);
//        List<Accounts> accounts = Swift.getAccounts();
//        System.out.println("Initial accounts: ");
//        for (Accounts account:accounts){
//            System.out.println(n + ": " + account.toString());
//            n += 1;
//        }
//        Swift.openAccount(ACCOUNT_TYPE.SAVINGS, 150);
//        List<Accounts> newAccounts = Swift.getAccounts();
//        System.out.println("\nNew accounts: ");
//        for (Accounts account:newAccounts){
//            System.out.println(m + ": " + account.toString());
//            m += 1;
//        }
        // test for deleting account
//        Customers Swift = customersInfo.get(3);
//        List<Accounts> accounts = Swift.getAccounts();
//        System.out.println("Initial accounts: ");
//        int n = 1;
//        int m = 1;
//        for (Accounts account:accounts){
//            System.out.println(n + ": " + account.toString());
//            n += 1;
//        }
//        Swift.deleteAccount("GB98MIDL07009312708430");
//        List<Accounts> newAccounts = Swift.getAccounts();
//        System.out.println("\nNew accounts: ");
//        for (Accounts account:newAccounts){
//            System.out.println(m + ": " + account.toString());
//            m += 1;
//        }
        // test for adding transactions
//        Customers customer1 = customersInfo.get(0);
//        Accounts account1 = customer1.getAccounts().get(0);
//        System.out.println("account1: " + account1.toString());
//        Accounts account2 = customer1.getAccounts().get(1);
//        System.out.println("account2: " + account2.toString());
//        // withdraw money
//        account1.withdrawal(100, "withdrawal");
//        System.out.println("withdrawal:\naccount1_withdrawal: " + account1.getCurrentBalance());
//        // make a deposit
//        account1.deposit(50,"deposit");
//        System.out.println("deposit:\naccount1_deposit:" + account1.getCurrentBalance());
//        // transfer money from account1 to account2
//        account1.transfer(100, account2,"transfer 100 yuan");
//        System.out.println("transfer:\naccount1_transfer: " + account1.getCurrentBalance());
//        System.out.println("account2_transfer: " + account2.getCurrentBalance());
//        // card transactions
//        account2.card(50, "card 50 yuan");
//        System.out.println("card:\naccount2_card: " + account2.getCurrentBalance());
//        // check transactions record
//        System.out.println("transaction record:\naccount1's transaction: " + account1.checkTransactionsRecord());
//        System.out.println("account2's transaction: " + account2.checkTransactionsRecord());
//        String c = ",";
//        String line = "10010001" + c + "GB98MIDL07009320123490" + c + "100" + c + "0.05";
//        SavingsAccounts savingsAccount1 = new SavingsAccounts(line);
//        savingsAccount1.card(20, "card 20 yuan");
        // test for updating file
//        List<List> accountList = FileUtils.cateforizeAccounts(customersInfo);
//        List<CreditAccounts> newCredit = accountList.get(0);
//        List<DebitAccounts> newDebit = accountList.get(1);
//        List<SavingsAccounts> newSavings = accountList.get(2);
//        String filename4 = "resource/transactions.csv";
//        FileUtils.updateCustomerFile(filename, customersInfo);
//        FileUtils.updateCreditFile(filename2, newCredit);
//        FileUtils.updateDebitFile(filename3, newDebit);
//        FileUtils.updateSavingsFile(filename1, newSavings);
//        FileUtils.writeTransactionsRecord(filename4, customersInfo);
    }

}
