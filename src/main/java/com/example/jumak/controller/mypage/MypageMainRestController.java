package com.example.jumak.controller.mypage;

import com.example.jumak.domain.vo.myPage.InquiryVo;
import com.example.jumak.domain.vo.myPage.MemberVo;
import com.example.jumak.domain.vo.myPage.OrderDetailVo;
import com.example.jumak.service.mypage.InquiryService;
import com.example.jumak.service.mypage.MemberService;
import com.example.jumak.service.mypage.ShippingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypages")
public class MypageMainRestController {

    private final ShippingService shippingService;
    private final InquiryService inquiryService;
    private final MemberService memberService;


    //    주문내역 배송상태 조회
    @GetMapping("/search-shipping")
    public List<OrderDetailVo> searchShipping(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest req) {
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setStartDate(startDate);
        orderDetailVo.setEndDate(endDate);
        orderDetailVo.setUserNumber(userNumber);

        return shippingService.findSearchDetail(orderDetailVo);
    }

    //    취소/반품/교환상태 조회
    @GetMapping("/cancel-shipping")
    public List<OrderDetailVo> cancelShipping(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest req) {
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setStartDate(startDate);
        orderDetailVo.setEndDate(endDate);
        orderDetailVo.setUserNumber(userNumber);

        return shippingService.findCancelDetail(orderDetailVo);
    }



//     환불/입금상태 조회
    @GetMapping("/refund-shipping")
    public List<OrderDetailVo> refundShipping(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest req) {
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");


        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setStartDate(startDate);
        orderDetailVo.setEndDate(endDate);
        orderDetailVo.setUserNumber(userNumber);

        return shippingService.findRefundDetail(orderDetailVo);
    }


//    1대1 문의 내역 조회
    @GetMapping("/inquiry")
    public List<InquiryVo> Inquiry(@RequestParam String startDate,
                                   @RequestParam String endDate,
                                   HttpServletRequest req) {
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");

        InquiryVo inquiryVo = new InquiryVo();
        inquiryVo.setStartDate(startDate);
        inquiryVo.setEndDate(endDate);
        inquiryVo.setUserNumber(userNumber);

        return inquiryService.findInquiry(inquiryVo);
    }

    @GetMapping("/member-info")
    public Long enterMember(HttpServletRequest req, @RequestParam String userPassword) {
        Long userNumber = (Long) req.getSession().getAttribute("userNumber");

        MemberVo memberVo = new MemberVo();
        memberVo.setUserNumber(userNumber);
        memberVo.setUserPassword(userPassword);




        return memberService.enterPassword(memberVo);
    }
}
