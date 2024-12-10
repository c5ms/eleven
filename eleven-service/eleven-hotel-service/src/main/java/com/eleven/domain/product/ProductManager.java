package com.eleven.domain.product;

import com.eleven.domain.hotel.HotelErrors;
import com.eleven.domain.plan.Plan;
import com.eleven.domain.room.RoomKey;
import com.eleven.domain.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

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
            var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException);
            var product = Product.of(plan, room);
            productRepository.save(product);
        }
    }

}
