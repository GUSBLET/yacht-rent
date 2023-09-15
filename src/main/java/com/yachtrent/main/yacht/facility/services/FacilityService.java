package com.yachtrent.main.yacht.facility.services;

import com.yachtrent.main.yacht.facility.dto.UpdatingFacilityDTO;
import com.yachtrent.main.yacht.facility.Facility;
import com.yachtrent.main.yacht.facility.FacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public List<Facility> getAllFacilityByYachtId(long id){
        return facilityRepository.findAllByYachtId(id);
    }

    public ResponseEntity<String> updateFacilityByYacht(List<UpdatingFacilityDTO> updatingFacilityDTOList){
        for (UpdatingFacilityDTO updatingFacilityDTO: updatingFacilityDTOList) {
            Facility facility = updatingFacilityDTO.toEntity(updatingFacilityDTO);
            facilityRepository.save(facility);
        }
        return ResponseEntity.ok().body("OK");
    }

}
