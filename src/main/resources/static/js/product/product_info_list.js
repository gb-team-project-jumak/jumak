let orderValue = $('.section-head__more').val();
let cateValue = $('input[name=shopCategory]:checked').val();

{
    let $checkedBtn = $('input[name=shopCategory]:checked');
    console.log($checkedBtn)

    $('label').removeClass('checked');
    $checkedBtn.closest('label').addClass('checked');
}



$('.section-head__more').on('change', function () {
    orderValue = $(this).val();

    window.location.href = `/product/view?orderCond=${orderValue}&cate=${cateValue}`;
})

$('input[name=shopCategory]').on('change', function () {
    let $label = $(this).closest('label')

    $('.category-box').removeClass('checked');
    $label.addClass('checked');

    cateValue = $(this).val();

    window.location.href = `/product/view?orderCond=${orderValue}&cate=${cateValue}`;
});
