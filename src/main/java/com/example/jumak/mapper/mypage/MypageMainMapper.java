package com.example.jumak.mapper.mypage;

import com.example.jumak.domain.dto.user.UserDto;
import com.example.jumak.domain.vo.myPage.InquiryVo;
import com.example.jumak.domain.vo.myPage.OrderDetailVo;
import com.example.jumak.domain.vo.myPage.OrderStatusVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MypageMainMapper {
    //회원 이름 조회
    String selectName(Long userNumber);

//    회원 닉네임 조회
    String selectNickName(Long userNumber);

//    작성글 개수 조회
    Long selectBoardCount(Long userNumber);

//    작성 댓글 조회
    Long selectReplyCount(Long userNumber);

//    배송 상태 조회
    List<OrderStatusVo> selectOrderStatusCount(Long userNumber);

//    배송 상태 취소/교환/반품 조회
    List<OrderStatusVo> selectOrderCancelStatusCount(Long userNumber);


//    최근 주문내역 리스트 조회
    List<OrderDetailVo> selectOrderDetail(Long userNumber);

    void insert(UserDto userDto);
}
