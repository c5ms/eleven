package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainUtils;
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
public class HotelRoom extends AbstractEntity implements HotelAware, Sellable {

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

    @Embedded.Empty
    private Stock stock;

    @Embedded.Empty
    private RoomDesc desc;

    private HotelRoom(String roomId, String hotelId) {
        this.id = roomId;
        this.hotelId = hotelId;
    }

    public static HotelRoom create(String id, Hotel hotel, String name, RoomDesc desc, RoomSize size, Stock stock) {
        DomainUtils.must(stock.greaterTan(Stock.ZERO), () -> new IllegalArgumentException("stock must gather than zero"));

        var hotelId = hotel.getId();
        var room = new HotelRoom(id, hotelId);
        room.name = name;
        room.size = size;
        room.stock = stock;
        room.desc = desc;
        room.saleState=SaleState.STOPPED;
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

    public void updateDesc(RoomDesc desc) {
        this.desc = desc;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateSize(RoomSize size) {
        this.size = size;
    }

    public void updateStock(Stock stock) {
        this.stock = stock;
    }
}
