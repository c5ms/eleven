package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.model.hotel.event.HotelClosedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelOpenedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelRelocatedEvent;
import com.eleven.hotel.domain.values.Contact;
import com.eleven.hotel.domain.values.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;


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

    @Embedded.Empty(prefix = "contact_")
    private Contact contact;

    @Embedded.Empty(prefix = "hotel_")
    private HotelDesc desc;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    private Hotel(String id) {
        this.id = id;
    }

    public static Hotel create(String id, Register register) {
        var hotel = new Hotel(id);
        hotel.name = register.getHotelName();
        hotel.saleState = SaleState.STARTED;
        return hotel;
    }

    public void update(HotelDesc detail) {
        this.desc = detail;
    }

    public void update(Contact contact) {
        this.contact = contact;
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

    public boolean isBelongingBy(HotelAware belonging) {
        return StringUtils.equals(belonging.getHotelId(), getId());
    }
}
