const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");

const endPoint = 10; //질문이 12개라서
const select = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0]; // 어떠한 답변을 했는지 배열 생성, 사용자가 버튼을 선택할때마다 배열에 내용을 추가 //현재 23개 0 ~ 22

function calResult() {
  //결과를 연산해주는 함수
  console.log("calResult" + select);
  let result = select.indexOf(Math.max(...select)); // indexOf는 인덱스값을 반환하고, max는 가장큰 값, ...은 전개구문이라고 해줌
  console.log("리절트" + result);
  return result;
}

function setResult() {
  let point = calResult();
  const resultName = document.querySelector(".resultname");
  resultName.innerHTML = infoList[point].name;

  let resultImg = document.createElement("img");
  const imgDiv = document.querySelector("#resultImg");
  let imgURL = "/assets/vbti/agent/agent-" + point + ".png";
  resultImg.src = imgURL;
  resultImg.alt = point;
  resultImg.classList.add("agentImg");
  imgDiv.appendChild(resultImg);

  const resultDesc = document.querySelector(".resultDesc");
  resultDesc.innerHTML = infoList[point].desc;
  const hiddenInput = document.querySelector("#hidden_test_result");
  hiddenInput.value = infoList[point].agent; // 밸류값에다가 결과를 저장
  const hiddenInputRole = document.querySelector("#hidden_test_role");
  hiddenInputRole.value = infoList[point].role;

}

function goResult() {
  qna.style.WebkitAnimation = "fadeOut 1s";
  qna.style.animation = "fadeOut 1s";

  setTimeout(() => {
    result.style.WebkitAnimation = "fadeIn 1s";
    result.style.animation = "fadeIn 1s";
    setTimeout(() => {
      qna.style.display = "none";
      result.style.display = "block";
    }, 450);
    let qIdx = 0;
    goNext(qIdx);
  }, 450);

  console.log("select : " + select);
  //calResult();
  setResult();
}

function addAnswer(answerText, qIdx, idx) {
  let a = document.querySelector(".answerBox");
  let answer = document.createElement("button");
  answer.classList.add("answerList");
  answer.classList.add("fadeIn");
  answer.classList.add("dbPrevent");
  a.appendChild(answer);
  answer.innerHTML = answerText;
  // console.log("앤서 텍스트 : " + answerText);

  answer.addEventListener(
      "click",
      function () {
        let children = document.querySelectorAll(".answerList");
        for (let i = 0; i < children.length; i++) {
          children[i].disabled = true;
          children[i].style.WebkitAnimation = "fadeOut 0.5s";
          children[i].style.animation = "fadeOut 0.5s";
        }
        setTimeout(() => {
          let target = qnaList[qIdx].a[idx].type; //qIdx번째 질문에서 idx번째 선택지.
          for (let i = 0; i < Object.keys(target).length; i++) {
            select[target[i]] += 1;
          }
          console.log("qidx = " + qIdx);
          console.log("idx = " + idx);
          //select[qIdx] = idx; //사용자가 몇번쨰 질문에서 몇번째 답변을 클릭했는지 저장.

          for (let i = 0; i < children.length; i++) {
            children[i].style.display = "none";
          }
          goNext(++qIdx);
        }, 450);
      },
      false
  );
  console.log("select[]" + select);
}

function goNext(qIdx) {
  if (qIdx === endPoint) {
    goResult();
    return;
  }
  let q = document.querySelector(".qBox");
  q.innerHTML = qnaList[qIdx].q;
  //console.log("qna List : " + qnaList[0].q);
  for (let i in qnaList[qIdx].a) {
    addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
    //console.log(qnaList[qIdx].a[i].answer);
  }
  let status = document.querySelector(".statusBar");
  status.style.width = (100 / endPoint) * (qIdx + 1) + "%";
}

function begin() {
  main.style.WebkitAnimation = "fadeOut 1s";
  main.style.animation = "fadeOut 1s";

  setTimeout(() => {
    qna.style.WebkitAnimation = "fadeIn 1s";
    qna.style.animation = "fadeIn 1s";
    setTimeout(() => {
      main.style.display = "none";
      qna.style.display = "block";
      qna.style.margin = "0 auto";
    }, 450);
    let qIdx = 0;
    goNext(qIdx);
  }, 450);
  //main.style.display = "none";
  //qna.style.display = "block";
}
