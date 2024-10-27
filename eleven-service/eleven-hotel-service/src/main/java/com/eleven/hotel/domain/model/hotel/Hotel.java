package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.model.hotel.event.HotelClosedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelOpenedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelRelocatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;


@Table(name = Hotel.TABLE_NAME)
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Hotel extends AbstractEntity implements Sellable {

    public static final String TABLE_NAME = "hotel";
    public static final String DOMAIN_NAME = "Hotel";

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private String hotelId;

    @Column(value = "sale_state")
    private SaleState saleState;

    @Embedded.Empty(prefix = "position_")
    private Position position;

    @Setter
    @Embedded.Empty(prefix = "hotel_")
    private Description description;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    protected Hotel(String hotelId) {
        this.id = DomainContext.nextId();
        this.hotelId = hotelId;
        this.saleState = SaleState.STARTED;
    }

    protected static Hotel of(String hotelId, Register register) {
        var description = new Description(register.getHotel().getName());

        var hotel = new Hotel(hotelId);
        hotel.setDescription(description);

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

    @Getter
    @FieldNameConstants
    @AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
    public static class Position {

        @Column(value = "province")
        private String province;

        @Column(value = "city")
        private String city;

        @Column(value = "district")
        private String district;

        @Column(value = "street")
        private String street;

        @Column(value = "address")
        private String address;

        @Column(value = "lat")
        private Double lat;

        @Column(value = "lng")
        private Double lng;

    }


    @Getter
    @FieldNameConstants
    @AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
    public static class Description {

        @Column(value = "name")
        private String name;

        @Column(value = "description")
        private String description;

        @Column(value = "head_pic_url")
        private String headPicUrl;

        @Column(value = "room_number")
        private Integer roomNumber;

        @Column(value = "check_in_time")
        private LocalTime checkInTime;

        @Column(value = "check_out_time")
        private LocalTime checkOutTime;

        @Column(value = "email")
        private String email;

        @Column(value = "tel")
        private String tel;

        public Description(String name) {
            this.name = name;
        }

    }
}
