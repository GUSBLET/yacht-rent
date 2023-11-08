package com.yachtrent.main.yacht;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yacht")
public class YachtRestController {

    private final YachtService yachtService;
}
