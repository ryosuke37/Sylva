import { createContext, type RefObject } from "react";

export type PostModalContextType = {
  dialogRef: RefObject<HTMLDialogElement | null>;
  parentPostId: string;
  quotedPostId: string;
  openNewPost(): void;
  openReply(postId: string): void;
  openQuote(postId: string): void;
  close(): void;
};

export const PostModalContext = createContext<PostModalContextType | null>(
  null
);
