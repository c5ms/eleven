package com.eleven.hotel.api.endpoint.core;

public interface HotelEndpoints {

    interface Tags {
        String REGISTER = "register";
        String HOTEL = "hotel";
        String ROOM = "room";
        String PLAN = "plan";
    }

    interface Paths {
        String REGISTER = "/registers";
        String HOTEL = "/hotels";
        String ROOM =   "/hotels/{hotelId:\\d+}/rooms";
        String PLAN =  "/hotels/{hotelId:\\d+}/plans";
    }


}
