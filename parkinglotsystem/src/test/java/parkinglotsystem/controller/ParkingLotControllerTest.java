package parkinglotsystem.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;

import parkinglotsystem.controller.ParkingLotController;
import parkinglotsystem.model.Car;
import parkinglotsystem.model.Slot;
import parkinglotsystem.model.Token;
import parkinglotsystem.service.ParkingLot;
@WebMvcTest(ParkingLotController.class)
class ParkingLotControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParkingLot service;
	
	    @Test
	    public void initiateSlots() throws Exception
	    {
	        Slot slot = new Slot(1);
	        ArrayList<Slot> Success = new ArrayList<Slot>();
	        Success.add(slot);

	        when(service.initiateLot(10)).thenReturn(Success);

	        mockMvc.perform(post("/create_parking_lot_with_slots")
	                .param("NumberOfLot","10"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].slotNumber").value("1"))
	                .andExpect(jsonPath("$[0].slotFree").value("true"));
	        verify(service, times(1)).initiateLot(10);
	        verifyNoMoreInteractions(service);

	    }
	    @Test
	    public void parkTheCar() throws Exception
	    {
	        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));

	        when(service.parkTheCar("Blue","123")).thenReturn(token);
	        mockMvc.perform(post("/ParkTheCar")
	                        .param("Color","Blue")
	                        .param("Number","123")
	                )
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$['carDetails'].carColor").value("Blue"))
	                .andExpect(jsonPath("$['carDetails'].carNumber").value("123"))
	                .andExpect(jsonPath("$.tokenNumber").value("123123"));
	        verify(service, times(1)).parkTheCar("Blue","123");
	        verifyNoMoreInteractions(service);

	    }
    @Test
    public void searchCarByRegNo() throws Exception
    {
        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));

        when(service.getCarByRegNO("123")).thenReturn(token);

        mockMvc.perform(get("/getCarByRegNo/{carNumber}", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenNumber", is("123123")))
                .andExpect(jsonPath("$['carDetails'].carNumber", is("123"))
                );

        verify(service, times(1)).getCarByRegNO("123");
        verifyNoMoreInteractions(service);

    }
    @Test
    public void searchCarByColor() throws Exception
    {
        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));

        when(service.getCarByColor("Blue")).thenReturn(token);

        mockMvc.perform(get("/getCarByColor/{carColor}", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenNumber", is("123123")))
                .andExpect(jsonPath("$['carDetails'].carNumber", is("123"))
                ).andExpect(jsonPath("$['carDetails'].carColor", is("Blue"))
                        );

        verify(service, times(1)).getCarByColor("Blue");
        verifyNoMoreInteractions(service);

    }
    @Test
    public void UnParkTheCar() throws Exception
    {
        String responseString = "Car exit from parkinglot";

        when(service.unParkTheCar("123123")).thenReturn(responseString);

        mockMvc.perform(delete("/unParkTheCar/{tokenNumber}", "123123"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", is("Car exit from parkinglot")));

        verify(service, times(1)).unParkTheCar("123123");
        verifyNoMoreInteractions(service);

    }

}
