const csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
const csrfToken = $("meta[name='_csrf']").attr("content");
const $postModal = $("#post-modal");

$(function () {
  $(".post-create-button").on("click", function () {
    $(".post-modal input[name='parentPostId']").val(
      $(this).data("parentPostId")
    );
    $(".post-modal input[name='quotedPostId']").val(
      $(this).data("quotedPostId")
    );

    $postModal[0].showModal();
  });

  $("#post-submit-button").on("click", function () {
    const $textarea = $("#post-modal textarea");
    const $errorMessages = $("#post-modal .error-messages");
    $errorMessages.children().remove();

    $.ajax({
      url: "/api/post",
      type: "Post",
      contentType: "application/json",
      headers: {
        [csrfHeaderName]: csrfToken,
      },
      data: JSON.stringify({
        content: $textarea.val(),
        parentPostId: $("#post-modal input[name='parentPostId']").val(),
        quotedPostId: $("#post-modal input[name='quotedPostId']").val(),
      }),
      timeout: 5000,
    })
      .done(function (data) {
        $textarea.val("");
        $postModal[0].close();
        console.log("Post success.");
      })
      .fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Post failed.");
        const response = jqXHR.responseJSON;
        if (textStatus === "timeout") {
          appendErrorMessageToModal(
            "要求がタイムアウトしました",
            $errorMessages
          );
        } else {
          if ("details" in response) {
            response.details.forEach((detail) => {
              appendErrorMessageToModal(detail.message, $errorMessages);
            });
          } else {
            appendErrorMessageToModal(response.message, $errorMessages);
          }
        }
      });
  });

  $("#post-modal-close-button").on("click", function () {
    $postModal[0].close();
  });
});

function appendErrorMessageToModal(errorMessage, $target) {
  const $li = $("<li>").text(errorMessage).addClass("error-message");
  $target.append($li);
}
