import { useContext } from "react";
import { createPortal } from "react-dom";
import { PostModalContext } from "./PostModalContext";

export function CreateNewPostButton() {
  const newPostButtonRoot = document.getElementById("new-post-button-root");
  const modal = useContext(PostModalContext)!;

  if (!newPostButtonRoot) {
    return null;
  }

  return createPortal(
    <button className='post-create-button' onClick={() => modal.openNewPost()}>
      <span className='material-symbols-rounded'>new_window</span>
    </button>,
    newPostButtonRoot
  );
}

export function ReplyButton({ parentPostId }: { parentPostId: string }) {
  const modal = useContext(PostModalContext)!;

  return (
    <button
      className='reply-button'
      onClick={() => modal.openReply(parentPostId)}
    >
      <span className='material-symbols-rounded'>comment</span>
    </button>
  );
}

export function QuotePostButton({ quotedPostId }: { quotedPostId: string }) {
  const modal = useContext(PostModalContext)!;

  return (
    <button
      className='quote-post-button'
      onClick={() => modal.openQuote(quotedPostId)}
    >
      <span className='material-symbols-rounded'>format_quote</span>
    </button>
  );
}
