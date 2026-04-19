document.addEventListener("DOMContentLoaded", () => {
  const element = document.querySelector(".target.post");
  element.scrollIntoView({
    block: "start",
    behavior: "instant",
    inline: "start",
  });
});
