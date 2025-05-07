package com.motiveschina.hotel.features.room;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Set;
import com.motiveschina.hotel.features.hotel.values.Occupancy;
import com.motiveschina.hotel.features.room.event.RoomActiveEvent;
import com.motiveschina.hotel.features.room.event.RoomCreatedEvent;
import com.motiveschina.hotel.features.room.event.RoomDeactivateEvent;
import com.motiveschina.hotel.utils.DateRangeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class RoomTest {

    Room room;

    @BeforeEach
    void setUp() {
        room = Room.builder()
            .hotelId(1L)
            .basic(RoomBasic.of("room type 1 ", "room desc", 40, 8))
            .stock(RoomStock.of(DateRangeTestUtils.today(), 100))
            .occupancy(Occupancy.of(1, 5))
            .images(Set.of("1", "2"))
            .build();
    }

    @Test
    @Order(1)
    void initialize() {
        assertThat(room.getHotelId()).isNotNull();
        assertThat(room.getActive()).isFalse();
        assertThat(room.hasEvents(RoomCreatedEvent.class)).isTrue();
    }

    @Test
    void update() {
    }

    @Test
    void active() {
        room.active();
        assertThat(room.getActive()).isTrue();
        assertThat(room.hasEvents(RoomActiveEvent.class)).isTrue();
    }

    @Test
    void deactivate() {
        room.active();
        room.deactivate();
        assertThat(room.getActive()).isFalse();
        assertThat(room.hasEvents(RoomDeactivateEvent.class)).isTrue();
    }

}
