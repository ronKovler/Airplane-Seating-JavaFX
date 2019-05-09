
/**
 * Write a description of class AirplaneSeat here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirplaneSeat// Airplane HAS-A(some) airplaneSeats
{
    private boolean reserved;
    private int category;//1== economy, 2 == business, 3 == first
    private double price;
    private final double businessMultiplier = 1.4;
    private final double firstMultiplier = 1.7;
    private String seatName;
    //precondition - type is in range (1, 3) inclusive, price is posotive
    public AirplaneSeat(boolean res, int type, double cost, String name){
        reserved = res;
        category = type;
        price = cost;
        seatName = name;
        if(type == 2){
            price *= businessMultiplier;
        }else if(type == 3){
            price *= firstMultiplier;
        }
    }
    
    
    //accessor for reserved
    public static boolean isReserved(AirplaneSeat seat){
        return seat.reserved;
    }
    //accessor for seat price
    public static double getPrice(AirplaneSeat seat){
        return seat.price;
    }
    //accessor for seat name
    public static String getName(AirplaneSeat seat){
        return seat.seatName;
    }
    //accessor for seat category as int value
    public static int getCategoryInt(AirplaneSeat seat){
        return seat.category;
    }
    //accessor for seat category as String representation
    public static String getCategory(AirplaneSeat seat){
        if(seat.category == 1){
            return "Economy Class";
        }else if(seat.category == 2){
            return "Business Class";
        }else {
            return "First Class";
        }
    }
    
    
}
