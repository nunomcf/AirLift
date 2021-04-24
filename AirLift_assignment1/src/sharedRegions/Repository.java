package sharedRegions;

import java.io.File;
import java.io.PrintWriter;

import entities.States;
import main.AirLift;

import java.io.FileNotFoundException;

/**
 * This datatype implements the general repository of information shared region.
 * The internal state of the simulation is kept here and the logging is handled.
 */
public class Repository {
	// State abreviations
    private final String[] stateAbrv = { "ATRG", "RDFB", "WTFB", "FLFW", "DRPP", "FLBK", "WTFL", "WTPS", "CKPS", "RDTF", "GTAP",
            "INQE", "INFL", "ATDS"};
    
    private File f;
    private PrintWriter pw;
    
    // Pilot
    private States pilotState; 
    
    // Hostess
    private States hostessState; 
    
    // Passenger
    private States[] passengersState;
    
    /**
     * Repository instantiation.
     * @throws FileNotFoundException when there's no file
     */
    public Repository() throws FileNotFoundException {
    	f = new File(AirLift.filename);
        pw = new PrintWriter(f);
        
        pilotState = States.AT_TRANSFER_GATE;
        hostessState = States.WAIT_FOR_NEXT_FLIGHT;

        passengersState = new States[AirLift.N_PASSENGERS];
        for (int i = 0; i < AirLift.N_PASSENGERS; i++)
        	passengersState[i] = States.GOING_TO_AIRPORT;
    }
    
    /**
     * Closes the printwriter. Called when the simulation ends.
     */
    public void closeWriter() {
        //pw.close();
    }
    
    /**
    *
    * @param pilotState state of the pilot
    */
   public synchronized void setPilotState(States pilotState) {
       if (this.pilotState != pilotState) {
           this.pilotState = pilotState;
           export();
       }
   }

   /**
    *
    * @param id if of the passenger
    * @param passengerState state of the passenger
    */
   public synchronized void setPassengerState(int id, States passengerState, boolean export) {
       if (passengersState[id] != passengerState) {
    	   passengersState[id] = passengerState;
           if (export)
               export();
       }
   }
   
   /**
    * Prints the internal state and also saves it to a file.
    */
   private void export() {
       String output = getInternalState();
       System.out.println(output);
       pw.write(output);
       pw.flush();
   }
   
   /**
    * Builds the string that represents the internal state.
    * @return internal state of the problem as a string
    */
   private String getInternalState() {
       String str = stateAbrv[pilotState.ordinal()] + "  ";
       str += String.format("%s ", stateAbrv[hostessState.ordinal()]);
       
/*
       for (int i = 0; i < RepairShop.N; i++) {
           str += String.format(" %s %02d %s %s ", stateAbrv[customerStates[i].ordinal()], currentCarId[i],
                   booleanToStr(wantRental[i]), booleanToStr(carRepaired[i]));

           if ((i+1) % 10 == 0)
               str += "\n              ";
       }

       str += String.format("  %02d %02d %02d", customerQueue, waitingForKey, finishedJobs);
       str += String.format("                  %02d  %02d", customerCars, replacementCars);
       str += String.format("          %02d ", reqCount);

       for (int i = 0; i < RepairShop.K; i++) {
           str += String.format("  %02d  %02d  %s ", stock[i], pendingVehicles[i], booleanToStr(managerAlerted[i]));
       }

       str += "               ";
       
       for (int i = 0; i < RepairShop.K; i++) {
           str += String.format("  %02d", soldParts[i]);
       }*/

       return str + "\n\n";
   }

   private String booleanToStr(boolean b) {
       if (b)
           return "T";
       return "F";
   }
}
