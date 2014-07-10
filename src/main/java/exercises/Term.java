package exercises;

import java.io.IOException;

public class Term {
    private static int MIN_TERM=1, MAX_TERM=1000000;
    private static String PROMPT = "Please enter the term, in years, over which the loan is repaid: ";
    private int _terms;
    
    public static Term promptUserForInput() throws IOException{
        int retry = 5;
        String input = "";
        while (retry > 0){
            try{
                input = ConsoleManager.promptForUserInput(PROMPT);
                return new Term(input);
            }catch(NumberFormatException e){
                ConsoleManager.print(input + " is not a valid number format\n");
            }catch(Exception e){
                ConsoleManager.print(e.getMessage()+"\n");
            }
            ConsoleManager.print("Please re-enter\n");
            retry--;
        }
        throw new IOException("Reached maximum tries to obtain Term");
    }
    
    public Term(int proposedTerm) throws InvalidValueException{
        if(proposedTerm < MIN_TERM || proposedTerm > MAX_TERM){
            String msg = String.format("%s  is not a valid "
                    + "amount. Please use a value between %s and %s",
                    proposedTerm, MIN_TERM, MAX_TERM);
            throw new InvalidValueException(proposedTerm+" is not a valid term"
                    + ". A valid term is an integer between "+MIN_TERM+" and "
                    +MAX_TERM);
        }
        this._terms = proposedTerm;
    }
    
    public Term(String termString) throws Exception{
        this(Integer.parseInt(termString));
    }
    
    public int getTerms(){
        return _terms;
    }

}
