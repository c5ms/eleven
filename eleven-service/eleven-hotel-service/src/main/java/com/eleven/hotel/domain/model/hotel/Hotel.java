package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.model.hotel.event.HotelClosedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelOpenedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelRelocatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;


@Table(name = "hotel")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Hotel extends AbstractEntity implements Sellable {

    @Id
    private String id;

    @Column(value = "sale_state")
    private SaleState saleState;

    @Column(value = "hotel_name")
    private String name;

    @Embedded.Empty(prefix = "position_")
    private Position position;

    @Setter
    @Embedded.Empty(prefix = "hotel_")
    private Description description;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    private Hotel(String id) {
        this.id = id;
        this.saleState = SaleState.STARTED;
    }

    public static Hotel of(String id, Register register) {
        var hotel = new Hotel(id);
        hotel.name = register.getHotelName();
        return hotel;
    }

    @Override
    public void startSale() {
        this.saleState = SaleState.STARTED;
        addEvent(new HotelOpenedEvent(id));
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
        addEvent(new HotelClosedEvent(id));
    }

    @Override
    public boolean isOnSale() {
        return saleState.isOnSale();
    }

    public void relocate(Position position) {
        this.position = position;
        this.addEvent(new HotelRelocatedEvent(id));
    }

    public boolean has(HotelAware belonging) {
        return StringUtils.equals(belonging.getHotelId(), getId());
    }

    @Getter
    @RequiredArgsConstructor
    @FieldNameConstants
    public static class Position {

        @Column(value = "province")
        private final String province;

        @Column(value = "city")
        private final String city;

        @Column(value = "district")
        private final String district;

        @Column(value = "street")
        private final String street;

        @Column(value = "address")
        private final String address;

        @Column(value = "lat")
        private final Double lat;

        @Column(value = "lng")
        private final Double lng;

    }


    @Getter
    @RequiredArgsConstructor
    @FieldNameConstants
    public static class Description {

        @Column(value = "description")
        private final String description;

        @Column(value = "head_pic_url")
        private final String headPicUrl;

        @Column(value = "room_number")
        private final Integer roomNumber;

        @Column(value = "check_in_time")
        private final LocalTime checkInTime;

        @Column(value = "check_out_time")
        private final LocalTime checkOutTime;

        @Column(value = "email")
        private final String email;

        @Column(value = "tel")
        private final String tel;

    }
}
