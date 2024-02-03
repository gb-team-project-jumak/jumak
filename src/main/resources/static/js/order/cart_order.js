// 배송 주문 정보
let $deliveryBox = $('.delivery-box');


//배송지 기본정보
$deliveryBox.on('click',function (e){
   let idx = $deliveryBox.index(this);
   console.log(idx);

   for(let i=0;i<$deliveryBox.length;i++){
       if(i==idx){
           $deliveryBox.eq(i).addClass('checked');
       }else{
           $deliveryBox.eq(i).removeClass('checked');
       }
   }
});

$('.basic').on('click', function (e) {

    $.ajax({
        url : '/orders/delivery',
        type: 'get',
        dataType: 'json',
        success : function (resp){
            console.log(resp);
            $('#receiverName').val(resp.userName)
            $('#address').val(resp.userAddress)
            $('#zipcode').val(resp.userZipcode)
            $('#addressDetail').val(resp.userAddressDetail)
            $('#receiverPhone').val(resp.userPhoneNumber)
            $('#receiverCellPhone').val(resp.userCellphoneNumber)

        },
        error : function (xhr, status, err) {
            console.log(err);
        }
    });
});

$('.new').on('click', function (e) {
    $('#receiverName').val('');
    $('#address').val('');
    $('#zipcode').val('');
    $('#addressDetail').val('');
    $('#receiverPhone').val('');
    $('#receiverCellPhone').val('');

});



//직접입력
//배송지주소찾기
function searchAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addressDetail").focus();
        }
    }).open();
}

// 결제하기

var IMP = window.IMP;
IMP.init("imp74253861");

$("#order-btn").on("click", function () {

    var inputValue1 = document.getElementById('receiverName').value.trim();
    var inputValue2 = document.getElementById('zipcode').value.trim();
    var inputValue3 = document.getElementById('address').value.trim();
    var inputValue4 = document.getElementById('receiverPhone').value.trim();
    var inputValue5 = document.getElementById('receiverCellPhone').value.trim();
   var checkbox1Checked = document.getElementById('check-item1').checked;
   var checkbox2Checked = document.getElementById('termAgree_orderCheck').checked;
    if (inputValue1 !== '' && inputValue2 !== '' && inputValue3 !== '' && inputValue4 !== ''
        && inputValue5 !== '' && checkbox1Checked && checkbox2Checked ) {
        console.log('배송지가 모두 입력되었고, 체크표시도 됨');
        requestPay();
    } else if (inputValue1 !== '' && inputValue2 !== '' && inputValue3 !== '' && inputValue4 !== ''
        && inputValue5 !== ''){
        console.log('배송지가 입력되지 않음');
        var msg1 = '개인정보를 모두 동의해주세요';
        alert(msg1);
    }else if (checkbox1Checked && checkbox2Checked ) {
        console.log('모두 동의하지 않음');
        var msg2 = '배송지가 입력되지 않았습니다. ';
        alert(msg2);
    }else{
        console.log('암것도 안 함');
        var msg3 = '배송지를 입력해주시고, 개인정보를 모두 동의해주세요';
        alert(msg3);
    }


});

    function requestPay() {
        let productName =$('.product-info__detail').text();
        let buyerName = $('#receiverName').text();
        let buyerCellPhone = $('#orderCellPhoneNumber').val();
        let buyerEmail = $('#orderEmail').val();
        let address=$('#address').text();
        let addressDetail = $('#addressDetail').text();
        let zipcode=$('#zipcode').text();
        let totalPrice = parseInt($('.price_total').val());


    IMP.request_pay(
        {
            pg: "html5_inicis.INIBillTst",
            pay_method: "card",
            merchant_uid: "jumak_" + new Date().getTime(), // 주문번호
            name: productName,
            amount: totalPrice, // 숫자 타입
            buyer_email: buyerEmail,
            buyer_name: buyerName,
            buyer_tel: buyerCellPhone,
            buyer_addr: address + addressDetail,
            buyer_postcode: zipcode,
        },
        function (rsp) {
            // callback
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            console.log(rsp);
            if(rsp.success){
                paymentinfo(rsp);

            } else {
                window.location.href="/order/fail?price="+parseInt(totalPrice);
            }
        }
    );
}

    function paymentinfo(rsp) {
        let priceAmount= $('.pricebasic').text().replaceAll(',','');
        let discount = $('.total-goods-dc-price').text().replaceAll(',','');
        let paymentTotal = $('.price_total').val().replaceAll(',','');
        let orderRecipient = $('#receiverName').val();
        let orderCellphoneNumber = $('#orderCellPhoneNumber').val();
        let orderPhoneNumber = $('#orderPhoneNumber').val();
        let orderAddress=$('#address').val();
        let orderAddressDetail = $('#addressDetail').val();
        let orderZipcode=$('#zipcode').val();
        let totalPrice = $('.price_total').val();

        let postData = {
            paymentTotalAmount: parseInt(priceAmount),
            paymentTotalDiscount: parseInt(discount),
            paymentDeliveryFee:3000,
            paymentTotal: parseInt(paymentTotal),
            orderRecipient: orderRecipient,
            orderAddress: orderAddress,
            orderAddressDetail: orderAddressDetail,
            orderZipcode: parseInt(orderZipcode),
            orderCellphoneNumber:orderCellphoneNumber ,
            orderPhoneNumber:orderPhoneNumber,
            orderStatusNumber: 2,
        };


        console.log(postData);
        $.ajax({
        url:'/orders/payment',
        type: 'post',
        data: JSON.stringify(postData),
        contentType:'application/json; charset=utf-8',
        async : false,
        success:function (resp){
            console.log('성공!');
            location.href="/order/success?price="+parseInt(totalPrice);

        },
        error : function (xhr, status, err) {
            console.log(err);
            window.location.href="/order/fail?price="+parseInt(totalPrice);

        }

    });
}