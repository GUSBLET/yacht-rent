package com.yachtrent.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yachtrent.domain.DTO.PriceCalculationRequestDTO;
import com.yachtrent.domain.view.models.account.SignInViewModel;
import com.yachtrent.domain.view.models.account.SignUpViewModel;
import com.yachtrent.domain.view.models.order.CreateOrderViewModel;
import com.yachtrent.services.account.OrderService;
import com.yachtrent.services.account.PriceCounterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/order/")
public class OrderController {
    private final OrderService orderService;
    private  final PriceCounterService priceCounterService;

    public OrderController(OrderService orderService,
                           PriceCounterService priceCounterService){
        this.priceCounterService = priceCounterService;
        this.orderService = orderService;
    }

    @GetMapping("create-order-page")
    public String pageCreateOrder(Model model){
        model.addAttribute("title", "New order");
        model.addAttribute("content", "order/create-order-page");
        model.addAttribute("createOrderViewModel", new CreateOrderViewModel());
        return "layout";
    }

    @PostMapping("create-order")
    public String createOrder(@Valid @ModelAttribute("createOrderViewModel")
                              CreateOrderViewModel createOrderViewModel,
                              BindingResult result,
                              Model model){
        if(result.hasErrors()){
            model.addAttribute("title", "New order");
            model.addAttribute("content", "order/create-order-page");
            model.addAttribute("createOrderViewModel", new CreateOrderViewModel());
            return "layout";
        }
        try {
            orderService.createNewOrder(createOrderViewModel);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("title", "Success");
        model.addAttribute("text", "Success, confirm mail");
        return "account/success";
    }

    @GetMapping("count-price")
    public ResponseEntity<Object> countPrice(String dateOfStart, String dateOfFinish,
                                             String hourOfStart, String hourOfFinish)
            throws ParseException, JsonProcessingException {
        if(Objects.equals(dateOfFinish, "") || Objects.equals(dateOfStart, "") ||
                Objects.equals(hourOfStart, "") || Objects.equals(hourOfFinish, ""))
            return ResponseEntity.ofNullable("Enter all parameters of rent");

        float result = priceCounterService.countFullPrice(
                priceCounterService.convertToParametersToDate(
                        dateOfStart, hourOfStart),
                priceCounterService.convertToParametersToDate(dateOfFinish,
                        hourOfFinish));

        if(result == -1.0)
            return ResponseEntity.ofNullable("Order cannot be more than 48 hours.");
         return ResponseEntity.ok(result);
    }

}
