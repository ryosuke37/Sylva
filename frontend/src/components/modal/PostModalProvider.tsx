import { useRef, useState } from "react";

import { PostModalContext } from "./PostModalContext";

export function PostModalProvider({ children }: React.PropsWithChildren) {
  const [parentPostId, setParentPostId] = useState<string>("");
  const [quotedPostId, setQuotedPostId] = useState<string>("");
  const dialogRef = useRef<HTMLDialogElement>(null);

  function openNewPost() {
    setParentPostId("");
    setQuotedPostId("");

    dialogRef.current?.showModal();
  }

  function openReply(postId: string) {
    setParentPostId(postId);
    setQuotedPostId("");

    dialogRef.current?.showModal();
  }

  function openQuote(postId: string) {
    setParentPostId("");
    setQuotedPostId(postId);

    dialogRef.current?.showModal();
  }

  function close() {
    dialogRef.current?.close();
  }

  return (
    <PostModalContext.Provider
      value={{
        dialogRef: dialogRef,
        parentPostId: parentPostId,
        quotedPostId: quotedPostId,
        openNewPost: openNewPost,
        openReply: openReply,
        openQuote: openQuote,
        close: close,
      }}
    >
      {children}
    </PostModalContext.Provider>
  );
}
