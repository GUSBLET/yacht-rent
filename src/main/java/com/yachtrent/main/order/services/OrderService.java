package com.yachtrent.main.order.services;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountRepository;
import com.yachtrent.main.account.IAccountService;
import com.yachtrent.main.order.OrderStatus;
import com.yachtrent.main.order.dto.ControllingOrderTable;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import com.yachtrent.main.order.dto.EditingOrderStatusDTO;
import com.yachtrent.main.order.dto.OrderResultDTO;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleStatus;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final IAccountService accountService;
    private final PriceCounterService priceCounterService;


    public ResponseEntity<OrderResultDTO> createNewOrder(CreateOrderDTO model) throws ParseException {
        Date startTime = Date.from(priceCounterService.convertToParametersToDate(model.getDateOfStart(), model.getHourOfStart()).toInstant());
        Date finishTime = Date.from(priceCounterService.convertToParametersToDate(model.getDateOfFinish(), model.getHourOfFinish()).toInstant());

        if (orderRepository.findByTimeRange(startTime.toInstant(), finishTime.toInstant()).isEmpty()) {
            float price = priceCounterService.countFullPrice(startTime, finishTime, model.getYacht().getId());

            if (price == -1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new OrderResultDTO("Limit error. Your rent should be less than 48 hours", null));
            }

            Account result = accountService.signUpAnonymous(model).getBody();
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
