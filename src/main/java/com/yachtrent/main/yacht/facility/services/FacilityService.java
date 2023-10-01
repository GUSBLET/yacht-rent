package com.yachtrent.main.yacht.facility.services;

import com.yachtrent.main.yacht.facility.dto.UpdatingFacilityDTO;
import com.yachtrent.main.yacht.facility.Facility;
import com.yachtrent.main.yacht.facility.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public List<Facility> getAllFacilityByYachtId(long id) {
        return facilityRepository.findAllByYachtId(id);
    }

    public ResponseEntity<String> updateFacilityByYacht(List<UpdatingFacilityDTO> updatingFacilityDTOList) {
        for (UpdatingFacilityDTO updatingFacilityDTO : updatingFacilityDTOList) {
            Facility facility = updatingFacilityDTO.toEntity(updatingFacilityDTO);
            facilityRepository.save(facility);
        }
        return ResponseEntity.ok().body("OK");
    }

    public ResponseEntity<String> updateFacility(UpdatingFacilityDTO dto) {
        Optional<Facility> facility = Optional.ofNullable(facilityRepository.findByNameAndCountAndYachtId(
                        dto.getName(),
                        dto.getCount(),
                        dto.getYacht().getId())
                .orElseGet(() -> {
                    return dto.toEntity(dto);
                }));

        if(facility.get().getId() == null){
            facilityRepository.save(facility.get());
            return ResponseEntity.ok().body("Facility added");
        }

        return null;
    }

    @Transactional
    public void removeAllFacilitiesByYachtId(long id) {
        facilityRepository.deleteAllByYachtId(id);
    }

}
