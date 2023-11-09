package com.yachtrent.main.order.services;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderRepository;
import com.yachtrent.main.order.OrderStatus;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import com.yachtrent.main.order.dto.EditingOrderStatusDTO;
import com.yachtrent.main.order.dto.OrderResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final PriceCounterService priceCounterService;
    private final AccountService accountService;
    private final OrderRepository orderRepository;


    public ResponseEntity<OrderResultDTO> createNewOrder(CreateOrderDTO model) throws ParseException {
        Date startTime = Date.from(priceCounterService.convertToParametersToDate(model.getDateOfStart(), model.getHourOfStart()).toInstant());
        Date finishTime = Date.from(priceCounterService.convertToParametersToDate(model.getDateOfFinish(), model.getHourOfFinish()).toInstant());

        if (orderRepository.findByTimeRange(startTime.toInstant(), finishTime.toInstant()).isEmpty()) {
            float price = priceCounterService.countFullPrice(startTime, finishTime, model.getYacht().getId());

            if (price == -1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new OrderResultDTO("Limit error. Your rent should be less than 48 hours", null));
            }

            //тут надо глянуть на логику
            Account result = accountService.signUpAnonymous(model);
            if (result == null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new OrderResultDTO("International error", null));

            Order newOrder =
                    Order.builder()
                            .price(price)
                            .status(OrderStatus.NOT_CONFIRMED.toString())
                            .account(result)
                            .finishOfRent(finishTime.toInstant())
                            .startOfRent(startTime.toInstant())
                            .build();


            Order order = orderRepository.save(newOrder);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new OrderResultDTO("order added", order));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new OrderResultDTO("This schedule is already taken", null));
    }
//
    public ResponseEntity<String> editeOrderStatus(EditingOrderStatusDTO dto){
        Optional<Order> order = orderRepository.findById(dto.getId());
        if(order.isPresent()){
            order.get().setStatus(dto.getOrderStatus().toString());
            orderRepository.updateOrder(dto.getId(), order.get());
            return ResponseEntity.ok().body("Updated");
        }
        return ResponseEntity.internalServerError().body("Order does not exist");
    }

    // Pause.
    public ResponseEntity<String> confirmOrder(Order model) {
        model.setStatus(OrderStatus.CONFIRMED.toString());
        return null;
    }

    public Set<Order> find(long id){
        return orderRepository.findOrdersByYachtId(id);
    }
}
