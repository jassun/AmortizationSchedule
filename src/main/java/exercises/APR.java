package exercises;

import java.io.IOException;

public class APR {
    private static double MIN_APR = 0.000001d, MAX_APR=100d;
    private static String PROMPT = "Please enter the annual percentage rate used to repay the loan: ";
    private double _apr;
    
    public static APR promptForUserInput() throws IOException{
        int retry = 5;
        String input = "";
        while (retry > 0){
            try{
                input = ConsoleManager.promptForUserInput(PROMPT);
                return new APR(input);
            }catch(NumberFormatException e){
                ConsoleManager.print(input + " is not a valid number format\n");
            }catch(Exception e){
                ConsoleManager.print(e.getMessage()+"\n");
            }
            ConsoleManager.print("Please re-enter\n");
            retry--;
        }
        throw new IOException("Reached maximum tries to obtain APR");
    }
    
    public APR(double proposedAPR) throws InvalidValueException {
        if(proposedAPR<MIN_APR||proposedAPR>MAX_APR){
            String msg = String.format("%s  is not a valid "
                    + "amount. Please use a value between %.6f and %.0f",
                    proposedAPR, MIN_APR, MAX_APR);
            throw new InvalidValueException(msg);
        }
        this._apr=proposedAPR;
    }
    
    public APR(String APRString) throws Exception{
        this(Double.parseDouble(APRString));
    }
    
    public double getValue(){
        return _apr;
    }

}
