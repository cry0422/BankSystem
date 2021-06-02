package banksystem;

public class SavingsAccounts extends Accounts {

    // fields 
    private double rate;

    //constructor
    public SavingsAccounts(String line) {
        super(line);
        String[] savingInfo = line.split(",");
        this.rate = Double.parseDouble(savingInfo[3]);
    }

    // getter
    public double getRate() {
        return rate;
    }

    // to String
    @Override
    public String toString() {
        String c = ",";
        return super.toString() + c + rate;
    }

    /*overriding method to ensure that savings accounts don't support card transactions 
    and withdrawal, and it cannot be the payee in a transfer transaction.
     */
    @Override
    public void withdrawal(double amount, String reference) {
        throw new IllegalArgumentException("Savings account don't support withdrawals!");
    }

    @Override
    public void withdrawAll(String reference) {
        throw new IllegalArgumentException("Savings account don't support withdrawals!");
    }

    @Override
    public void transfer(double amount, Accounts transferAccount, String reference) {
        throw new IllegalArgumentException("A savings account cannot be the payer in a transfer transaction.");
    }

    @Override
    public void card(double amount, String reference) {
        throw new IllegalArgumentException("Savings account don't support card transactions!");
    }
}
