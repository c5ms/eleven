package com.eleven.hotel.api.endpoint.core;

public interface HotelEndpoints {

    interface Tags {
        String REGISTER = "register";
        String HOTEL = "hotel";
        String ROOM = "room";
        String PLAN = "plan";
    }

    interface Paths {
        String REGISTER = "/register";
        String HOTEL = "/hotels";
        String ROOM = HOTEL + "/{hotelId}/rooms";
        String PLAN = HOTEL + "/{hotelId}/plans";
    }


}
