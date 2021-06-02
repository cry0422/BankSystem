package banksystem;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // read the file customers
    public static List<Customers> loadCustomersFile(String filename) {
        // creat an empty array list and get the file
        List<Customers> information = new ArrayList<>();
        Path path = Paths.get(filename);
        // read each line in the file
        try ( BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    information.add(new Customers(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading Customers' information from file.");
        }
        return information;
    }

    // read the file saving_accounts
    public static List<SavingsAccounts> loadSavingFile(String filename, double rate) {
        // creat an empty array list and get the file
        List<SavingsAccounts> saving = new ArrayList<>();
        Path path = Paths.get(filename);
        // read each line in the file
        try ( BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    // add a initial interest rate for saving account
                    line = line + "," + rate;
                    saving.add(new SavingsAccounts(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading saving accounts from file.");
        }
        return saving;
    }

    // read the file credit_accounts
    public static List<CreditAccounts> loadCreditFile(String filename) {
        // creat an empty array list and get the file
        List<CreditAccounts> credit = new ArrayList<>();
        Path path = Paths.get(filename);
        // read each line in the file
        try ( BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    credit.add(new CreditAccounts(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading credit accounts from file.");
        }
        return credit;
    }

    // read the file debit_accounts
    public static List<DebitAccounts> loadDebitFile(String filename) {
        // creat an empty array list and get the file
        List<DebitAccounts> debit = new ArrayList<>();
        Path path = Paths.get(filename);
        // read each line in the file
        try ( BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    debit.add(new DebitAccounts(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading debit accounts from file.");
        }
        return debit;
    }

    // update the file customer
    public static void updateCustomerFile(String destinationFile, List<Customers> toWrite) {
        Path path = Paths.get(destinationFile);
        try ( BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Customers customer : toWrite) {
                writer.write(customer.toString());
                writer.newLine();
            }
            System.out.println("The file customer written.");
        } catch (IOException e) {
            System.out.println("Errors in writing file customer.");
        }
    }

    // update the file credit_accounts
    public static void updateCreditFile(String destinationFile, List<CreditAccounts> toWrite) {
        Path path = Paths.get(destinationFile);
        try ( BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (CreditAccounts credit : toWrite) {
                writer.write(credit.toString());
                writer.newLine();
            }
            System.out.println("The file credit_accounts written.");
        } catch (IOException e) {
            System.out.println("Errors in writing file credit_accounts.");
        }
    }

    // update the file debit_accounts
    public static void updateDebitFile(String destinationFile, List<DebitAccounts> toWrite) {
        Path path = Paths.get(destinationFile);
        try ( BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (DebitAccounts debit : toWrite) {
                writer.write(debit.toString());
                writer.newLine();
            }
            System.out.println("The file debit_accounts written.");
        } catch (IOException e) {
            System.out.println("Errors in writing file debit_accounts.");
        }
    }

    // update the file customer
    public static void updateSavingsFile(String destinationFile, List<SavingsAccounts> toWrite) {
        Path path = Paths.get(destinationFile);
        try ( BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (SavingsAccounts savings : toWrite) {
                writer.write(savings.toString());
                writer.newLine();
            }
            System.out.println("The file savings_accounts written.");
        } catch (IOException e) {
            System.out.println("Errors in writing file savings_accounts.");
        }
    }
    
    // write the file transactions
    public static void writeTransactionsRecord(String destinationFile, List<Customers> customers){
        Path path = Paths.get(destinationFile);
        try ( BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Customers customer : customers) {
                List<Accounts> accounts = customer.getAccounts();
                for (Accounts account : accounts) {
                    List<Transactions> transactions = account.checkTransactionsRecord();
                    for (Transactions transaction : transactions) {
                        writer.write(account.getAccountNum() + "," + transaction.toString());
                        writer.newLine();
                    }
                }
            }
            System.out.println("The file transactions written.");
        } catch (IOException e) {
            System.out.println("Errors in writing file transactions.");
        } 
    }
    
    // Categorize accounts by type
    public static List<List> cateforizeAccounts(List<Customers> customers){
        List<List> accountList = new ArrayList<>();
        List<CreditAccounts> credit = new ArrayList<>();
        List<DebitAccounts> debit = new ArrayList<>();
        List<SavingsAccounts> savings = new ArrayList<>();
        for (Customers customer:customers){
            List<Accounts> accounts = customer.getAccounts();
            for (Accounts account:accounts){
                if (account instanceof CreditAccounts){
                    credit.add((CreditAccounts)account);
                }
                else if (account instanceof DebitAccounts){
                    debit.add((DebitAccounts)account);
                }
                else if (account instanceof SavingsAccounts){
                    savings.add((SavingsAccounts)account);
                }
            }
        }
        accountList.add(credit);
        accountList.add(debit);
        accountList.add(savings);
        return accountList;
    }
}
