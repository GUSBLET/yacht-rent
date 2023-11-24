package com.yachtrent.main.yacht.services;


import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.order.OrderService;
import com.yachtrent.main.yacht.YachtRepository;
import com.yachtrent.main.yacht.YachtService;
import com.yachtrent.main.yacht.creator.services.CreatorService;
import com.yachtrent.main.yacht.facility.services.FacilityService;
import com.yachtrent.main.yacht.photo.services.YachtPhotoService;
import com.yachtrent.main.yacht.type.services.YachtTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class YachtServiceTest {
    @InjectMocks
    private YachtService yachtService;

    @Mock
    private YachtRepository yachtRepository;

    @Mock
    private YachtTypeService yachtTypeService;

    @Mock
    private AccountService accountService;

    @Mock
    private CreatorService creatorService;

    @Mock
    private OrderService orderService;

    @Mock
    private YachtPhotoService yachtPhotoService;

    @Mock
    private FacilityService facilityService;

//
//    @Test
//    public void testRemoveYachtOK() {
//        // Создайте объект RemoveYachtDTO для вашего теста
//        RemoveYachtDTO removeYachtDTO = RemoveYachtDTO.builder()
//                .id(1).build();
//        when(yachtRepository.findById(removeYachtDTO.getId())).thenReturn(Optional.of(Yacht.builder()
//                .id(1L).build()));
//        ResponseEntity<String> response = yachtService.removeYacht(removeYachtDTO.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        verify(yachtRepository).deleteById(removeYachtDTO.getId());
//    }
//
//    @Test
//    public void shouldOKWhenOrderExistByYacht() {
//        // Создайте объект RemoveYachtDTO для вашего теста
//        RemoveYachtDTO removeYachtDTO = RemoveYachtDTO.builder()
//                .id(1).build();
//        Set<Order> orderList = new HashSet<>();
//        Yacht s = Yacht.builder()
//                .id(1L).build();
//
//        for (int i = 0; i < 3; i++) {
//            Order order = Order.builder()
//                    .price(100.0f) // Установите цену по вашему усмотрению
//                    .status(OrderStatus.DONE.toString()) // Установите статус по вашему усмотрению
//                    .startOfRent(Instant.now()) // Установите дату начала аренды
//                    .finishOfRent(Instant.now().plusSeconds(3600)) // Установите дату окончания аренды
//                    .account(Account.builder().id(1L)
//                            .yachts(new HashSet<>()).build()) // Установите соответствующий объект Account
//                    .build();
//            order.setYachts(new HashSet<>());
//            // Добавьте Yacht (и другие связанные объекты) к заказу
//            order.getYachts().add(s);
//
//            orderList.add(order);
//        }
//
//        s.setOrders(orderList);
//
//
//        when(yachtRepository.findById(removeYachtDTO.getId())).thenReturn(Optional.of(s));
//
//        ResponseEntity<String> response = yachtService.removeYacht(removeYachtDTO.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//
//        verify(yachtRepository).deleteById(removeYachtDTO.getId());
//    }
//
//    @Test
//    public void shouldBadRequestWhenOneOfOrderActive() {
//
//        RemoveYachtDTO removeYachtDTO = RemoveYachtDTO.builder()
//                .id(1).build();
//        Set<Order> orderList = new HashSet<>();
//        Yacht s = Yacht.builder()
//                .id(1L).build();
//
//        for (int i = 0; i < 3; i++) {
//            Order order = Order.builder()
//                    .price(100.0f) // Установите цену по вашему усмотрению
//                    .status(OrderStatus.CONFIRMED.toString()) // Установите статус по вашему усмотрению
//                    .startOfRent(Instant.now()) // Установите дату начала аренды
//                    .finishOfRent(Instant.now().plusSeconds(3600)) // Установите дату окончания аренды
//                    .account(Account.builder().id(1L)
//                            .yachts(new HashSet<>()).build()) // Установите соответствующий объект Account
//                    .build();
//            order.setYachts(new HashSet<>());
//            // Добавьте Yacht (и другие связанные объекты) к заказу
//            order.getYachts().add(s);
//
//            orderList.add(order);
//        }
//
//        s.setOrders(orderList);
//
//        when(yachtRepository.findById(removeYachtDTO.getId())).thenReturn(Optional.of(s));
//
//        ResponseEntity<String> response = yachtService.removeYacht(removeYachtDTO.getId());
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
}
