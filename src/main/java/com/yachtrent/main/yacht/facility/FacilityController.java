package com.yachtrent.main.yacht.facility;


import com.yachtrent.main.yacht.facility.dto.UpdatingFacilityDTO;
import com.yachtrent.main.yacht.facility.services.FacilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/facility")
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("updating-facility-page")
    public String updatindFacilityPage(Model model) {
        List<UpdatingFacilityDTO> updatingFacilityDTOList = new ArrayList<>();
        for (Facility facility : facilityService.getAllFacilityByYachtId(1L)) {
            UpdatingFacilityDTO updatingFacilityDTO = new UpdatingFacilityDTO();
            updatingFacilityDTOList.add(updatingFacilityDTO.toDto(facility));
        }

        model.addAttribute("title", "Udating facility");
        model.addAttribute("content", "facility/updating-facility-page");
        model.addAttribute("updatingFacilityDTOList", updatingFacilityDTOList);
        return "layout";
    }
}
