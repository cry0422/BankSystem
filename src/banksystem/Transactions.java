package banksystem;

public class Transactions {

    //enum data type for type attribute
    public enum TYPE {
        WITHDRAWAL, DEPOSIT, TRANSFER, CARD
    }

    // fields
    private String reference;
    private TYPE type;
    private double amount;

    // constructor
    public Transactions(String reference, TYPE type, double amount) {
        this.reference = reference;
        this.type = type;
        this.amount = validateAmount(amount);
    }

    // check if the amount is valid
    private double validateAmount(double amount) {
        switch (type) {
            case WITHDRAWAL:
                // the amount of withdrawal should be negative
                if (amount >= 0) {
                    throw new IllegalArgumentException("The amount of a withdrawal must "
                            + "be negative.");
                }
                break;
            case DEPOSIT:
                // the amount of deposit should be positive
                if (amount <= 0) {
                    throw new IllegalArgumentException("The amount of a deposit must be"
                            + " positive.");
                }
                break;
            case TRANSFER:
                // the amount of transfer should not be zero.
                if (amount == 0) {
                    throw new IllegalArgumentException("The amount should be a signed numeric value");
                }
                break;
            case CARD:
                // the amount of card should not be zero.
                if (amount == 0) {
                    throw new IllegalArgumentException("The amount should be a signed numeric value");
                }
                break;
        }
        return amount;
    }

    // getters
    public String getReference() {
        return reference;
    }

    public TYPE getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    // to String
    @Override
    public String toString() {
        String c = ",";
        return reference + c + type + c + amount;
    }
}
