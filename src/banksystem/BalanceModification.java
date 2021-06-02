package banksystem;

// an interface to modify the balance of account
public interface BalanceModification {
    public void increaseBalance(double amount);
    public void decreaseBalance(double amount);
}
