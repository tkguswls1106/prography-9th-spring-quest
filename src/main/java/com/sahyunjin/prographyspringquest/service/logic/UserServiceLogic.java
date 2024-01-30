package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.dto.faker.FakerDataResponseDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.dto.user.PageResponseDto;
import com.sahyunjin.prographyspringquest.dto.user.UserResponseDto;
import com.sahyunjin.prographyspringquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final FakerServiceLogic fakerServiceLogic;


    @Transactional
    @Override
    public void initFakeUsers(FakerRequestDto fakerRequestDto) {

        // !!! 방 등등의 이외 초기화 코드는 차후 추가로 작성 예정. !!!
        // 먼저 초기화부터 실시.
        userJpaRepository.deleteAll();

        // Fake사용자 데이터 반환받음.
        FakerResponseDto fakerResponseDto = fakerServiceLogic.makeFakeUsers(fakerRequestDto);
        List<FakerDataResponseDto> fakerDataResponseDtos = fakerResponseDto.getData();

        // fakerId 기준으로 오름차순 정렬.
        fakerDataResponseDtos.sort(Comparator.comparing(FakerDataResponseDto::getId));

        // User로 DB에 저장. (벌크 insert)
        List<User> users = fakerDataResponseDtos.stream().map(FakerDataResponseDto::toEntity)
                .collect(Collectors.toList());
        userJpaRepository.saveAll(users);  // 한번에 벌크 insert 시킴. (저장되는 객체 하나라도 에러 추적 가능. 그리고 효율성 증가.)
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDto findUsers(Integer size, Integer page) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());  // id 기준으로 오름차순 정렬
        Page<User> users = userJpaRepository.findAll(pageable);
        Page<UserResponseDto> userResponseDtos = users.map(UserResponseDto::new);  // entity를 dto로 변환

        PageResponseDto pageResponseDto = new PageResponseDto(Long.valueOf(userResponseDtos.getTotalElements()).intValue(), userResponseDtos.getTotalPages(), userResponseDtos.getContent());

        return pageResponseDto;
    }
}
