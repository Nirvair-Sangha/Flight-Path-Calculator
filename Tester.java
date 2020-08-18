import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws IOException {
        ProjMain obj = new ProjMain();
        Scanner flightDataFile = obj.inFlightData("/Users/nicksangha/Desktop/Project3_3345_New/FlightDataFile");//used absolute file path
        Scanner pathCalc = obj.inPathFile("/Users/nicksangha/Desktop/Project3_3345_New/PathsToCalculateFile");
        BufferedWriter write = new BufferedWriter(new FileWriter("/Users/nicksangha/Desktop/Project3_3345_New/OutputFile.txt"));
        ArrayList<City> allCityList = new ArrayList<City>();
        allCityList = obj.readDataFile(flightDataFile, allCityList);
        obj.flightCalculation(pathCalc, allCityList, write);
        write.close();
    }
}
