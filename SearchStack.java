import java.util.ArrayList;
import java.io.*;

public class SearchStack {

    int tT;
    int tC;

    ArrayList<Destination> citiesVisited;

    SearchStack() {
        citiesVisited = new ArrayList<Destination>();
        tC = 0;
        tT = 0;
    }

    SearchStack(SearchStack a) {
        this.citiesVisited = new ArrayList<Destination>(a.citiesVisited);
        this.tC = a.tC;
        this.tT = a.tT;
    }

    SearchStack(ArrayList<Destination> allCities) {
        citiesVisited = allCities;
        tC = 0;
        tT = 0;
    }

    public int push(Destination destination) {
        citiesVisited.add(destination);
        updatePathWeight(destination.flightCost, destination.flightTime);
        return citiesVisited.indexOf(destination);
    }

    public Destination pop() {
        Destination lastOne = citiesVisited.get(citiesVisited.size()-1);
        updatePathWeight(-lastOne.flightCost, -lastOne.flightTime);
        citiesVisited.remove(citiesVisited.size()-1);
        return lastOne;
    }

    public boolean isIn(Destination d) {
        for(Destination x: citiesVisited) {
            if(x.destName.equals(d.destName))
                return true;
        }
        return false;
    }

    public Destination peek() {
        return citiesVisited.get(citiesVisited.size() - 1);
    }

    public int getVar(char var) {
        if(var == 'C')
            return tC;
        else if(var == 'T')
            return tT;
        else
            return -1;
    }


    public boolean isEmpty() {
        if(citiesVisited.isEmpty())
            return true;
        else
            return false;
    }


    public void writeFlightPath(int index, char var, BufferedWriter writer) throws IOException {
        writer.write("\nFlight Path " + (index+1) + ": \n");
        for(int i = 0; i < citiesVisited.size(); i++) {
            writer.write(citiesVisited.get(i).destName);
            if(i != citiesVisited.size() - 1)
                writer.write(" -> ");
            else
                writer.write("; ");
        }
        if(var == 'C')
        {writer.write("\nTotal Cost: $" + tC); }
        else if(var == 'T')
        {writer.write("\nTotal Time: " + tT + " minutes"); }
        else
            writer.write("\nError, no flight paths exist\n");
        writer.write(". \n");
    }

    public void displayFlightPath(int index, char var) {
        System.out.print("\nFlight Path (");
        if (var == 'C')
            System.out.print("Cost");
        else
            System.out.print("Time");
        System.out.print(") " + (index+1) + ": \n");
        for(int i = 0; i < citiesVisited.size(); i++) {
            System.out.print(citiesVisited.get(i).destName);
            if(i != citiesVisited.size() - 1)
                System.out.print(" -> ");
            else
                System.out.print("; ");
        }
        if(var == 'C')
            printC();
        else if(var == 'T')
            printT();
        else
            System.out.print("\nError, no flight paths exist\n");
        System.out.print(". \n");
    }

    public void updatePathWeight(int c, int t) {
        addC(c);
        addT(t);
    }

    public void addC(int c) {
        tC += c;
    }

    public void addT(int t) {
        tT += t;
    }

    public void printT() {
        System.out.print("Total Time: " + tT + " minutes");
    }

    public void printC() {
        System.out.print("Total Cost: $" + tC);
    }
}