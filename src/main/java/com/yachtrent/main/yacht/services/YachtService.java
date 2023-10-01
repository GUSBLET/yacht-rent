package com.yachtrent.main.yacht.services;

import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderStatus;
import com.yachtrent.main.order.services.OrderService;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.YachtRepository;
import com.yachtrent.main.yacht.creator.services.CreatorService;
import com.yachtrent.main.yacht.dto.CreatingYachtDTO;
import com.yachtrent.main.yacht.dto.RemoveYachtDTO;
import com.yachtrent.main.yacht.facility.services.FacilityService;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.photo.services.YachtPhotoService;
import com.yachtrent.main.yacht.type.services.YachtTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class YachtService {

    private final YachtRepository yachtRepository;
    private final YachtTypeService yachtTypeService;
    private final AccountService accountService;
    private final CreatorService creatorService;
    private final OrderService orderService;
    private final YachtPhotoService yachtPhotoService;
    private final FacilityService facilityService;

    public ResponseEntity<String> addYacht(CreatingYachtDTO creatingYachtDTO) {
        if (yachtRepository.findByName(creatingYachtDTO.getName()).isEmpty()){
            Yacht yacht;
            List<YachtPhoto> yachtPhotoList = new ArrayList<>();

            yacht = creatingYachtDTO.toEntity(creatingYachtDTO);

            yacht.setYachtType(yachtTypeService.getType(creatingYachtDTO.getType()));
            yacht.setAccount(accountService.getAccount(creatingYachtDTO.getAccountId()));
            yacht.setCreator(creatorService.getByCreatorCompanyName(creatingYachtDTO.getCreator().getCompanyName()));

            for (YachtPhoto yachtPhoto : yacht.getPhotos()) {
                yachtPhotoList.add(yachtPhoto);
            }

            yacht = yachtRepository.save(yacht);

            for (YachtPhoto yachtPhoto : yachtPhotoList) {
                yachtPhoto.setYacht(yacht);
            }
            yachtPhotoService.saveAllYachtPhotoByYacht(yachtPhotoList);
            return ResponseEntity.ok().body("Yacht added");
        }

        return ResponseEntity.badRequest().body("Yacht with next name has already existed");
    }

    public ResponseEntity<String> removeYacht(long id){
        yachtPhotoService.removeAllPhotosByYachtId(id);
        facilityService.removeAllFacilitiesByYachtId(id);

        Optional<Yacht> yacht = yachtRepository.findYachtWithOrdersById(id);
        if(yacht.isPresent()){
            if (yacht.get().getOrders() == null || checkOrderIsNotActive(yacht.get().getOrders())){
                yachtRepository.deleteById(id);
                return ResponseEntity.ok().body("Removed");    
            }
            return ResponseEntity.badRequest().body("You have active orders, remove them");
        }

        return ResponseEntity.internalServerError().body("yacht does ot exist");
    }


    public Optional<Yacht> findYachtById(long id){
        return yachtRepository.findById(id);
    }







    private boolean checkOrderIsNotActive(Set<Order> orders){
        for (Order order: orders) {
            if(Objects.equals(order.getStatus(), OrderStatus.CONFIRMED.toString()))
                return false;
        }
        return true;
    }
}
