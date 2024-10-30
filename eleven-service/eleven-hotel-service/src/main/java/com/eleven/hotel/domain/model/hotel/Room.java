package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.Sellable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
public class Room extends AbstractEntity implements Sellable {

    public static final String TABLE_NAME = "room";
    public static final String DOMAIN_NAME = "Room";

    @Id
    @Column("obj_id")
    private final String id;

    @Column(value = "hotel_id")
    private String hotelId;

    @Column(value = "room_id")
    private String roomId;

    @Column(value = "sale_state")
    private SaleState saleState;

    @Setter
    @Column(value = "charge_type")
    private ChargeType chargeType;

    @Setter
    @Embedded.Empty(prefix = "room_")
    private Description description;

    @Setter
    @Embedded.Empty(prefix = "restrict_")
    private Restriction restriction;

    @Version
    @Column("update_version")
    private Integer version;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    Room(String hotelId, String roomId) {
        this.id = DomainHelper.nextId();
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.saleState = SaleState.STOPPED;
    }

    public   static Room of(String hotelId, String roomId, Description description, Restriction restriction, ChargeType chargeType) {
        Validate.notNull(restriction.maxPerson, "max person must not null");
        Validate.notNull(restriction.minPerson, "min person must not null");
        Validate.isTrue(restriction.maxPerson > restriction.minPerson, "max person must be greater than min");

        var room = new Room(hotelId, roomId);
        room.setDescription(description);
        room.setChargeType(chargeType);
        room.setRestriction(restriction);
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

    @Getter
    @FieldNameConstants
    public static final class Description {

        @Column(value = "name")
        private final String name;

        @Column(value = "type")
        private final RoomType type;

        @Column(value = "desc")
        private final String desc;

        @Column(value = "pic_url")
        private final String headPicUrl;

        public Description(String name, RoomType type, String desc, String headPicUrl) {
            Validate.notNull(name, "name must not null");
            Validate.notNull(type, "type must not null");
            this.name = name;
            this.type = type;
            this.desc = desc;
            this.headPicUrl = headPicUrl;
        }

    }

    @Getter
    @FieldNameConstants
    public static final class Restriction {

        @Column(value = "max_person")
        private final Integer maxPerson;

        @Column(value = "min_person")
        private final Integer minPerson;

        public Restriction(Integer minPerson, Integer maxPerson) {
            this.maxPerson = maxPerson;
            this.minPerson = minPerson;
        }

    }

}
