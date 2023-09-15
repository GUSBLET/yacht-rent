package com.yachtrent.main.yacht.services;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.YachtRepository;
import com.yachtrent.main.yacht.creator.services.CreatorService;
import com.yachtrent.main.yacht.dto.CreatingYachtDTO;
import com.yachtrent.main.yacht.dto.RemoveYachtDTO;
import com.yachtrent.main.yacht.facility.FacilityRepository;
import com.yachtrent.main.yacht.facility.services.FacilityService;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.photo.YachtPhotoRepository;
import com.yachtrent.main.yacht.photo.service.YachtPhotoService;
import com.yachtrent.main.yacht.type.YachtType;
import com.yachtrent.main.yacht.type.services.YachtTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class YachtService {

    private final YachtRepository yachtRepository;
    private final YachtTypeService yachtTypeService;
    private final AccountService accountService;
    private final CreatorService creatorService;
    private final YachtPhotoService yachtPhotoService;
    private final FacilityService facilityService;

    public ResponseEntity<String> addYacht(CreatingYachtDTO creatingYachtDTO) {
        if (yachtRepository.findByName(creatingYachtDTO.getName()).isEmpty()){
            Yacht yacht;
            List<YachtPhoto> yachtPhotoList = new ArrayList<>();

            yacht = creatingYachtDTO.toEntity(creatingYachtDTO);

            yacht.setYachtType(yachtTypeService.getType(creatingYachtDTO.getType()));
            yacht.setAccount(accountService.getAccount(creatingYachtDTO.getAccountId()));
            yacht.setCreator(creatorService.getCreatorBComapanyName(creatingYachtDTO.getCreator().getСompanyName()));

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

    public void removeYacht(RemoveYachtDTO removeYachtDTO){
        yachtPhotoService.removeAllPhotosByYachtId(removeYachtDTO.getId());
        facilityService.removeAllFacilitiesByYachtId(removeYachtDTO.getId());
        yachtRepository.deleteById(removeYachtDTO.getId());
    }
}
