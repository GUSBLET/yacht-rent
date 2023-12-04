package com.yachtrent.main.order;

import com.yachtrent.main.order.dto.OrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@ToString
public class OrderController {
    private final OrderService orderService;
    private OrderDto order;

    @GetMapping("/{yachtId}")
    public String getPageOrder(@PathVariable("yachtId") long yachtId,
                               Model model
    ) {
        order = orderService.createOrderDto(yachtId);
        model.addAttribute("order", order);
        return "order/create-order-page";
    }

    @PostMapping("/create-order")
    public String decorOrder(@Valid @ModelAttribute("order") OrderDto orderDto,
                             BindingResult bindingResult,
                             Model model
    ) {
        orderDto.setYacht(order.getYacht());
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "order/create-order-page";
        }
        if (orderService.isYachtAccommodateNumberOfPeople(orderDto)) {
            String message = "The number of people should not be less than zero or exceed "
                    + orderDto.getYacht().getCapacity() +
                    " people on this vessel";
            model.addAttribute("numberOfPeopleError", true)
                    .addAttribute("message_numberOfPeopleError", message);
            log.error("Incorrect numberOfPeopleError");
            return "order/create-order-page";
        }
        if (orderService.isTotalTimeMinusNumber(orderDto.getStartOfRent(), orderDto.getFinishOfRent())) {
            String message = "Rental time must exceed at least an hour";
            model.addAttribute("message_incorrect_time", message);
            log.error("Incorrect time");
            return "order/create-order-page";
        }
        if (orderDto.getDate() == null) {
            model.addAttribute("message_date", "Choose date");
            log.error("Incorrect date");
            return "order/create-order-page";
        }

        order.setNumberOfPeople(orderDto.getNumberOfPeople());
        order.setStartOfRent(orderDto.getStartOfRent());
        order.setFinishOfRent(orderDto.getFinishOfRent());
        order.setDate(orderDto.getDate());
        order.setSubtotal(orderService.getSubtotal(orderDto));
        model.addAttribute("viewing_order", order);
        return "order/client_order";
    }

    @PostMapping("/your_order")
    public String createOrder(@ModelAttribute("paymentMethod") String paymentMethod) {
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.CONFIRMED);
        orderService.saveOrder(order);
        return "redirect:/home";
    }
}
