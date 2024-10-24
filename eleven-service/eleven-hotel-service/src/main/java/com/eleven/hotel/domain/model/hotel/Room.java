package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomSize;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.values.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "room")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Room extends AbstractEntity implements HotelAware, Sellable {

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private final String hotelId;

    @Column(value = "name")
    private String name;

    @Column(value = "size")
    private RoomSize size;

    @Column(value = "sale_state")
    private SaleState saleState;

    @Column(value = "charge_type")
    private ChargeType chargeType;

    @Embedded.Empty
    private Stock stock;

    @Embedded.Empty
    private RoomDesc desc;

    private Room(String roomId, String hotelId) {
        this.id = roomId;
        this.hotelId = hotelId;
    }

    public static Room create(String id, Hotel hotel, String name, RoomDesc desc, RoomSize size, Stock stock, ChargeType chargeType) {
        DomainUtils.must(stock.greaterTan(Stock.ZERO), () -> new IllegalArgumentException("stock must gather than zero"));

        var hotelId = hotel.getId();
        var room = new Room(id, hotelId);
        room.name = name;
        room.size = size;
        room.stock = stock;
        room.desc = desc;
        room.chargeType = chargeType;
        room.saleState = SaleState.STOPPED;
        return room;
    }

    @Override
    public void startSale() {
        this.saleState = SaleState.STARTED;
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    @Override
    public SaleState getSaleState() {
        return this.saleState;
    }

    @Override
    public boolean isOnSale() {
        return saleState.isOnSale();
    }

    public void update(String name, RoomDesc desc, RoomSize size, ChargeType chargeType) {
        this.name = name;
        this.desc = desc;
        this.size = size;
        this.chargeType = chargeType;
    }

}
