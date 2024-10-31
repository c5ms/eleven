package com.eleven.hotel.endpoint.web.hotel;

import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.RoomDto;
import com.eleven.hotel.api.endpoint.request.RoomCreateRequest;
import com.eleven.hotel.api.endpoint.request.RoomUpdateRequest;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.endpoint.configure.AsMerchantApi;
import com.eleven.hotel.endpoint.convert.RoomConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RoomConvertor roomConvertor;

    @Operation(summary = "list room")
    @GetMapping
    public List<RoomDto> listRoom(@PathVariable("hotelId") Integer hotelId) {
        return roomService.listRoom(hotelId)
            .stream()
            .map(roomConvertor::toDto)
            .collect(Collectors.toList());
    }

    @Operation(summary = "read room")
    @GetMapping("/{roomId}")
    public Optional<RoomDto> readRoom(@PathVariable("hotelId") Integer hotelId, @PathVariable("roomId") Integer roomId) {
        return roomService.readRoom(hotelId, roomId)
            .map(roomConvertor::toDto);
    }

    @Operation(summary = "create room")
    @PostMapping
    public RoomDto createRoom(@PathVariable("hotelId") Integer hotelId, @RequestBody @Validated RoomCreateRequest request) {
        var command = roomConvertor.toCommand(request);
        var room = roomService.createRoom(hotelId, command);
        return roomConvertor.toDto(room);
    }

    @Operation(summary = "update room")
    @PutMapping("/{roomId}")
    public RoomDto updateRoom(@PathVariable("hotelId") Integer hotelId,
                              @PathVariable("roomId") Integer roomId,
                              @RequestBody @Validated RoomUpdateRequest request) {
        var command = roomConvertor.toCommand(request);
        var room = roomService.updateRoom(hotelId, roomId, command);
        return roomConvertor.toDto(room);
    }

    @Operation(summary = "delete room")
    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable("hotelId") Integer hotelId, @PathVariable("roomId") Integer roomId) {
        roomService.deleteRoom(hotelId, roomId);
    }

}
