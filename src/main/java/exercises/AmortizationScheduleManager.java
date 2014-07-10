package exercises;

import java.util.ArrayList;

public class AmortizationScheduleManager {
    private long _amountInCents; 
    private double _apr;
    private int _termInMonth;
    private final double _monthlyInterestDivisor = 12d * 100d;
    private double _monthlyInterestRate = 0d;
    private final long _monthlyPayment;
    private static final String FORMAT_STRING="%1$-20s%2$-20s%3$-20s%4$-15s%5$-15s%6$-15s\n";
    
    public AmortizationScheduleManager(Amount amount, APR apr, Term term) {
        this._amountInCents= Math.round(amount.getValue()*100);
        this._apr=apr.getValue();
        this._termInMonth=term.getTerms()*12;
        _monthlyPayment = calculateMonthlyPayment();
    }
    
    
    /**
     * M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
     * Where:
     * P = Principal
     * I = Interest
     * J = Monthly Interest in decimal form:  I / (12 * 100)
     * N = Number of months of loan
     * M = Monthly Payment Amount 
     * @return
     */
    protected long calculateMonthlyPayment(){
        // calculate J
        _monthlyInterestRate = _apr / _monthlyInterestDivisor;
        
        // this is 1 / (1 + J)
        double tmp = Math.pow(1d + _monthlyInterestRate, -1);
        
        // this is Math.pow(1/(1 + J), N)
        tmp = Math.pow(tmp, _termInMonth);
        
        // this is 1 / (1 - (Math.pow(1/(1 + J), N))))
        tmp = Math.pow(1d - tmp, -1);
        
        // M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
        double rc = _amountInCents * _monthlyInterestRate * tmp;
        
        return Math.round(rc);
    }
    
    public void populateSchedule(){
        
        int maxPaymentNum=this._termInMonth+1;
        int paymentNumber = 0;
        long totalPayments=0, totalInterestPaid=0;
        
        long balance = this._amountInCents;
        
        ConsoleManager.printf(FORMAT_STRING,
                "PaymentNumber", "PaymentAmount", "PaymentInterest",
                "CurrentBalance", "TotalPayments", "TotalInterestPaid");
        
        ConsoleManager.printf(FORMAT_STRING, paymentNumber++, 0d,0d,
                ((double) this._amountInCents) / 100d,
                ((double) totalPayments) / 100d,
                ((double) totalInterestPaid) / 100d, "\n");
        
        while ((balance > 0) && (paymentNumber <= maxPaymentNum)) {
            // Calculate H = P x J, this is your current monthly interest
            long curMonthlyInterest = Math.round(((double) balance) * this._monthlyInterestRate);

            // the amount required to payoff the loan
            long curPayoffAmount = balance + curMonthlyInterest;
            
            // the amount to payoff the remaining balance may be less than the calculated monthlyPaymentAmount
            long curMonthlyPaymentAmount = Math.min(this._monthlyPayment, curPayoffAmount);
            
            // it's possible that the calculated monthlyPaymentAmount is 0,
            // or the monthly payment only covers the interest payment - i.e. no principal
            // so the last payment needs to payoff the loan
            if ((paymentNumber == maxPaymentNum) &&
                    ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
                curMonthlyPaymentAmount = curPayoffAmount;
            }
            
            // Calculate C = M - H, this is your monthly payment minus your monthly interest,
            // so it is the amount of principal you pay for that month
            long curMonthlyPrincipalPaid = curMonthlyPaymentAmount - curMonthlyInterest;
            
            // Calculate Q = P - C, this is the new balance of your principal of your loan.
            long curBalance = balance - curMonthlyPrincipalPaid;
            
            totalPayments += curMonthlyPaymentAmount;
            totalInterestPaid += curMonthlyInterest;
            
            // output is in dollars
            ConsoleManager.printf(FORMAT_STRING, paymentNumber++,
                    ((double) curMonthlyPaymentAmount) / 100d,
                    ((double) curMonthlyInterest) / 100d,
                    ((double) curBalance) / 100d,
                    ((double) totalPayments) / 100d,
                    ((double) totalInterestPaid) / 100d, "\n");
                        
            // Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
            balance = curBalance;
        }
    }

}
