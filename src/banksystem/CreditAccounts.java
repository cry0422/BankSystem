package banksystem;

public class CreditAccounts extends Accounts{
    // fields
    private long card;
    
    //constructor
    public CreditAccounts(String line){
        super (line);
        String[] creditInfo = line.split(",");
        this.card = validateCard(Long.parseLong(creditInfo[3]));
    }
    
    // check if card is a 16-digit number
    private long validateCard(long card) {
        String sCard = card + "";
        if (sCard.length() == 16) {
            for (int i = 0; i < sCard.length(); i++) {
                if (!Character.isDigit(sCard.charAt(i))) {
                    throw new IllegalArgumentException("The character in card should be whole number.");
                }
            }
        } else {
            throw new IllegalArgumentException("Card should be a 16-digit number.");
        }
        return card;
    }
    
    // getters
    public long getCard(){
        return card;
    }
    
    // to String
    @Override
    public String toString() {
        String c = ",";
        return super.toString() + c + card;
    }
}
