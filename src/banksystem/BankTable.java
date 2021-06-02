package banksystem;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BankTable extends JPanel{
    // fields 
    private JPanel JP1,JP2;
    private JComboBox customerList,accountList;
    private JLabel cusInfo ,accInfo, label1, label2;
    private List<Customers> customers;
    private List<Accounts> accounts;
    
    // constructor
    public BankTable(List<Customers> customers){
        this.JP1 = new JPanel();
        this.JP2 = new JPanel();
        this.customers = customers;
        this.accounts = new ArrayList<>();
        this.customerList = new JComboBox<String>();
        this.accountList = new JComboBox<String>();
        this.cusInfo = new JLabel();
        this.accInfo = new JLabel();
        this.label1 = new JLabel("Customers: ");
        label1.setFont(new Font("Dialog", 1, 15));
        this.label2 = new JLabel("Accounts: ");
        label2.setFont(new Font("Dialog", 1, 15));
        // add options for customerList menu
        for (Customers cus:customers) {
            String option = cus.getName() + "," + cus.getIdNumber();
            customerList.addItem(option);
        }
        
        JP1.add(label1);
        JP1.add(customerList);
        JP1.add(cusInfo);
        JP2.add(label2);
        JP2.add(accountList);
        JP2.add(accInfo);
        
        this.setLayout(new BorderLayout());
        this.add(JP1, BorderLayout.NORTH);
        this.add(JP2, BorderLayout.SOUTH);
        
        // add action listener for customerList and accountList menu
        customerList.addActionListener(new MenuListener());
        accountList.addActionListener(new MenuListener1());
    }
    
    // helper method to find full information of selected customer and return information
    private String getCustomerInfo(String selectCus){
        String customerInfo = "";
        String[] scusomer = selectCus.split(",");
        String name = scusomer[0];
        int idNumber = Integer.parseInt(scusomer[1]);
        for (Customers cus:customers){
            // check the name and id number
            if (cus.getName().equals(name) && cus.getIdNumber() == idNumber){
                customerInfo = cus.toString();
            }
        }
        return customerInfo;
    }
    
    // helper method to get account list of the selected customer
    private void getAccount(String id){
        for (Customers cus : customers) {
            // check the id number
            if (cus.getIdNumber() == Integer.parseInt(id)) {
                accounts = cus.getAccounts();
            }
        }
    }
    
    // helper method to get account options of selected customer and return account options
    private List<String> getAccountOptions(String id){
        String accountType = "";
        String digits = "";
        getAccount(id);
        List<String> accountOptions = new ArrayList<>();
        for (Accounts account : accounts) {
            // get the last four digits of account number
            String IBAN = account.getAccountNum();
            digits = IBAN.substring(IBAN.length() - 4);
            // get the type of the account
            if (account instanceof CreditAccounts) {
                accountType = "Credit";
            } else if (account instanceof DebitAccounts) {
                accountType = "Debit";
            } else if (account instanceof SavingsAccounts) {
                accountType = "Savings";
            }
            accountOptions.add(accountType + " xxx-" + digits);
        }
        return accountOptions;
    }
    
    // helper method to get full information of selected account and return account information
    private String getAccountInfo (String selectAccount) {
        String accountInfo = "";
        String[] selected = selectAccount.split(" ");
        String saccountType = selected[0] + "Accounts";
        String saccountNum = selectAccount.substring(selectAccount.length()-4);
        for (Accounts acc:accounts){
            // check the last four digits of account number and account type
            String accountNum = acc.getAccountNum().substring(acc.getAccountNum().length()-4);
            String accountType = (String)acc.getClass().getSimpleName();
            if (saccountNum.equals(accountNum) && saccountType.equals(accountType)){
                accountInfo = acc.toString();
            }
        }
        return accountInfo;
    }
    
    //helper method to creat new ActionListener for customerList menu
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox menu1 = (JComboBox) e.getSource();
            accountList.removeAllItems();
            String selectCus= menu1.getSelectedItem().toString();
            String[] customerInfo = getCustomerInfo(selectCus).split(",");
            // output as a row
            cusInfo.setText("<html><body>" + "Name: " + customerInfo[0] + "<br>" 
                    + "Id Number: " + customerInfo[1] + "<br>" + "Date Of Birth: " 
                    + customerInfo[2] + "<br>" + "Number Of Accounts: " 
                    + customerInfo[3] + "<body></html>");
            // add items in the account list
            List<String> accounts = getAccountOptions(customerInfo[1]);
            for (String acc:accounts){
                accountList.addItem(acc);
            }
        }
    }
    
    //helper method to creat new ActionListener for accountList menu
    private class MenuListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox menu2 = (JComboBox) e.getSource();
            if (menu2.getSelectedItem() == null) {
            } 
            else {
                String selectAccount = menu2.getSelectedItem().toString();
                String[] accountInfo = getAccountInfo(selectAccount).split(",");
                // Savings account has rate, while debit and credit has card.
                if (accountInfo[3].length() == 16) {
                    accInfo.setText("<html><body>" + "Account Number: " + accountInfo[1] + "<br>"
                            + "Balance: " + accountInfo[2] + "<br>" + "Card: "
                            + accountInfo[3] + "<body></html>");
                }
                else {
                    accInfo.setText("<html><body>" + "Account Number: " + accountInfo[1] + "<br>"
                            + "Balance: " + accountInfo[2] + "<br>" + "Rate: "
                            + accountInfo[3] + "<body></html>");
                }
            }
        }
    }
}
