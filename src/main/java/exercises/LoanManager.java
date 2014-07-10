package exercises;

public class LoanManager {
    public static void main(String[] args){
        Amount totalBorrowed=null;
        APR fixApr=null;
        Term termOfLoan=null;
        try{
            totalBorrowed = Amount.promptForUserInput();
            fixApr = APR.promptForUserInput();
            termOfLoan = Term.promptUserForInput();
        }catch(Exception e){
            ConsoleManager.print("Failed to get all required info. Abording ");
            return;
        }
        
        AmortizationScheduleManager asm = new AmortizationScheduleManager(totalBorrowed, fixApr, termOfLoan);
        asm.populateSchedule();
    }

}
