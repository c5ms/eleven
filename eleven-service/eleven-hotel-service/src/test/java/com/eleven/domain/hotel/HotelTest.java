package com.eleven.domain.hotel;

import com.eleven.travel.domain.hotel.Hotel;
import com.eleven.travel.domain.hotel.command.HotelUpdateCommand;
import com.eleven.travel.domain.hotel.event.HotelActiveEvent;
import com.eleven.travel.domain.hotel.event.HotelCreatedEvent;
import com.eleven.travel.domain.hotel.event.HotelDeactivateEvent;
import com.eleven.travel.domain.hotel.event.HotelUpdatedEvent;
import com.eleven.travel.domain.hotel.values.Address;
import com.eleven.travel.domain.hotel.values.CheckPolicy;
import com.eleven.travel.domain.hotel.values.HotelBasic;
import com.eleven.travel.domain.hotel.values.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.YearMonth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HotelTest {

    Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = Hotel.builder()
                .basic(HotelBasic.of("test hotel", "test hotel desc", "test@test.com", "87960606", 20, YearMonth.of(2024, 1), YearMonth.of(2024, 5), 5, 900))
                .address(Address.of("china", "liaoning", "dalian", "high tech street", "32#"))
                .position(Position.of(113.00001D, 114.00001D))
                .checkPolicy(CheckPolicy.of(LocalTime.of(12, 0), LocalTime.of(13, 0)))
                .build();
    }

    @Test
    @Order(1)
    void initialize() {
        assertThat(hotel).hasNoNullFieldsOrPropertiesExcept("hotelId");
        assertThat(hotel.getHotelId()).isNull();
        assertThat(hotel.getActive()).isFalse();
        assertThat(hotel.hasEvents(HotelCreatedEvent.class)).isTrue();
    }

    @Test
    void active() {
        hotel.setActive(false);
        hotel.active();
        assertThat(hotel.getActive()).isTrue();
        assertThat(hotel.hasEvents(HotelActiveEvent.class)).isTrue();
    }

    @Test
    void deactivate() {
        hotel.setActive(true);
        hotel.deactivate();
        assertThat(hotel.getActive()).isFalse();
        assertThat(hotel.hasEvents(HotelDeactivateEvent.class)).isTrue();
    }

    @Test
    void update() {
        var patch = HotelUpdateCommand.builder()
                .basic(HotelBasic.of("test hotel (new name)", "test hotel desc (new desc)", "test@test.com", "87960606", 20, YearMonth.of(2024, 1), YearMonth.of(2024, 5), 5, 900))
                .address(Address.of("china", "liaoning", "dalian", "high tech street", "32#"))
                .position(Position.of(113.00001D, 114.00001D))
                .checkPolicy(CheckPolicy.of(LocalTime.of(12, 0), LocalTime.of(13, 0)))
                .build();
        hotel.update(patch);
        assertThat(hotel.getBasic()).isEqualTo(patch.getBasic());
        assertThat(hotel.getAddress()).isEqualTo(patch.getAddress());
        assertThat(hotel.getPosition()).isEqualTo(patch.getPosition());
        assertThat(hotel.getCheckPolicy()).isEqualTo(patch.getCheckPolicy());
        assertThat(hotel.hasEvents(HotelUpdatedEvent.class)).isTrue();
    }


}
