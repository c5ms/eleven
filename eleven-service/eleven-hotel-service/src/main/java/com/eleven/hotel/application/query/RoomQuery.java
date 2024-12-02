package com.eleven.hotel.application.query;

import com.eleven.core.infrastructure.jpa.Specifications;
import com.eleven.hotel.application.query.filter.RoomFilter;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomKey;
import com.eleven.hotel.domain.model.room.RoomRepository;
import com.eleven.hotel.domain.values.RoomBasic;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomQuery {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public Optional<Room> readRoom(RoomKey roomKey) {
        return roomRepository.findByRoomKey(roomKey).filter(HotelContext::mustReadable);
    }

    @Transactional(readOnly = true)
    public Collection<Room> listRoom(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public Page<Room> queryPage(RoomFilter filter, Pageable pageable) {
        var spec = getSpec(filter);
        return roomRepository.findAll(spec, pageable);
    }

    private static Specification<Room> getSpec(RoomFilter filter) {
        return Specifications.query(Room.class)
                .and(Objects.nonNull(filter.getHotelId()), Specs.hotelIdIs(filter.getHotelId()))
                .and(StringUtils.isNotBlank(filter.getPlanName()), Specs.nameLike(filter.getPlanName()))
                .getSpec();
    }

    @UtilityClass
    public class Specs {

        Specification<Room> nameLike(@Nullable String name) {
            return (root, query, builder) ->
                    builder.like(root.get(Room.Fields.basic).get(RoomBasic.Fields.name), "%" + name + "%");
        }

        Specification<Room> hotelIdIs(@Nullable Long hotelId) {
            return (root, query, builder) ->
                    builder.equal(root.get(Room.Fields.hotelId), hotelId);
        }
    }

}
