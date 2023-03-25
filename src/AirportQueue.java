import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class AirportQueue {
    private static final int MAX_QUEUE_SIZE = 10;
    public int timeSlot = 0;
    public float arrivalFreq;
    public float departureFreq;
    Queue<Plane> arrivalQueue = new LinkedList<>();
    Queue<Plane> departureQueue = new LinkedList<>();
    public int numOfArrivals = 0;
    public int numOfDepartures = 0;
    public int numRejected = 0;
    public int totalWaitingTime = 0;


    //Les data for simuleringen fra bruker:
    public AirportQueue(String airportName) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nSimulation for " + airportName + " airport\n");
        System.out.print("How many time slots?: ");
        timeSlot = scanner.nextInt();
        System.out.print("Avg frequency for departures? ");
        arrivalFreq = scanner.nextFloat();
        System.out.print("Avg frequency for arrivals?: ");
        departureFreq = scanner.nextFloat();
    }

    //Standard metode for å trekke random antall avganger/ankomster:
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public class Plane {
        //tid den ankommer landingskø
        private int startTime;
        private int id;

        public Plane(int startTime, int id) {
            this.startTime = startTime;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public int getWaitTime(int now) {
            return now - startTime;
        }
    }

    public void simulate() {
        Plane plane = null;
        int arriving, departing, id = 0;

        ////For hver tidssteg i simuleringen
        for (int time = 0; time < timeSlot; time++) {

            System.out.println(time + ": ");

            //Trekk et tilfeldig antall nye fly som kommer for å lande
            arriving = getPoissonRandom(arrivalFreq);

            for (int j = 0; j < arriving; j++) {
                if (arrivalQueue.size() >= MAX_QUEUE_SIZE) {
                    //Hvis landingskøen er full
                    //Avvis det nye flyet (henvis til annen flyplass)
                    System.out.println("\tArrival rejected, no available slots");
                    numRejected++;
                } else {
                    //Ellers - Sett det nye flyet sist i landingskøen
                    plane = new Plane(time, ++id);
                    arrivalQueue.add(plane);
                    System.out.printf("\tFlight " + plane.getId() + " queued for arrival at time " + time + "\n");
                    plane.startTime = time;
                }
            }

            //Trekk et tilfeldig antall nye fly som kommer for å ta av:
            departing = getPoissonRandom(departureFreq);


            //For hvert nytt fly som kommer for å ta av
            for (int k = 0; k < departing; k++) {
                //Hvis landingskøen er full
                if (departureQueue.size() >= MAX_QUEUE_SIZE) {
                    //Avvis det nye flyet (avgang må prøves senere)
                    System.out.println("\tDeparture rejected, no available slots, please try again later");
                    numRejected++;
                } else {
                    //ellers
                    //Sett det nye flyet sist i avgangskøen
                    plane = new Plane(time, ++id);
                    departureQueue.add(plane);
                    System.out.printf("\tFlight " + plane.getId() + " queued for departure at time " + time + "\n");
                    plane.startTime = time;
                }
            }

            //Hvis landingskøen ikke er tom
            if (!arrivalQueue.isEmpty()) {
                //Ta ut første fly i landingskøen og la det få lande
                System.out.println("\tFlight: " + arrivalQueue.poll().getId() + " is now landing at time " + time + "\n");
                numOfArrivals++;
            }
            //ellers hvis avgangskøen ikke er tom
            else if (!departureQueue.isEmpty()) {
                //Ta ut første fly i avgangskøen og la det få ta av
                System.out.println("\tFlight: " + departureQueue.poll().getId() + " is now departing at time " + time + "\n");
                numOfDepartures++;
            }
            //ellers
            else {
                //Flyplassen er tom for fly
                System.out.println("\tRunway queue empty");
            }
        }
    }

    public int getTotalHandledFlights() {
        return numOfArrivals + numOfDepartures;
    }

    public int getAverageWaitTime(){
        return totalWaitingTime / (numOfDepartures + numOfArrivals);
    }

    public void printOut() {
        System.out.println("Simulation done after " + timeSlot + " time units");
        System.out.println("Number of flights handled: " + getTotalHandledFlights());
        System.out.println("Number of flights departed: " + numOfDepartures);
        System.out.println("Number of flights arrived: " + numOfArrivals);
        System.out.println("Number of rejected flights: " + numRejected);
        System.out.println("Number of flights in arrival queue: " + arrivalQueue.size());
        System.out.println("Number of flights in departure queue: " + departureQueue.size());

    }

    public static void main(String[] args) {
        AirportQueue airport = new AirportQueue("OSL");
        airport.simulate();
        airport.printOut();


    }

}

