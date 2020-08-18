import java.util.LinkedList;

public class City {
    LinkedList<Destination> destinationList;
    String cityName;

    public City(String cityName, LinkedList<Destination> destinationList) {
        this.cityName = cityName;
        this.destinationList = destinationList;
    }

}