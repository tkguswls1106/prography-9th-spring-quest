package com.sahyunjin.prographyspringquest.service.logic;

import com.sahyunjin.prographyspringquest.domain.user.User;
import com.sahyunjin.prographyspringquest.domain.user.UserJpaRepository;
import com.sahyunjin.prographyspringquest.dto.faker.FakerDataResponseDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerRequestDto;
import com.sahyunjin.prographyspringquest.dto.faker.FakerResponseDto;
import com.sahyunjin.prographyspringquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final FakerServiceLogic fakerServiceLogic;


    @Transactional
    @Override
    public void initFakeUsers(FakerRequestDto fakerRequestDto) {

        // 초기화 코드는 차후 작성 예정.

        // Fake사용자 데이터 반환받음.
        FakerResponseDto fakerResponseDto = fakerServiceLogic.makeFakeUsers(fakerRequestDto);
        List<FakerDataResponseDto> fakerDataResponseDtos = fakerResponseDto.getData();

        // fakerId 기준으로 오름차순 정렬.
        fakerDataResponseDtos.sort(Comparator.comparing(FakerDataResponseDto::getId));

        // User로 DB에 저장.
        for(int i=0; i<fakerDataResponseDtos.size(); i++) {
            User user = fakerDataResponseDtos.get(i).toEntity();
            userJpaRepository.save(user);
        }
    }
}
