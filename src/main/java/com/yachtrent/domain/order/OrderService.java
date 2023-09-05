package com.yachtrent.domain.order;

import com.yachtrent.domain.dto.CreateOrderViewModel;
import com.yachtrent.domain.time.Timetable;
import com.yachtrent.domain.time.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TimetableRepository timetableRepository;
    private final PriceCounterService priceCounterService;

//    public OrderService(OrderRepository orderRepository,
//                        TimetableRepository timetableRepository,
//                        PriceCounterService priceCounterService){
//        this.orderRepository = orderRepository;
//        this.timetableRepository = timetableRepository;
//        this.priceCounterService = priceCounterService;
//    }

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

            Timetable newTimetable = timetableRepository.save(
                    Timetable.builder()
                            .startOfRent(startTime)
                            .finishOfRent(finishTime)
                            .build()
            );

            Order newOrder =
                    Order.builder()
                            .customerName(model.getCustomerName())
                            .customerEmail(model.getCustomerEmail())
                            .customerPhoneNumber(model.getCustomerPhoneNumber())
                            .price(price)
                            .build();

            newOrder.getTimetables().add(newTimetable);
            orderRepository.save(newOrder);

            return ResponseEntity.status(HttpStatus.OK).body("Success added");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This schedule is already taken");
    }
}
