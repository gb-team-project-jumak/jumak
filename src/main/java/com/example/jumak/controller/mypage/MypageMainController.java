package com.example.jumak.controller.mypage;

import com.example.jumak.domain.dto.qa.QaDto;
import com.example.jumak.domain.dto.user.UserDto;
import com.example.jumak.domain.vo.myPage.InquiryVo;
import com.example.jumak.domain.vo.myPage.MemberVo;
import com.example.jumak.domain.vo.myPage.OrderDetailVo;
import com.example.jumak.domain.vo.myPage.OrderStatusVo;
import com.example.jumak.service.mypage.InquiryService;
import com.example.jumak.service.mypage.MemberService;
import com.example.jumak.service.mypage.MypageMainService;
import com.example.jumak.service.mypage.ShippingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageMainController {
    private final MypageMainService mypageMainService;
    private final ShippingService shippingService;

    private final InquiryService inquiryService;
    private final MemberService memberService;


//    메인
    @GetMapping("/main")
    public String main(Model model, HttpServletRequest req) {
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

//       회원 이름 조회
        String userName = mypageMainService.findUserName(userNumber);

//        작성 게시물 조회
        Long boardCount = mypageMainService.findBoardCount(userNumber);

//        작성 댓글 조회
        Long replyCount = mypageMainService.findReplyCount(userNumber);

//        배송 상태 조회
        List<OrderStatusVo> statusList = mypageMainService.findOrderStatusCount(userNumber);

//        배송 취소/교환/반품 상태 조회
        List<OrderStatusVo> cancelList = mypageMainService.findOrderCancelStatusCount(userNumber);

//        최근 주문내역 리스트 조회
        List<OrderDetailVo> orderDetailList = mypageMainService.findOrderDetail(userNumber);

        model.addAttribute("userName", userName);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("replyCount", replyCount);
        model.addAttribute("statusList", statusList);
        model.addAttribute("cancelList", cancelList);
        model.addAttribute("orderDetailList", orderDetailList);

        return "mypage/main/main";
    }

//    주문목록/배송조회
    @GetMapping("/search-shipping")
    public String searchShipping(Model model, HttpServletRequest req, OrderDetailVo orderDetailVo) {
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

//       회원 이름 조회
        String userName = mypageMainService.findUserName(userNumber);

//        작성 게시물 조회
        Long boardCount = mypageMainService.findBoardCount(userNumber);

//        작성 댓글 조회
        Long replyCount = mypageMainService.findReplyCount(userNumber);

//        기간별 주문/배송 리스트 조회
        orderDetailVo.setUserNumber(userNumber);
//        List<OrderDetailVo> orderDetailList = searchShippingService.findOrderDetail(orderDetailVo);


        model.addAttribute("userName", userName);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("replyCount", replyCount);
//        model.addAttribute("orderDetailList", orderDetailList);
        return "mypage/search_shipping/search_shipping";
    }


//    주문 취소 페이지
    @GetMapping("/cancel-shipping")
    public String cancelShipping(Model model, HttpServletRequest req) {
                Long userNumber = (Long)req.getSession().getAttribute("userNumber");

//       회원 이름 조회
        String userName = mypageMainService.findUserName(userNumber);

//        작성 게시물 조회
        Long boardCount = mypageMainService.findBoardCount(userNumber);

//        작성 댓글 조회
        Long replyCount = mypageMainService.findReplyCount(userNumber);

        OrderDetailVo orderDetailVo = new OrderDetailVo();

//        기간별 주문/배송 리스트 조회
        orderDetailVo.setUserNumber(userNumber);
//        List<OrderDetailVo> orderDetailList = searchShippingService.findOrderDetail(orderDetailVo);


        model.addAttribute("userName", userName);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("replyCount", replyCount);

        return "mypage/cancel_shipping/cancel_shipping";
    }

//    환불
    @GetMapping("/refund-shipping")
    public String refundShipping(Model model, HttpServletRequest req) {
                Long userNumber = (Long)req.getSession().getAttribute("userNumber");

//       회원 이름 조회
        String userName = mypageMainService.findUserName(userNumber);

//        작성 게시물 조회
        Long boardCount = mypageMainService.findBoardCount(userNumber);

//        작성 댓글 조회
        Long replyCount = mypageMainService.findReplyCount(userNumber);

        OrderDetailVo orderDetailVo = new OrderDetailVo();

//        기간별 주문/배송 리스트 조회
        orderDetailVo.setUserNumber(userNumber);
//        List<OrderDetailVo> orderDetailList = searchShippingService.findOrderDetail(orderDetailVo);


        model.addAttribute("userName", userName);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("replyCount", replyCount);
        return "mypage/refund_shipping/refund_shipping";
    }

//    1대1 문의
    @GetMapping("/inquiry")
    public String inquiry (Model model, HttpServletRequest req)
    {
        Long userNumber = 61L;
//                Long userNumber = (Long)req.getSession().getAttribute("userNumber");

//       회원 이름 조회
        String userName = mypageMainService.findUserName(userNumber);

//        작성 게시물 조회
        Long boardCount = mypageMainService.findBoardCount(userNumber);

//        작성 댓글 조회
        Long replyCount = mypageMainService.findReplyCount(userNumber);

        OrderDetailVo orderDetailVo = new OrderDetailVo();

//        1:1문의내역 조회
        orderDetailVo.setUserNumber(userNumber);
//        List<OrderDetailVo> orderDetailList = searchShippingService.findOrderDetail(orderDetailVo);


        model.addAttribute("userName", userName);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("replyCount", replyCount);
        return "mypage/inquiry/inquiry";
    }

//    1대1문의 작성
    @GetMapping("/inquiry-detail")
    public String inquiryDetail(@SessionAttribute(value = "userNumber", required = false) Long userNumber,
                                Model model) {
        String nickName = mypageMainService.findNickName(userNumber);

        model.addAttribute("nickName", nickName);

        return userNumber == null ? "user/login/login" : "mypage/inquiry_detail/inquiry_detail";
    }

    @PostMapping("/inquiry-detail")
    public RedirectView inquiryDetail(QaDto qaDto,
                                      @SessionAttribute("userNumber") Long userNumber,
                                      RedirectAttributes redirectAttributes) {
        qaDto.setUserNumber(userNumber);
    inquiryService.register(qaDto);
    Long qaNumber = qaDto.getQaNumber();


        redirectAttributes.addFlashAttribute("qaNumber", qaNumber);

    return new RedirectView("/mypage/inquiry");
    }

//    게시물 보기
    @GetMapping("/inquiry-view")
    public String inquiryView(Model model, HttpServletRequest req, Long qaNumber) {
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

        log.info("qaNumber : {}", qaNumber);

        QaDto qaDto = new QaDto();
        qaDto.setQaNumber(qaNumber);
        qaDto.setUserNumber(userNumber);

//        닉네임 가져오기
        String userNickName = mypageMainService.findNickName(userNumber);

        QaDto inquiryDetail = inquiryService.findInquiryDetail(qaDto);
        String qaDate = inquiryDetail.getQaDate();
        String qaContent = inquiryDetail.getQaContent();
        String qaTitle = inquiryDetail.getQaTitle();
        String qaReply = inquiryDetail.getQaReply();
        String qaReplyDate = inquiryDetail.getQaReplyDate();


        model.addAttribute("userNickName", userNickName);
        model.addAttribute("qaDate", qaDate);
        model.addAttribute("qaTitle", qaTitle);
        model.addAttribute("qaContent", qaContent);
        model.addAttribute("qaReply", qaReply);
        model.addAttribute("qaReplyDate", qaReplyDate);


        return "mypage/inquiry_detail/inquiry_view";
    }

//    회원정보
    @GetMapping("/member-info")
    public String memberInfo(Model model, HttpServletRequest req) {
      Long userNumber = (Long)req.getSession().getAttribute("userNumber");



//        정보 가져오기
        UserDto memberDetail = memberService.findMemeber(userNumber);

        model.addAttribute("member", memberDetail);


        return "mypage/member_info/member_info";
    }

    @PostMapping("/member-info")
    public RedirectView memberInfo(MemberVo memberVo, HttpServletRequest req) {
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

        memberVo.setUserNumber(userNumber);

        memberService.modifyMember(memberVo);

        return new RedirectView("/mypage/member-info");
    }

//    회원탈퇴
    @GetMapping("/member-delete")
    public String memberDelete() {
        return "mypage/member_delete/member_delete";
    }

    @PostMapping("/member-delete")
    public RedirectView memberDelete(Model model, HttpServletRequest req,
                                     MemberVo memberVo) {
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

        memberVo.setUserNumber(userNumber);

        memberService.removeMember(memberVo);

        return new RedirectView("/mypage/member-delete");
    }

//    배송지 관리
    @GetMapping("/shopping-address")
    public String shoppingAddress() {
        return "mypage/shopping_address/shopping_address";
    }

//    작성글확인
    @GetMapping("/manage-comment")
    public String manageComment() {
        return "mypage/manage_comment/manage_comment";
    }

}
