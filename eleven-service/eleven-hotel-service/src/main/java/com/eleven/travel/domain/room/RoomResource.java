package com.eleven.travel.domain.room;

import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.travel.domain.room.request.RoomCreateRequest;
import com.eleven.travel.domain.room.request.RoomUpdateRequest;
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
@AsRestApi
@RequiredArgsConstructor
@Tag(name = "room")
@RequestMapping("/hotels/{hotelId:[0-9]+}/rooms")
public class RoomResource {

    private final RoomFinder roomFinder;
    private final RoomService roomService;
    private final RoomConverter roomConverter;

    @Operation(summary = "list room")
    @GetMapping
    public List<RoomDto> listRoom(@PathVariable("hotelId") Long hotelId) {
        return roomFinder.listRoom(hotelId)
                .stream()
                .map(roomConverter::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "read room")
    @GetMapping("/{roomId:[0-9]+}")
    public Optional<RoomDto> readRoom(@PathVariable("hotelId") Long hotelId, @PathVariable("roomId") Long roomId) {
        var roomKey = RoomKey.of(hotelId, roomId);
        return roomFinder.readRoom(roomKey).map(roomConverter::toDto);
    }

    @Operation(summary = "create room")
    @PostMapping
    public RoomDto createRoom(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated RoomCreateRequest request) {
        var command = roomConverter.toCommand(request);
        var room = roomService.createRoom(hotelId, command);
        return roomConverter.toDto(room);
    }

    @Operation(summary = "update room")
    @PutMapping("/{roomId:[0-9]+}")
    public RoomDto updateRoom(@PathVariable("hotelId") Long hotelId,
                              @PathVariable("roomId") Long roomId,
                              @RequestBody @Validated RoomUpdateRequest request) {
        var command = roomConverter.toCommand(request);
        var roomKey = RoomKey.of(hotelId, roomId);
        var room = roomService.updateRoom(roomKey, command);
        return roomConverter.toDto(room);
    }

    @Operation(summary = "delete room")
    @DeleteMapping("/{roomId:[0-9]+}")
    public void deleteRoom(@PathVariable("hotelId") Long hotelId, @PathVariable("roomId") Long roomId) {
        var roomKey = RoomKey.of(hotelId, roomId);
        roomService.deleteRoom(roomKey);
    }

}
