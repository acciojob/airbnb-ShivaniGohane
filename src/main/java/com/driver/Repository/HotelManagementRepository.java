package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Repository
public class HotelManagementRepository {

    HashMap<String,Hotel> hoteldb=new HashMap<>();
    HashMap<Integer,User> userdb=new HashMap<>();
    HashMap<String,Booking> bookingdb=new HashMap<>();
    HashMap<Integer,Integer> countofBooking=new HashMap<>();

    public String addHotel(Hotel hotel){
        if(hotel==null || hotel.getHotelName()==null){
            return "FAILURE";
        }
        if(hoteldb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hoteldb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(@RequestBody User user){
        userdb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities(){
        int max = 0;
        for (String hotelName : hoteldb.keySet()){
            max = Math.max(max, hoteldb.get(hotelName).getFacilities().size());
        }
        if(max==0){
            return "";
        }
        List<String> hotelWithMostFacility = new ArrayList<>();
        for (String hotel : hoteldb.keySet()){
            if(hoteldb.get(hotel).getFacilities().size()==max){
                hotelWithMostFacility.add(hotel);
            }
        }
        if(hotelWithMostFacility.size()==1){
            return hotelWithMostFacility.get(0);
        }
        Collections.sort(hotelWithMostFacility);
        return hotelWithMostFacility.get(0);
    }

    public int bookARoom(Booking booking){

        String key = UUID.randomUUID().toString();
        booking.setBookingId(key);

        String hotelName = booking.getHotelName();
        if(hoteldb.containsKey(hotelName)==false || hoteldb.get(hotelName).getAvailableRooms()<booking.getNoOfRooms()){
            return -1;
        }
        Hotel hotel = hoteldb.get(hotelName);
        int amount = hotel.getPricePerNight()*booking.getNoOfRooms();
        booking.setAmountToBePaid(amount);
        hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());
        hoteldb.put(hotelName, hotel);
        bookingdb.put(key, booking);
        int adharNo = booking.getBookingAadharCard();
        Integer currBooking = countofBooking.get(adharNo);
        if(countofBooking.containsKey(adharNo)){
            countofBooking.put(adharNo, currBooking+1);
        }
        else {
            countofBooking.put(adharNo, 1);
        }
        return booking.getAmountToBePaid();
    }

    public int getBookings(Integer aadharCard)
    {
        return countofBooking.get(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
        Hotel hotel = hoteldb.get(hotelName);
        List<Facility> currFacility = hotel.getFacilities();

        for (Facility facility : newFacilities){
            if(currFacility.contains(facility)==false){
                currFacility.add(facility);
            }
        }
        hotel.setFacilities(currFacility);
        hoteldb.put(hotelName,hotel);
        return hotel;
    }
}
