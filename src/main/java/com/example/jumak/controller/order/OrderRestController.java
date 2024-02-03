package com.example.jumak.controller.order;

import com.example.jumak.domain.vo.order.OrderDetailPayVo;
import com.example.jumak.domain.vo.order.OrderVo;
import com.example.jumak.domain.vo.order.PaymentVo;
import com.example.jumak.service.order.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {
    private final OrderService orderService;

//    배송지 입력
    @GetMapping("/delivery")
    public OrderVo orderDelivery(@SessionAttribute("userNumber") Long userNumber){
       return orderService.findByUNumber(userNumber);
    }

//    결제 정보
    @PostMapping("/payment")
    public void paymentOrder(@RequestBody OrderVo orderVo,
                             @SessionAttribute("userNumber") Long userNumber){
        orderVo.setUserNumber(userNumber);
        System.out.println(orderVo);
        orderService.paymentProcess(orderVo);

    }



}
