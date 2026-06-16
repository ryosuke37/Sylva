import { useContext, useRef, useState } from "react";

import { PostModalContext } from "./PostModalContext";
import type { PostDto } from "../../types/PostDto";
import { TimelineContext } from "../timeline/TimelineContext";
import { TreeContext } from "../tree/TreeContext";
import { getPost } from "../../api/postApi";
import { getLoginUserId } from "../../common/auth";

export function PostModalProvider({ children }: React.PropsWithChildren) {
  const timeline = useContext(TimelineContext)!;
  const tree = useContext(TreeContext)!;
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const isLoggedIn = getLoginUserId() != null;
  const [parentPost, setParentPost] = useState<PostDto | null>(null);
  const [quotedPost, setQuotedPost] = useState<PostDto | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  function openNewPost() {
    setParentPost(null);
    setQuotedPost(null);

    open();
  }

  async function openReply(postId: string) {
    setParentPost(await getPost(postId));
    setQuotedPost(null);

    open();
  }

  async function openQuote(postId: string) {
    setParentPost(null);
    setQuotedPost(await getPost(postId));

    open();
  }

  function open() {
    setIsOpen(true);
    dialogRef.current?.showModal();
  }

  function close() {
    dialogRef.current?.close();
    setIsOpen(false);
  }

  function onPostCreated(post: PostDto) {
    if (timeline) {
      timeline.prependPost(post);
    } else if (tree) {
      tree.prependPost(post);
    }
  }

  return (
    <PostModalContext.Provider
      value={{
        dialogRef: dialogRef,
        isOpen: isOpen,
        isLoggedIn: isLoggedIn,
        parentPost: parentPost,
        quotedPost: quotedPost,
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
