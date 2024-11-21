package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.inventory.RoomInventory;
import com.eleven.hotel.domain.model.inventory.RoomInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {

    private final RoomInventoryRepository roomInventoryRepository;

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
