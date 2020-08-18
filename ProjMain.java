import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;


public class ProjMain {

    public Scanner inFlightData(String fileName) {
        String flightDatFileName = fileName;
        File flightTing = new File(flightDatFileName);
        Scanner fD;
        try{
            fD = new Scanner(flightTing);
            return fD;
        }
        catch (IOException e) {
            System.out.print("File Cant be Found\n");
            return null;
        }
    }

    public Scanner inPathFile(String fileName) {
        String pathsToCalculateFile = fileName;
        File pathTing = new File(pathsToCalculateFile);
        Scanner pF;
        try {
            pF = new Scanner(pathTing);
            return pF;
        }
        catch (IOException e) {
            System.out.print("2nd File Cant be Found\n");
            return null;
        }
    }

    public ArrayList<City> readDataFile(Scanner flightDat, ArrayList<City> allCities) {
        flightDat.useDelimiter("(\\|)|(\\s+)");
        int numLines = Integer.parseInt(flightDat.nextLine());
        for(int i = 0; i < numLines; i++) {

            City cityA = addC(flightDat, allCities);
            String nameA = cityA.cityName;
            City cityB = addC(flightDat, allCities);
            String nameB = cityB.cityName;

            int tempCost = Integer.parseInt(flightDat.next());
            int tempTime = Integer.parseInt(flightDat.next());

            for(City c : allCities) {
                if(c.cityName.equals(nameA)) {
                    c.destinationList.add(new Destination(nameB, tempCost, tempTime));
                }
                else if(c.cityName.equals(nameB)) {
                    c.destinationList.add(new Destination(nameA, tempCost, tempTime));
                }
            }
        }
        return allCities;
    }

    public City addC(Scanner flightDat, ArrayList<City> allCities) {
        String tempCityNameA = flightDat.next();
        boolean cityInside = false;
        for (City c : allCities) {
            if (c.cityName.equals(tempCityNameA))
            {cityInside = true; return c;}
        }
        if (!cityInside) {
            City newCity = new City(tempCityNameA, new LinkedList<Destination>());
            allCities.add(newCity);
            return newCity;
        }
        return null;
    }

    public void flightCalculation(Scanner pathsToCalc, ArrayList<City> allCities, BufferedWriter writer) {
        int numLines = Integer.parseInt(pathsToCalc.nextLine());
        pathsToCalc.useDelimiter("(\\|)|(\\s+)");
        for(int i = 0; i < numLines; i++) {
            City source = getCity(pathsToCalc, allCities);
            City destination = getCity(pathsToCalc, allCities);
            ArrayList<SearchStack> paths = findAllPaths(allCities, source, destination);
            char var = pathsToCalc.next().charAt(0);
            displayShortestPaths(paths, var, writer);
        }
    }

    public City getCity(Scanner pathsToCalc, ArrayList<City> allCities) {
        String sourceName = pathsToCalc.next();
        City aCity = null;
        for (City c: allCities) {
            if(c.cityName.equals(sourceName))
            {aCity = c; break;}
        }
        if(aCity == null) {
            System.out.print("\nUh-oh, path list file contains a city not on the map\n");
            String temp = pathsToCalc.nextLine();
        }
        return aCity;
    }

    public ArrayList<SearchStack> findAllPaths(ArrayList<City> allCities, City source, City dest) {
        ArrayList<SearchStack> paths = new ArrayList<SearchStack>();
        SearchStack stk = new SearchStack();
        stk.push(new Destination(source.cityName, 0, 0));
        stk.peek().indexCT = 0;
        while(!stk.isEmpty()) {
            for (City x : allCities) {
                if(x.cityName.equals(stk.peek().destName)) {
                    if(stk.peek().indexCT < x.destinationList.size()) {
                        Destination upNext = x.destinationList.get(stk.peek().indexCT);
                        if(stk.isIn(upNext)) {
                            stk.peek().indexCT++;
                            break;
                        }
                        else if (upNext.destName.equals(dest.cityName)){
                            stk.push(upNext);
                            paths.add(new SearchStack(stk));
                            stk.pop();
                            stk.peek().indexCT++;
                            break;
                        }
                        else {
                            stk.push(upNext);
                            stk.peek().indexCT = 0;
                            break;
                        }
                    }
                    else {
                        stk.pop();
                        if(!stk.isEmpty())
                            stk.peek().indexCT++;
                        break;
                    }

                }
            }
        }
        return paths;
    }

    public void displayShortestPaths(ArrayList<SearchStack> paths, char var, BufferedWriter write) {
        if(paths.isEmpty())
            System.out.print("\nNo flight paths found!\n");
        else {
            for(int j = 0; j < 3 && !paths.isEmpty(); j++) {
                SearchStack minStk = null;
                int minimum = paths.get(0).getVar(var);
                for(SearchStack s: paths) {
                    if (s.getVar(var) <= minimum)
                    {minimum = s.getVar(var); minStk = s;}
                }
                minStk.displayFlightPath(j, var);
                try {
                    minStk.writeFlightPath(j, var, write);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paths.remove(minStk);
            }
        }
    }

}
