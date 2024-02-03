let productNumber = $('.product-num').val();
let page = 1;

// 옵션 숫자 계산
{
    let priceText = $('#result-price').text();
    let price = +priceText.replaceAll(',', '');
    let $numBox = $('.numBox');

    let $displayPrice01 = $('.option_price_display_0');
    let $displayPrice02 = $('.total_price--display');

    $('.plus').on('click', function () {
        let count = $numBox.val();

        $numBox.val(++count);
        let totalPrice = count * price;
        $displayPrice01.text(totalPrice.toLocaleString());
        $displayPrice02.text(totalPrice.toLocaleString());
    });

    $('.minus').on('click', function () {
        let count = $numBox.val();
        if(count < 2) { return; }
        $numBox.val(--count);
        let totalPrice = count * price;
        $displayPrice01.text(totalPrice.toLocaleString());
        $displayPrice02.text(totalPrice.toLocaleString());
    });
}


// 장바구니 바로 가기
$('.btn_add_cart').on('click', function (){
    $('#product-form').attr('action', '/order/cart');
    if($(this).data('num')){
        $('#product-form').submit();
    }else{
        alert('로그인 하세요');
        location.href = "/user/login";
    }
})




// 바로구매 바로 가기
// 유저넘버를 갖고 있으면, 전체 form 데이터를 submit으로 보내줌
// 아니면 로그인 페이지로 이동
$('.btn_add_order').on('click', function (){
    $('#product-form').attr('action', '/order/next');
    if($(this).data('num')){
        $('#product-form').submit();
    }else{
        alert('로그인 하세요');
        location.href = "/user/login";
    }
})












