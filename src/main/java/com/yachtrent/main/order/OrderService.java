package com.yachtrent.main.order;

import com.yachtrent.main.dto.order.CreateOrderViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PriceCounterService priceCounterService;


    public ResponseEntity<String> createNewOrder(CreateOrderViewModel model) throws ParseException {
        Date startTime = priceCounterService.convertToParametersToDate(model.getDateOfStart(), model.getHourOfStart());
        Date finishTime = priceCounterService.convertToParametersToDate(model.getDateOfFinish(), model.getHourOfFinish());


//         timetableRepository.findByTimeRange(startTime, finishTime).isEmpty()
        if (true) {
            float price = priceCounterService.countFullPrice(startTime, finishTime);

            if (price == -1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Limit error. Your rent should be less than 48 hours");
            }


            Order newOrder =
                    Order.builder()
                            .price(price)
                            .build();


            orderRepository.save(newOrder);

            return ResponseEntity.status(HttpStatus.OK).body("Success added");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This schedule is already taken");
    }
}
