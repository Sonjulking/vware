$(document).ready(function (event) {
  // 'modal-open1' 클래스를 가진 모든 요소 찾기
  if (event.which == 1) {
    //JS에서 event.keycode랑 같음 1번이 마우스 좌클임
    var mouseX = event.pageX;
    var mouseY = event.pageY;

    $(".modal-open1").click(function (event) {
      // 'plz-login1' ID를 가진 요소의 표시 전환
      var content = $("#plz-login1");
      if (content.css("display") === "none") {
        content
          .css({
            display: "block",
            position: "absolute",
            left: mouseX,
            top: mouseY,
          })
          .fadeIn();
      } else {
        content.css("display", "none");
      }
    });
  }

  // 'modal-close1' ID를 가진 요소에 클릭 이벤트 리스너 추가
  $("#modal-close1").click(function () {
    console.log("클릭됌");
    $("#plz-login1").css("display", "none");
  });
});
