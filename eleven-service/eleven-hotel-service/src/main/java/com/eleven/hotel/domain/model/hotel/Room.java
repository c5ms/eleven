package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.RoomLevel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;


@Table(name = "hms_hotel_room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Room extends AbstractEntity  {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState;

    @Setter
    @Embedded
    private RoomBasic basic;

    @Setter
    @Embedded
    private RoomRestriction restriction;

    @Version
    @Column(name = "update_version")
    private Long version;

    public Room(Long hotelId, RoomBasic basic, RoomRestriction restriction) {
        this.setHotelId(hotelId);
        this.setSaleState(SaleState.STOPPED);
        this.setBasic(basic);
        this.setRestriction(restriction);
    }

    public void startSale() {
        this.saleState = SaleState.STARTED;
    }

    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    public SaleState getSaleState() {
        return this.saleState;
    }

    public boolean isOnSale() {
        return saleState.isOnSale();
    }

    @Getter
    @Embeddable
    @FieldNameConstants
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class RoomBasic {

        @Column(name = "room_name")
        private String name;

        @Column(name = "room_level")
        @Enumerated(EnumType.STRING)
        private RoomLevel level;

        @Column(name = "room_desc")
        private String desc;

        @Column(name = "room_pic_url")
        private String headPicUrl;

        public RoomBasic(String name, RoomLevel level, String desc, String headPicUrl) {
            Validate.notNull(name, "name must not null");
            Validate.notNull(level, "level must not null");
            this.name = name;
            this.level = level;
            this.desc = desc;
            this.headPicUrl = headPicUrl;
        }
    }

    @Getter
    @Embeddable
    @FieldNameConstants
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class RoomRestriction {

        @Column(name = "restrict_max_person")
        private Integer maxPerson;

        @Column(name = "restrict_min_person")
        private Integer minPerson;

        public RoomRestriction(Integer minPerson, Integer maxPerson) {
            //        Validate.notNull(maxPerson, "max person must not null");
            //        Validate.notNull(minPerson, "min person must not null");
            //        Validate.isTrue(maxPerson > minPerson, "max person must be greater than min");
            this.maxPerson = maxPerson;
            this.minPerson = minPerson;
        }

    }
}
