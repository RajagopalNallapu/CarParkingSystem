package parkinglotsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import parkinglotsystem.model.Car;
import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
@Service
public class ParkingLot {
	 ArrayList<Slot> availableSlotList;
	   private final List<Token> tokenForLot;
	   private final List<Token> historyOfParking;

	   public ParkingLot() {
	      this.tokenForLot = new ArrayList<>();
	      this.historyOfParking = new ArrayList<>();
	   }

	   public ArrayList<Slot> initiateLot(int numberOfLots) {
	      ArrayList<Slot> totalSlots = new ArrayList<Slot>() {};
	      for (int i = 1; i<= numberOfLots; i++) {
	         Slot getSlotAssignment = new Slot(i);
	         totalSlots.add(getSlotAssignment);
	      }

	      return this.availableSlotList = totalSlots;
	   }

	   public Token parkTheCar(String carColor, String carNumber){
	      Car car = new Car(carColor,carNumber);
	      if(isSlotAvailable()){
	         Slot availableSlot = getTheNextFreeSlot();
	         Token parkingToken = new Token(String.valueOf(System.currentTimeMillis()),availableSlot,car);
	         this.tokenForLot.add(parkingToken);
	         return parkingToken;
	      }else {
	         return null;
	      }
	   }
	   private boolean isSlotAvailable() {
	      boolean isSlotAvailable = false;

	      for(Slot slot:availableSlotList){
	         if(slot.isSlotFree()){
	            isSlotAvailable = true;
	            break;
	         }
	      }
	      return isSlotAvailable;
	   }
	   private Slot getTheNextFreeSlot() {
	      for(Slot slot : availableSlotList){
	         if(slot.isSlotFree()){
	            slot.makeSlotOccupied();
	            return slot;
	         }
	      }
	      return null;
	   }

	   public Token getCarByRegNO(String carNumber) {
	      for(Token tokenSearch:tokenForLot){
	         String carDetails = tokenSearch.getCarDetails().getCarNumber();
	         if(carDetails.equalsIgnoreCase(carNumber)){
	           return tokenSearch;
	         }
	      }
	      return null;
	   }
	   public Token getCarByColor(String carColor) {
		      for(Token tokenSearch:tokenForLot){
		         String carDetails = tokenSearch.getCarDetails().getCarColor();
		         if(carDetails.equalsIgnoreCase(carColor)){
		           return tokenSearch;
		         }
		      }
		      return null;
		   }

	   public String unParkTheCar(String tokenNumber) {
	      for(Token tokenInLot:tokenForLot){
	         if(tokenInLot.getTokenNumber().equals(tokenNumber)){
	            tokenForLot.remove(tokenInLot);
	            Slot slot = tokenInLot.getSlotDetails();
	            int slotNumber = slot.getSlotNumber();
	            return removeCarFromSlot(tokenInLot,slotNumber);
	         }
	         return "No token found";
	      }
	      return null;
	   }


	   private String removeCarFromSlot(Token token, int slotNumber) {
	      for (Slot removeEntry:availableSlotList){
	         if(removeEntry.getSlotNumber() == slotNumber){
	            removeEntry.makeSlotFree();
	            Token historyToken = token.updateCheckOutTime();
	            historyOfParking.add(historyToken);
	            return "Car exit from parkinglot";
	         }

	      }
	      return null;
	   }
	   public List<Token> listAllCars(){
	      for(Token tokenSearch:tokenForLot){
	         return tokenForLot;
	      }
	      return tokenForLot;
	   }
	   public List<Token> historyOfParking(){
	      return historyOfParking;
	   }
}
