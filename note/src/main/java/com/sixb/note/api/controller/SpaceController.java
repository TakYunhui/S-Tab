package com.sixb.note.api.controller;

import com.sixb.note.api.service.SpaceService;
import com.sixb.note.dto.space.JoinSpaceRequestDto;
import com.sixb.note.dto.space.SpaceRequestDto;
import com.sixb.note.dto.space.SpaceResponseDto;
import com.sixb.note.dto.space.UpdateSpaceTitleRequestDto;
import com.sixb.note.exception.NotFoundException;
import com.sixb.note.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space")
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    @GetMapping("/list")
    public ResponseEntity<List<SpaceResponseDto>> getAllSpaceDetails(long userId) {
        List<SpaceResponseDto> spaces = spaceService.findAllSpaceDetails(userId);
        return ResponseEntity.ok(spaces);
    }

    @PostMapping
    public ResponseEntity<SpaceResponseDto> createSpace(@RequestBody SpaceRequestDto requestDto, long userId) {
        SpaceResponseDto createdSpace = spaceService.createSpace(requestDto, userId);
        return new ResponseEntity<>(createdSpace, HttpStatus.CREATED);
    }

    @PatchMapping("/rename")
    public ResponseEntity<String> updateSpaceTitle(@RequestBody UpdateSpaceTitleRequestDto request) {
        try {
            spaceService.updateSpaceTitle(request.getSpaceId(), request.getNewTitle());
            return ResponseEntity.ok("스페이스 이름 수정 완료");
        } catch (NotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<String> deleteSpace(@PathVariable String spaceId) {
        boolean isUpdated = spaceService.deleteSpace(spaceId);
        if (isUpdated) {
            return ResponseEntity.ok("Space 삭제 완료");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinSpace(long userId, @RequestBody JoinSpaceRequestDto joinSpaceRequestDto) {
        spaceService.joinSpace(userId, joinSpaceRequestDto.getSpaceId());
        return ResponseEntity.ok("스페이스 참여 성공");
    }
}
