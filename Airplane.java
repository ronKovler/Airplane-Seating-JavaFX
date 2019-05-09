import java.util.ArrayList;
/**
 * Write a description of class Airplane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Airplane
{
    public int numSeatsPerRow, numRows, numRowsBusiness, numRowsFirst;
    private double avgTicketPrice;
    private String[] rowLetter = {null, "A", "B", "C", "D", "E", "F", "G", "H"};
    public ArrayList<AirplaneSeat> seatList;
    public AirplaneSeat[][] seatListArray;
    
    //pre condition -- cols is even
    public Airplane(int cols, int rowsTotal, int first, int business, double ticketPrice){
        numSeatsPerRow = cols;
        numRows = rowsTotal;
        numRowsBusiness = business;
        numRowsFirst = first;
        avgTicketPrice = ticketPrice;
        seatList = new ArrayList<AirplaneSeat>(cols * rowsTotal);
        seatListArray = new AirplaneSeat[rowsTotal][cols];
        fillAirplane();
    }
    
    
    
    private void fillAirplane(){
        int count = 1;
        for(int r = 1; r <= numRows; r++){
            for(int c = 1; c <= numSeatsPerRow; c++){
                
                if(r <= numRowsFirst){
                    boolean reserve = Math.random() < 0.2;
                    AirplaneSeat firstSeat = new AirplaneSeat(reserve, 3, avgTicketPrice, r + rowLetter[c]);
                    seatList.add(firstSeat);
                    seatListArray[r-1][c-1] = firstSeat;
                }else if(r > numRowsFirst && r <= numRowsBusiness + numRowsFirst){
                    boolean reserve = Math.random() < 0.4;
                    AirplaneSeat businessSeat = new AirplaneSeat(reserve, 2, avgTicketPrice, r + rowLetter[c]);
                    seatList.add(businessSeat);
                    seatListArray[r-1][c-1] = businessSeat;
                }else{
                    boolean reserve = Math.random() < 0.5;
                    AirplaneSeat economySeat = new AirplaneSeat(reserve, 1, avgTicketPrice, r + rowLetter[c]);
                    seatList.add(economySeat);
                    seatListArray[r-1][c-1] = economySeat;
                }
            }
        }
    }
    
    public static void main(String[] args){
        Airplane test = new Airplane(4, 20, 3, 4, 200);
        for(AirplaneSeat s: test.seatList){
            System.out.println(AirplaneSeat.isReserved(s));
        }
        
        for(int r = 0; r < test.numRows; r++){
            for(int c = 0; c< test.numSeatsPerRow; c++){
                System.out.print(AirplaneSeat.getName(test.seatListArray[r][c]) + " ");
                if(c == test.numSeatsPerRow -1){
                    System.out.println("");
                }
            }
        }
    }
}
