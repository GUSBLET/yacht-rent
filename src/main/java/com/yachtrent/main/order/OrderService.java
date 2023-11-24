package com.yachtrent.main.order;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.order.dto.OrderDto;
import com.yachtrent.main.yacht.YachtService;
import com.yachtrent.main.yacht.dto.YachtDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final AccountService accountService;
    private final YachtService yachtService;
    private final OrderRepository orderRepository;

    public void saveOrder(OrderDto dto) {
        Order order = dto.toEntity(dto);
        orderRepository.save(order);
        log.info("Successful order addition");
    }

    public OrderDto createOrderDto(long id) {
        Account account = accountService.getAuthentication();
        return account != null?
                OrderDto.builder()
                        .firstName(account.getName())
                        .lastName(account.getLastName())
                        .email(account.getEmail())
                        .phoneNumber(account.getPhoneNumber())
                        .yacht(new YachtDto().toDto(yachtService.findYachtById(id)))
                        .account(account)
                        .build() :
                OrderDto.builder()
                        .yacht(new YachtDto().toDto(yachtService.findYachtById(id)))
                        .build();
    }

    public boolean isYachtAccommodateNumberOfPeople(OrderDto dto) {
        YachtDto yacht = dto.getYacht();
        if(yacht != null) {
            int NumberOfPeople = Integer.parseInt(dto.getNumberOfPeople());
            return NumberOfPeople > yacht.getCapacity() || NumberOfPeople < yacht.getCapacity();
        }
        return false;
    }

    public boolean isTotalTimeMinusNumber(String startOfRent, String finishOfRent) {
        return getCountHour(startOfRent,finishOfRent)  < 1;
    }

    public float getSubtotal(OrderDto dto) {
        int countHour = getCountHour(dto.getStartOfRent(), dto.getFinishOfRent());
        return dto.getYacht().getPricePerHour() * countHour;
    }

    private int getCountHour(String startOfRent, String finishOfRent) {
        int start = LocalTime.parse(startOfRent).getHour();
        int finish = LocalTime.parse(finishOfRent).getHour();
        return finish - start;
    }

    public List<Order> getOrders(Account account) {
        return orderRepository.findByAccount(account);
    }

    public List<OrderDto> findAllOrder() {
        return new OrderDto().toDtoList(orderRepository.findAll());
    }

    public List<OrderDto> findOrdersByDateRange(String startDate, String endDate) {
        return new OrderDto().toDtoList(orderRepository.findByDate(
                LocalDate.parse(startDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                )
        );
    }

    public Slice<OrderDto> getPageOrders(List<OrderDto> orders, Pageable pageable) {
        List<OrderDto> pageList = orders.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, orders.size());
    }
}
