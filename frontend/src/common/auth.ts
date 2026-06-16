export function getLoginUserId(): string | null {
  const loginUserId =
    document
      .querySelector("meta[name='_login_user_id']")
      ?.getAttribute("content") ?? null;

  return loginUserId;
}
