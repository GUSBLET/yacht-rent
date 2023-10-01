package com.yachtrent.main.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import com.yachtrent.main.order.services.OrderService;
import com.yachtrent.main.order.services.PriceCounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.Objects;

@Controller
@RequestMapping("/order/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final PriceCounterService priceCounterService;


    @GetMapping("create-order-page")
    public String pageCreateOrder(Model model) {
        model.addAttribute("title", "New order");
        model.addAttribute("content", "createOrder");
        CreateOrderDTO s = new CreateOrderDTO();
        s.getYacht().setId(1L);
        model.addAttribute("createOrderDTO", s);
        return "layout";
    }

    @PostMapping("create-order")
    public String createOrder(@Valid @ModelAttribute("createOrderViewModel")
                              CreateOrderDTO createOrderDTO,
                              BindingResult result,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "New order");
            model.addAttribute("content", "order/create-order-page");
            model.addAttribute("createOrderViewModel", new CreateOrderDTO());
            return "layout";
        }
        try {
            orderService.createNewOrder(createOrderDTO);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("title", "Success");
        model.addAttribute("text", "Success, confirm mail");
        return "account/success";
    }

    @GetMapping("count-price")
    public ResponseEntity<Object> countPrice(String dateOfStart, String dateOfFinish,
                                             String hourOfStart, String hourOfFinish,
                                             long yachtId)
            throws ParseException, JsonProcessingException {
        if (Objects.equals(dateOfFinish, "") || Objects.equals(dateOfStart, "") ||
                Objects.equals(hourOfStart, "") || Objects.equals(hourOfFinish, ""))
            return ResponseEntity.ofNullable("Enter all parameters of rent");

        float result = priceCounterService.countFullPrice(
                priceCounterService.convertToParametersToDate(
                        dateOfStart, hourOfStart),
                priceCounterService.convertToParametersToDate(dateOfFinish,
                        hourOfFinish), yachtId);

        if (result == -1.0)
            return ResponseEntity.ofNullable("Order cannot be more than 48 hours.");
        return ResponseEntity.ok(result);
    }
}
