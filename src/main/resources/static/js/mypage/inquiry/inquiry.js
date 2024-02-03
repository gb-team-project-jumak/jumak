$(function() {
    fn_default_datepicker();
});

function fn_default_datepicker()
{
    let start = $( "#datepicker_start" ).datepicker({
        dateFormat: 'yy-mm-dd' //Input Display Format 변경
        ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
        ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
        ,changeYear: true //콤보박스에서 년 선택 가능
        ,changeMonth: true //콤보박스에서 월 선택 가능
        ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
        ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
        ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
        ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
        ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
        ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
        ,minDate: "-3Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
        ,maxDate: "0M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
    });

    let end = $( "#datepicker_end" ).datepicker({
        dateFormat: 'yy-mm-dd' //Input Display Format 변경
        ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
        ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
        ,changeYear: true //콤보박스에서 년 선택 가능
        ,changeMonth: true //콤보박스에서 월 선택 가능
        ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
        ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
        ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
        ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
        ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
        ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
        ,minDate: "-3Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
        ,maxDate: "0M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
        ,defaultDate: "+1w"
    });

    //초기값을 오늘 날짜로 설정
    $('#datepicker_start').datepicker('setDate', '-7D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
    $('#datepicker_end').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
}


{
    $('.today').on('click', function (){
        $('#datepicker_start').datepicker('setDate', 'today');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

    $('.week').on('click', function (){
        $('#datepicker_start').datepicker('setDate', '-7D');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

    $('.half_month').on('click', function (){
        $('#datepicker_start').datepicker('setDate', '-15D');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

    $('.month').on('click', function (){
        $('#datepicker_start').datepicker('setDate', '-1M');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

    $('.three_month').on('click', function (){
        $('#datepicker_start').datepicker('setDate', '-3M');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

    $('.year').on('click', function (){
        $('#datepicker_start').datepicker('setDate', '-1Y');
        $('#datepicker_end').datepicker('setDate', 'today');
    })

}


$(document).ready(function() {
    $(".search_button").on("click", function() {
        let startDate = $("#datepicker_start").val();
        let endDate = $("#datepicker_end").val();
        console.log("click!!")

        $.ajax({
            type: "GET",
            url: "/mypages/inquiry",
            data: { startDate: startDate, endDate: endDate },
            success: function(response) {
                updateTable(response);
                console.log(response)
            },
            error: function(xhr, status, error) {
                console.error("Error during AJAX request:", error);
                console.log('에러다!!')
            }
        });
    });

    function updateTable(data) {
        let tbody = $("tbody");
        tbody.empty();

        $.each(data, function(index, inquiryVo) {
            let row = $("<tr>").addClass("table_data");
            row.append($("<td>").text(inquiryVo.qaDate));
            row.append($("<td>").text(inquiryVo.qaCategoryName));
            // row.append($("<td>").text(inquiryVo.qaTitle));
            row.append($("<td>").html(`<a href="/mypage/inquiry-view?qaNumber=${inquiryVo.qaNumber}" target="_blank">${inquiryVo.qaTitle}</a>`));
            row.append($("<td>").text(inquiryVo.qaReply));
            row.append($("<td>").text(inquiryVo.qaReplyDate));
            tbody.append(row);

            console.log(row)
        });
    }
});


$('.add_post_btn').on('click', function (){
    window.location.href = '/mypage/inquiry-detail';
})