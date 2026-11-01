package in.sqrock.railway.model;

public class Booking {
    private final int id, trainNumber, seatNumber;
    private final String passenger, trainName, source, destination, journeyDate, status;
    public Booking(int id, int trainNumber, int seatNumber, String passenger, String trainName, String source, String destination, String journeyDate, String status) {
        this.id=id; this.trainNumber=trainNumber; this.seatNumber=seatNumber; this.passenger=passenger;
        this.trainName=trainName; this.source=source; this.destination=destination; this.journeyDate=journeyDate; this.status=status;
    }
    public int getId(){return id;} public String getPassenger(){return passenger;} public String getTrainName(){return trainName;}
    public int getTrainNumber(){return trainNumber;} public int getSeatNumber(){return seatNumber;}
    public String getSource(){return source;} public String getDestination(){return destination;}
    public String getJourneyDate(){return journeyDate;} public String getStatus(){return status;}
}
