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
    private final InventoryRepository inventoryRepository;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }

    public void takeStock(Room room) {
        var inventories = inventoryRepository.findByRoomKey(room.toKey());
        var dates = room.getStock().getAvailableDates();

        for (Inventory inventory : inventories) {
            if (room.isApplicable(inventory)) {
                inventory.active();
            } else {
                inventory.inactive();
            }
            dates.remove(inventory.getKey().getDate());
        }

        dates.stream().map(room::createInventory).forEach(inventories::add);
        inventoryRepository.saveAllAndFlush(inventories);
    }
}
