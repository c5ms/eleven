package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.application.model.RoomDto;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.RoomCreateRequest;
import com.eleven.hotel.api.endpoint.request.RoomUpdateRequest;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.values.Stock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.ROOM)
@RequestMapping(HotelEndpoints.Paths.ROOM)
public class RoomMerchantApi {

    private final RoomService roomService;
    private final HotelConvertor hotelConvertor;
    private final RoomRepository roomRepository;

    @Operation(summary = "list room")
    @GetMapping
    public List<RoomDto> listRoom(@PathVariable("hotelId") String hotelId) {
        var rooms = roomRepository.getRoomsByHotelId(hotelId);
        return rooms.stream().map(hotelConvertor.entities::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "read room")
    @GetMapping("/{roomId}")
    public Optional<RoomDto> readRoom(@Validated @PathVariable("hotelId") String hotelId, @PathVariable("roomId") String roomId) {
        return roomRepository.find(hotelId, roomId).map(hotelConvertor.entities::toDto);
    }

    @Operation(summary = "create room")
    @PostMapping
    public RoomDto createRoom(@PathVariable("hotelId") String hotelId, @RequestBody @Validated RoomCreateRequest request) {
        var command = RoomCreateCommand.builder()
            .hotelId(hotelId)
            .desc(request.getDesc())
            .size(request.getSize())
            .name(request.getName())
            .chargeType(request.getChargeType())
            .headPicUrl(request.getHeadPicUrl())
            .stock(Stock.of(request.getAmount()))
            .build();
        var room = roomService.createRoom(command);
        return hotelConvertor.entities.toDto(room);
    }

    @Operation(summary = "delete room")
    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable("hotelId") String hotelId, @PathVariable("roomId") String roomId) {
        var command = RoomDeleteCommand.builder()
            .hotelId(hotelId)
            .roomId(roomId)
            .build();
        roomService.deleteRoom(command);
    }

    @Operation(summary = "delete room")
    @PutMapping("/{roomId}")
    public Optional<RoomDto> updateRoom(@PathVariable("hotelId") String hotelId, @PathVariable("roomId") String roomId, @RequestBody @Validated RoomUpdateRequest request) {
        var command = RoomUpdateCommand.builder()
            .hotelId(hotelId)
            .roomId(roomId)
            .chargeType(request.getChargeType())
            .desc(request.getDesc())
            .size(request.getSize())
            .name(request.getName())
            .headPicUrl(request.getHeadPicUrl())
            .stock(Stock.of(request.getAmount()))
            .build();
        roomService.updateRoom(command);
        return readRoom(hotelId,roomId);
    }


}
