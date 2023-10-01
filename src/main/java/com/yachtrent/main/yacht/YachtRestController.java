package com.yachtrent.main.yacht;

import com.yachtrent.main.yacht.services.YachtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yacht")
public class YachtRestController {

    private final YachtService yachtService;
}
