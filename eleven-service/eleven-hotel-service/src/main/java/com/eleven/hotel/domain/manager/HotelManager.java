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
        var inventoryBuilder = Inventory.builder()
                .roomKey(room.toKey())
                .stock(room.getStock().getQuantity());

        inventories.forEach(inventory -> dates.remove(inventory.getKey().getDate()));
        for (Inventory inventory : inventories) {
            if (room.isBookable(inventory)) {
                inventory.enable();
            } else {
                inventory.disable();
            }
        }

        dates.stream()
                .map(inventoryBuilder::date)
                .map(Inventory.InventoryBuilder::build)
                .forEach(inventories::add);
        inventoryRepository.saveAllAndFlush(inventories);
    }

}
