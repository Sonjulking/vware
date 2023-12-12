const help_text = document.querySelector("#help_text");

function clHelpText() {
  help_text.classList.toggle("clikced-color");
}

help_text.addEventListener("click", clHelpText);
