// JavaScript code to handle the mouse click event
$(document).mousedown(function (event) {
  if (event.which == 1) {
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
    $("#popup").fadeOut();
  }
});

$("#popup").click(function () {
  // Hide the popup
  $("#popup").hide();
});
