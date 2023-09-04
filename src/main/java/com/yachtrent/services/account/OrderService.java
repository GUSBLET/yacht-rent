package com.yachtrent.services.account;

import com.yachtrent.databaselayer.repositories.OrderRepository;
import com.yachtrent.databaselayer.repositories.TimetableRepository;
import com.yachtrent.domain.entities.Order;
import com.yachtrent.domain.entities.Timetable;
import com.yachtrent.domain.view.models.order.CreateOrderViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final TimetableRepository timetableRepository;

    private final PriceCounterService priceCounterService;

    public OrderService(OrderRepository orderRepository,
                        TimetableRepository timetableRepository,
                        PriceCounterService priceCounterService){
        this.orderRepository = orderRepository;
        this.timetableRepository = timetableRepository;
        this.priceCounterService = priceCounterService;
    }

    public ResponseEntity<String> createNewOrder(CreateOrderViewModel model) throws ParseException {
        Date startTime = priceCounterService.convertToParametersToDate(model.getDateOfStart(), model.getHourOfStart());
        Date finishTime = priceCounterService.convertToParametersToDate(model.getDateOfFinish(), model.getHourOfFinish());

        if (timetableRepository.findByTimeRange(startTime, finishTime).isEmpty()) {
            float price = priceCounterService.countFullPrice(startTime, finishTime);

            if (price == -1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Limit error. Your rent should be less than 48 hours");
            }

            Timetable newTimetable = new Timetable();
            newTimetable.setStartOfRent(startTime);
            newTimetable.setFinishOfRent(finishTime);
            timetableRepository.save(newTimetable);

            Order newOrder = new Order();
            newOrder.setCustomerName(model.getCustomerName());
            newOrder.setCustomerEmail(model.getCustomerEmail());
            newOrder.setCustomerPhoneNumber(model.getCustomerPhoneNumber());
            newOrder.setPrice(price);
            newOrder.getTimetables().add(newTimetable);
            orderRepository.save(newOrder);

            return ResponseEntity.status(HttpStatus.OK).body("Success added");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This schedule is already taken");
    }




}
