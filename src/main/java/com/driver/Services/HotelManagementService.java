package com.driver.Services;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelManagementService {
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel){
        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user){
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities(){
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking){
        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard)
    {
       return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }

}
