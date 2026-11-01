package in.sqrock.railway.model;

public class Train {
    private final int id, number, availableSeats;
    private final String name, source, destination, departure, arrival;
    public Train(int id, int number, String name, String source, String destination, String departure, String arrival, int availableSeats) {
        this.id=id; this.number=number; this.name=name; this.source=source; this.destination=destination;
        this.departure=departure; this.arrival=arrival; this.availableSeats=availableSeats;
    }
    public int getId(){return id;} public int getNumber(){return number;} public String getName(){return name;}
    public String getSource(){return source;} public String getDestination(){return destination;}
    public String getDeparture(){return departure;} public String getArrival(){return arrival;}
    public int getAvailableSeats(){return availableSeats;}
    @Override public String toString(){return number+" - "+name+" | "+source+" to "+destination+" | "+departure+" | Seats: "+availableSeats;}
}
