package com.motiveschina.hotel.features.product;

import java.util.Set;
import com.motiveschina.hotel.features.plan.Plan;
import com.motiveschina.hotel.features.room.RoomErrors;
import com.motiveschina.hotel.features.room.RoomKey;
import com.motiveschina.hotel.features.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductManager {

    private final RoomRepository roomRepository;
    private final ProductRepository productRepository;

    public void createProducts(Plan plan, Set<Long> rooms) {
        var products = productRepository.findByPlan(plan);

        for (Product product : products) {
            if (!rooms.contains(product.getKey().getRoomId())) {
                productRepository.delete(product);
            }
        }

        for (Long roomId : rooms) {
            var roomKey = RoomKey.of(plan.getHotelId(), roomId);
            var room = roomRepository.findByRoomKey(roomKey).orElseThrow(RoomErrors.ROOM_NOT_FOUND::toException);
            var product = Product.of(plan, room);
            productRepository.save(product);
        }
    }

}
