package parkinglotsystem.controller;


import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
import parkinglotsystem.service.ParkingLot;

@RestController
@RequestMapping("CarParkingSystem")
public class ParkingLotController {

	private final ParkingLot service;

	public ParkingLotController(ParkingLot service) {
		this.service = service;
	}
    
    @PostMapping("/create_parking_lot_with_slots")
    public ArrayList<Slot> initiateLot(@RequestParam("NumberOfLot") String numberOfLot){
        ArrayList<Slot> availableSlot= service.initiateLot(Integer.parseInt(numberOfLot));
        return  availableSlot;
    }
   @PostMapping("/ParkTheCar")
    public Token parkCar(@RequestParam("Color") String carColor,@RequestParam("Number") String carNumber){
        Token token = service.parkTheCar(carColor,carNumber);
        return token;
    }

    @DeleteMapping("/unParkTheCar/{tokenNumber}")
    public String unParkCar(@PathVariable String tokenNumber){
        String parkingStatus = service.unParkTheCar(tokenNumber);
        return parkingStatus;
    }

    @GetMapping("/getCarByRegNo/{carNumber}")
    public Token getCarByRegNo(@PathVariable String carNumber) {
        return service.getCarByRegNO(carNumber);
    }
    @GetMapping("/getCarByColor/{carColor}")
    public Token getCarByColor(@PathVariable String carColor) {
        Token token = service.getCarByColor(carColor);
        return token;
    }

}