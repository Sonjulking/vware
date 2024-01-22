let preventDb = document.querySelector('.dbPrevent');
preventDb.addEventListener('click',
    function (event) {
      // 클릭 이벤트 비활성화
      preventDb.disabled = true;
      // 특정 시간 후에 다시 활성화
      setTimeout(function () {
        preventDb.disabled = false;
      }, 2000);  // 2000 밀리초 (2초) 후에 활성화
    });