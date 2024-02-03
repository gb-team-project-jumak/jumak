
let cancelCount = $("input[name='7']").val();

let return1 = $("input[name='8']").val();
let return2 = $("input[name='9']").val();
let returnCount = parseInt(return1) + parseInt(return2);

let change1 = $("input[name='10']").val();
let change2 = $("input[name='11']").val();
let changeCount = parseInt(change1) + parseInt(change2);

$(".cancel_count").text(cancelCount);
$(".change_count").text(changeCount);
$(".return_count").text(returnCount)

// if (cancelCount = 0) {
//     $(".cancel_count").text('0');
// } else {
//     $(".cancel_count").text(cancelCount);
// }
//
// if (changeCount = 0) {
//     $(".change_count").text('0');
// } else {
//     $(".change_count").text(changeCount);
// }
//
// if (returnCount = 0) {
//     $(".return_count").text('0')
// } else {
//     $(".return_count").text(changeCount)
// }

