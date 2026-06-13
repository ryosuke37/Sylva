import { useContext, useRef, useState } from "react";

import { PostModalContext } from "./PostModalContext";
import type { PostDto } from "../../types/PostDto";
import { TimelineContext } from "../timeline/TimelineContext";
import { TreeContext } from "../tree/TreeContext";

export function PostModalProvider({ children }: React.PropsWithChildren) {
  const timeline = useContext(TimelineContext)!;
  const tree = useContext(TreeContext)!;
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

  function onPostCreated(post: PostDto) {
    if (timeline) {
      timeline.prependPost(post);
    } else if (tree) {
      tree.prependDescendant(post);
    }
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
        onPostCreated: onPostCreated,
      }}
    >
      {children}
    </PostModalContext.Provider>
  );
}
