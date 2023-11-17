package com.yachtrent.main.yacht;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.home.dto.FilterDto;
import com.yachtrent.main.yacht.dto.YachtDto;
import com.yachtrent.main.yacht.type.YachtTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class YachtService {
    private final YachtRepository yachtRepository;
    private final YachtTypeRepository yachtTypeRepository;
    private final YachtSpecification yachtSpecification;

    public void addYacht(YachtDto yachtDTO) {
        Yacht yacht = yachtDTO.toEntity(yachtDTO);
        yacht.setYachtType(yachtTypeRepository.findByType(yachtDTO.getType()).orElseThrow());
        yachtRepository.save(yacht);
        log.trace("Success create new yacht");
    }

    @Modifying
    @Transactional
    public void deleteYachtById(long id) {
        yachtRepository.deleteById(id);
        log.info("success delete yacht");
    }

    @Modifying
    @Transactional
    public void updateYacht(YachtDto yachtDTO) {
        Yacht yacht = yachtDTO.toEntity(yachtDTO);
        yacht.setYachtType(yachtTypeRepository.findByType(yachtDTO.getType()).orElseThrow());
        yachtRepository.save(yacht);
        log.info("success update yacht");
    }

    public Yacht findYachtById(long id) {
        return yachtRepository.findById(id).orElseThrow();
    }

    public boolean isYachtExists(String name) {
        return yachtRepository.findByName(name).isPresent();
    }

    public boolean isBelongsYachtNameProvideId(Long id, String name) {
        Yacht yacht = yachtRepository.findByName(name).orElseThrow();
        return yacht.getId().equals(id);
    }

    public Set<Yacht> getYachts(Account account) {
        return yachtRepository.findByAccount(account);
    }

    public List<YachtDto> findAllYachtDto() {
        return new YachtDto().toDtoList(yachtRepository.findAll());
    }

    public List<YachtDto> findYachtsByNameDto(String name) {
        return new YachtDto().toDtoList(yachtRepository.findYachtsByName(name));
    }

    public List<YachtDto> findAllYachtBySpecification(FilterDto filterDto) {
        Specification<Yacht> filter = yachtSpecification.setPriceRange(filterDto.getMin(), filterDto.getMax())
                .and(yachtSpecification.setNumberOfPeople(filterDto.getCapacity()))
                .and(yachtSpecification.setTypeYacht(filterDto.getYachtTypes()));
        return new YachtDto().toDtoList(yachtRepository.findAll(filter));
    }
}
