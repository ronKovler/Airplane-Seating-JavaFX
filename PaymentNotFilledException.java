
/**
 * Write a description of class PaymentNotFilledException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PaymentNotFilledException extends Exception
{
    public PaymentNotFilledException(String msg){
        super(msg);
    }
    
    public PaymentNotFilledException(){
        super();
    }
}
