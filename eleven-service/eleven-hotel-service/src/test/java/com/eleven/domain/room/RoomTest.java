package com.eleven.domain.room;

import com.eleven.domain.hotel.values.Occupancy;
import com.eleven.domain.room.event.RoomActiveEvent;
import com.eleven.domain.room.event.RoomCreatedEvent;
import com.eleven.domain.room.event.RoomDeactivateEvent;
import com.eleven.utils.DateRangeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
