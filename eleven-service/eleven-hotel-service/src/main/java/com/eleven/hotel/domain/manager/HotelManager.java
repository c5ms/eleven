package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelManager {
    private final List<RoomValidator> roomValidators;
    private final List<HotelValidator> hotelValidators;
    private final RoomInventoryRepository roomInventoryRepository;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }

    public void takeStock(Room room) {
        var inventories = roomInventoryRepository.findByRoomKey(room.toKey());
        var dates = room.getStock().getAvailableDates();
        var inventoryBuilder = RoomInventory.builder()
                .roomKey(room.toKey())
                .stock(room.getStock().getQuantity());

        inventories.forEach(roomInventory -> dates.remove(roomInventory.getKey().getDate()));
        for (RoomInventory roomInventory : inventories) {
            if (room.isBookable(roomInventory)) {
                roomInventory.enable();
            } else {
                roomInventory.disable();
            }
        }

        dates.stream()
                .map(inventoryBuilder::date)
                .map(RoomInventory.RoomInventoryBuilder::build)
                .forEach(inventories::add);
        roomInventoryRepository.saveAllAndFlush(inventories);
    }

}
