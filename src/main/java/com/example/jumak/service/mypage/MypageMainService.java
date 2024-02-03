package com.example.jumak.service.mypage;

import com.example.jumak.domain.dto.user.UserDto;
import com.example.jumak.domain.vo.myPage.InquiryVo;
import com.example.jumak.domain.vo.myPage.OrderDetailVo;
import com.example.jumak.domain.vo.myPage.OrderStatusVo;
import com.example.jumak.mapper.mypage.MypageMainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageMainService {
    private final MypageMainMapper mypageMainMapper;


    //회원 이름 조회
    public String findUserName(Long userNumber) {
        return mypageMainMapper.selectName(userNumber);
    }

//    회원 닉네임 조회
    public String findNickName(Long userNumber) {
        return mypageMainMapper.selectNickName(userNumber);
    }

//    보드 작성 개수 조회
    public Long findBoardCount(Long userNumber) {
        return mypageMainMapper.selectBoardCount(userNumber);
    }

//    댓글 수 조회
    public Long findReplyCount(Long userNumber) { return mypageMainMapper.selectReplyCount(userNumber);}

//    배송 상태 조회
    public List<OrderStatusVo> findOrderStatusCount(Long userNumber) {
        return mypageMainMapper.selectOrderStatusCount(userNumber);
    }

//    배송 취소 상태 조회
    public List<OrderStatusVo> findOrderCancelStatusCount(Long userNumber){
        List<OrderStatusVo> statusVoList = mypageMainMapper.selectOrderCancelStatusCount(userNumber);
        return statusVoList;
    }

//    최근 주문내역 리스트 조회
    public List<OrderDetailVo> findOrderDetail(Long userNumber) {
        List<OrderDetailVo> orderDetailList = mypageMainMapper.selectOrderDetail(userNumber);

        return orderDetailList;
    }


    public void register(UserDto userDto) {
        mypageMainMapper.insert(userDto);
    }


}
