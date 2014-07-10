package exercises;

import java.io.IOException;

public class Amount {
    private static double MIN_AMOUNT = 0.01d, MAX_AMOUNT=1000000000000d;
    private static String PROMPT = "Please enter the amount you would like to borrow: ";
    private double _amount;
    
    public static Amount promptForUserInput() throws IOException{
        int retry = 5;
        String input = "";
        while (retry > 0){
            try{
                input = ConsoleManager.promptForUserInput(PROMPT);
                return new Amount(input);
            }catch(NumberFormatException e){
                ConsoleManager.print(input + " is not a valid number format\n");
            }catch(Exception e){
                ConsoleManager.print(e.getMessage()+"\n");
            }
            ConsoleManager.print("Please re-enter\n");
            retry--;
        }
        throw new IOException("Reached maximum tries to obtain amount");
    }
    
    public Amount(double proposedAmount) throws InvalidValueException{
        if(proposedAmount < MIN_AMOUNT||proposedAmount > MAX_AMOUNT){
            String msg = String.format("%f  is not a valid "
                    + "amount. Please use a value between %.2f and %.0f",
                    proposedAmount, MIN_AMOUNT, MAX_AMOUNT);
            throw new InvalidValueException(msg);
        }
        this._amount=proposedAmount;
    }
    
    public Amount(String amountString) 
            throws InvalidValueException, NumberFormatException{
        this(Double.parseDouble(amountString));
    }
    
    public double getValue(){
        return _amount;
    }
}
