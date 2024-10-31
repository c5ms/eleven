package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;


@Table(name = "hms_room")
@Entity
@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends AbstractEntity implements Saleable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState;

    @Setter
    @Column(name = "charge_type")
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @Setter
    @Embedded
    private Description description;

    @Setter
    @Embedded
    private Restriction restriction;

    @Version
    @Column(name = "update_version")
    private Integer version;

    public Room(Integer hotelId, Description description, Restriction restriction, ChargeType chargeType) {
        Validate.notNull(restriction.maxPerson, "max person must not null");
        Validate.notNull(restriction.minPerson, "min person must not null");
        Validate.isTrue(restriction.maxPerson > restriction.minPerson, "max person must be greater than min");

        this.hotelId = hotelId;
        this.saleState = SaleState.STOPPED;
        this.setDescription(description);
        this.setChargeType(chargeType);
        this.setRestriction(restriction);
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
    @Embeddable
    @FieldNameConstants
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class Description {

        @Column(name = "room_name")
        private String name;

        @Column(name = "room_type")
        private RoomType type;

        @Column(name = "room_desc")
        private String desc;

        @Column(name = "room_pic_url")
        private String headPicUrl;

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
    @Embeddable
    @FieldNameConstants
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class Restriction {

        @Column(name = "restrict_max_person")
        private Integer maxPerson;

        @Column(name = "restrict_min_person")
        private Integer minPerson;

        public Restriction(Integer minPerson, Integer maxPerson) {
            this.maxPerson = maxPerson;
            this.minPerson = minPerson;
        }

    }

}
