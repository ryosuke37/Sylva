export function getCsrfHeaderName(): string {
  return (
    document
      .querySelector("meta[name='_csrf_header']")
      ?.getAttribute("content") ?? ""
  );
}

export function getCsrfToken(): string {
  return (
    document.querySelector("meta[name='_csrf']")?.getAttribute("content") ?? ""
  );
}
