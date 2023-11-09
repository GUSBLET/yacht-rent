package com.yachtrent.main.yacht;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderStatus;
import com.yachtrent.main.yacht.dto.YachtDTO;
import com.yachtrent.main.yacht.type.services.YachtTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class YachtService {
    private final YachtTypeService yachtTypeService;
    private final YachtRepository yachtRepository;

    public void addYacht(YachtDTO yachtDTO) {
        Yacht yacht = yachtDTO.toEntity(yachtDTO);
        yacht.setYachtType(yachtTypeService.findBYachtType(yachtDTO.getType()));
        yacht.setAccount(yachtDTO.getAccount());

//        yachtPhotoService.saveAllYachtPhotoByYacht(yacht.getPhotos());
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
    public void updateYacht(Yacht yacht) {
        if (isYachtExistById(yacht.getId())) {
            yachtRepository.save(yacht);
            log.info("success update yacht");
        }
    }

    public boolean isYachtExistById(long id) {
        return yachtRepository.findById(id).isPresent();
    }

    public Yacht findYachtById(long id) {
        return yachtRepository.findById(id).orElseThrow();
    }

    public boolean isYachtExists(String name) {
        return yachtRepository.findByName(name).isPresent();
    }

    //TODO методы надо перенести в другую абстракцю
    private boolean checkOrderIsNotActive(Set<Order> orders) {
        for (Order order : orders) {
            if (Objects.equals(order.getStatus(), OrderStatus.CONFIRMED.toString()))
                return false;
        }
        return true;
    }

    public Set<Yacht> getYachts(Account account) {
        return yachtRepository.findByAccount(account);
    }
}
