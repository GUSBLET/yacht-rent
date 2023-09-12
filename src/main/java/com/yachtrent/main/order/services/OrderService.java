package com.yachtrent.main.order.services;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountRepository;
import com.yachtrent.main.account.IAccountService;
import com.yachtrent.main.dto.order.ControllingOrderTable;
import com.yachtrent.main.dto.order.CreateOrderDTO;
import com.yachtrent.main.dto.order.OrderResultDTO;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final IAccountService accountService;
    private final PriceCounterService priceCounterService;


    public ResponseEntity<OrderResultDTO> createNewOrder(CreateOrderDTO model) throws ParseException {
        Date startTime = priceCounterService.convertToParametersToDate(model.getDateOfStart(), model.getHourOfStart());
        Date finishTime = priceCounterService.convertToParametersToDate(model.getDateOfFinish(), model.getHourOfFinish());


        //TODO доделать
        if (true) {
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
                            .confirmedOrder(false)
                            .account(result)
                            .finishOfRent(finishTime)
                            .startOfRent(startTime)
                            .build();


            Order order = orderRepository.save(newOrder);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new OrderResultDTO("order added", order));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new OrderResultDTO("This schedule is already taken", null));
    }

    // Pause.
    public ResponseEntity<String> confirmOrder(Order model) {
        model.setConfirmedOrder(true);
        return null;
    }

    // Pause.
   /*
   * не совсем понимаю что ты тут хочешь возвращать но вот корявий пример кода
   *
   *   return accountRepository.findById(id)
               .map(account -> null)
               .orElseGet(() -> new IllegalArgumentException("account not found"));
   * */
    public ResponseEntity<List<ControllingOrderTable>> createControllingOrderTable(long id) {

        Optional<Account> account = accountRepository.findById(id);
        if(account.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return null;
    }
}
