package com.yachtrent.main.harbor;

import com.yachtrent.main.harbor.dto.UpdatingHarborDTO;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.YachtRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class HarborService {
    public final HarborRepository harborRepository;
    private final YachtRepository yachtRepository;

    public ResponseEntity<String> updateHarbor(List<UpdatingHarborDTO> updatingHarborDTOList){
        Optional<Yacht> yacht = yachtRepository.findById(updatingHarborDTOList.get(0).getYachtId());
        if(yacht.isPresent()){
            Set<Yacht> list = new HashSet<>();
            list.add(yacht.get());
            for (UpdatingHarborDTO updatingHarborDTO: updatingHarborDTOList) {
                Harbor harbor = Harbor.builder()
                        .yachts(list)
                        .longitude(updatingHarborDTO.getLongitude())
                        .latitude(updatingHarborDTO.getLatitude())
                        .city(updatingHarborDTO.getCity())
                        .address(updatingHarborDTO.getAddress())
                        .name(updatingHarborDTO.getName())
                        .build();
                harborRepository.save(harbor);
            }
            return ResponseEntity.ok().body("Ok");
        }

        return ResponseEntity.badRequest().body("Yacht does not exist");
    }
}
