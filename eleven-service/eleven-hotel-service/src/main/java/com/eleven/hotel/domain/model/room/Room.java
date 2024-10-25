package com.eleven.hotel.domain.model.room;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@FieldNameConstants
@Table(name = Room.TABLE_NAME)
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Room extends AbstractEntity implements HotelAware, Sellable {

    public static final String TABLE_NAME = "room";

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private final String hotelId;

    @Column(value = "sale_state")
    private SaleState saleState;

    @Column(value = "charge_type")
    private ChargeType chargeType;

    @Embedded.Empty(prefix = "room_")
    private Description description;

    @Embedded.Empty(prefix = "restrict_")
    private Restriction restriction;

    @Version
    private Integer version;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    public Room(String id, String hotelId, Description description, Restriction restriction, ChargeType chargeType) {
        this.id = id;
        this.hotelId = hotelId;
        this.description = description;
        this.chargeType = chargeType;
        this.restriction = restriction;
        this.saleState = SaleState.STOPPED;
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

    public void update(ChargeType chargeType, Description description, Restriction restriction) {
        this.chargeType = chargeType;
        this.description = description;
        this.restriction = restriction;
    }

    public record Description(@Column(value = "name") String name,
                              @Column(value = "desc") String desc,
                              @Column(value = "pic_url") String headPicUrl,
                              @Column(value = "type") RoomType type) {
    }

    public record Restriction(@Column(value = "max_person") Integer maxPerson,
                              @Column(value = "min_person") Integer minPerson) {
        public Restriction {
            Validate.notNull(maxPerson, "max person must not null");
            Validate.notNull(minPerson, "min person must not null");
            Validate.isTrue(maxPerson > minPerson, "max person must be greater than min");
        }
    }

}
