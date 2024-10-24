package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.values.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;
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

    @Column(value = "sale_state")
    private SaleState saleState;

    @Column(value = "charge_type")
    private ChargeType chargeType;

    @Embedded.Empty(prefix = "stock_")
    private Stock stock;

    @Embedded.Empty(prefix = "room_")
    private Desc desc;

    @Embedded.Empty(prefix = "restrict_")
    private Restrict restrict;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    @Builder
    public Room(String roomId, String hotelId, Desc desc, Stock stock, ChargeType chargeType, Restrict restrict) {
        Validate.isTrue(stock.greaterTan(Stock.ZERO), "stock must gather than zero");
        this.id = roomId;
        this.hotelId = hotelId;
        this.stock = stock;
        this.desc = desc;
        this.chargeType = chargeType;
        this.saleState = SaleState.STOPPED;
        this.restrict = restrict;
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

    public void update(Desc desc) {
        this.desc = desc;
    }

    public void update(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public void update(Restrict restrict) {
        this.restrict = restrict;
    }

    @Getter
    @Builder
    public static class Desc {

        @Column(value = "name")
        private String name;

        @Column(value = "desc")
        private String desc;

        @Column(value = "pic_url")
        private String headPicUrl;

        @Column(value = "type")
        private RoomType type;
    }


    @Getter
    @Builder
    public static class Restrict {

        @Column(value = "max_person")
        private Integer maxPerson;

        @Column(value = "min_person")
        private Integer minPerson;

    }


}
