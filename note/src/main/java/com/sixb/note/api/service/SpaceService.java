package com.sixb.note.api.service;

import com.sixb.note.dto.space.SpaceRequestDto;
import com.sixb.note.dto.space.SpaceResponseDto;
import com.sixb.note.entity.Space;
import com.sixb.note.entity.User;
import com.sixb.note.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpaceService {
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private UserService userService;

    public List<SpaceResponseDto> findAllSpaceDetails() {
        return spaceRepository.findAll().stream().map(space -> {
            log.info("Processing space: {}", space.getId());
            SpaceResponseDto dto = new SpaceResponseDto();
            dto.setSpaceId(space.getId());
            dto.setTitle(space.getTitle());
            dto.setIsPublic(space.getIsPublic());
            dto.setCreateAt(space.getCreatedAt());
            dto.setUpdateAt(space.getModifiedAt());

            List<User> usersInSpace = userService.findUsersBySpaceId(space.getId());
            List<SpaceResponseDto.UserResponse> userResponses = usersInSpace.stream().map(user -> {
                log.info("Adding user: {}", user.getNickname());
                SpaceResponseDto.UserResponse userResponse = new SpaceResponseDto.UserResponse();
                userResponse.setNickname(user.getNickname());
                userResponse.setProfileImg(user.getProfileImg());
                return userResponse;
            }).collect(Collectors.toList());

            dto.setUsers(userResponses);


            return dto;
        }).collect(Collectors.toList());
    }

    public SpaceResponseDto createSpace(SpaceRequestDto requestDto) {
        Space newSpace = new Space();
        newSpace.setTitle(requestDto.getTitle());
        newSpace.setIsPublic(true);
        LocalDateTime now = LocalDateTime.now();
        newSpace.setCreatedAt(now);
        newSpace.setModifiedAt(now);

        //보류 : 로그인 기능 구현 후 스페이스 생성 시 나 추가 해야 함

        Space savedSpace = spaceRepository.save(newSpace);

        return convertToSpaceResponseDto(savedSpace);
    }

    private SpaceResponseDto convertToSpaceResponseDto(Space space) {
        SpaceResponseDto responseDto = new SpaceResponseDto();
        responseDto.setSpaceId(space.getId());
        responseDto.setTitle(space.getTitle());
        responseDto.setIsPublic(true);
        responseDto.setRootFolderId(null);
        responseDto.setCreateAt(LocalDateTime.now());
        responseDto.setUpdateAt(null);
        responseDto.setUsers(new ArrayList<>());

        return responseDto;
    }
}