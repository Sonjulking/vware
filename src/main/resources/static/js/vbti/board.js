// JavaScript code to handle the mouse click event
$(".profile a").mousedown(function (event) {
  if (event.which == 1) {
    //JS에서 event.keycode랑 같음 1번이 마우스 좌클임
    // Left mouse button
    // Get the mouse coordinates
    var mouseX = event.pageX;
    var mouseY = event.pageY;

    // Show the popup at the mouse coordinates
    $("#popup")
      .css({
        position: "absolute",
        left: mouseX,
        top: mouseY,
      })
      .fadeIn();
  }
});

// JavaScript code to hide the popup when the mouse is clicked outside the popup
$(document).mouseup(function (event) {
  if ($("#popup").is(":visible") && !$(event.target).closest("#popup").length) {
    $("#popup").hide();
  }
});

// 이 코드는 JavaScript/jQuery로 작성되었으며 "mouseup" 이벤트 리스너를 전체 문서 개체에 바인딩합니다.

// 사용자가 마우스 버튼을 놓으면 이벤트가 트리거되고 기능이 실행됩니다. 이 함수는 jQuery의 ".is()" 메서드를 사용하여 ID가 ​​"popup"인 요소가 현재 페이지에 표시되는지 여부를 확인합니다.

// 팝업이 표시되고 팝업 내에서 클릭 이벤트가 발생하지 않은 경우(즉, 클릭된 대상 요소가 팝업의 자식 요소가 아닌 경우) 함수는 jQuery ".hide()" 메서드를 사용하여 팝업을 숨깁니다.

// 요약하면 이 코드는 사용자가 팝업 요소 외부를 클릭할 때 팝업 요소를 숨깁니다.

//!$(event.target).closest("#popup").length
//=>event.target이벤트를 트리거한 요소를 나타냅니다.
//메서드 .closest()는 jQuery 개체에서 호출됩니다.
// 이 메서드는 현재 요소에서 DOM 트리 위로 이동하여 지정된 선택기와 일치하는 첫 번째 조상 요소를 반환합니다.
// 이 경우 선택자는 #popup"popup"이라는 ID를 가진 요소를 선택하는 입니다
//.length속성은 .closest() 의 결과로 액세스됩니다.jQuery 개체의 요소 수를 반환합니다.
//이 경우 이벤트 대상이 ID가 "popup"인 요소의 자손인 경우 길이는 0보다 큽니다(적어도 하나의 일치하는 요소가 있음을 의미).
//따라서 전반적으로 이 코드는 이벤트 대상이 ID가 "popup"인 요소의 자손인지 여부를 확인하고 있는 true경우 반환하고 그렇지 false않은 경우 반환합니다.

$("#popup").click(function () {
  // Hide the popup
  $("#popup").hide();
});

// $("#popup").on("click");
