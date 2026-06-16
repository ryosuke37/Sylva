import { createContext, type RefObject } from "react";
import type { PostDto } from "../../types/PostDto";

export type PostModalContextType = {
  dialogRef: RefObject<HTMLDialogElement | null>;
  isOpen: boolean;
  isLoggedIn: boolean;
  parentPost: PostDto | null;
  quotedPost: PostDto | null;
  openNewPost(): void;
  openReply(postId: string): void;
  openQuote(postId: string): void;
  close(): void;
  onPostCreated(post: PostDto): void;
};

export const PostModalContext = createContext<PostModalContextType | null>(
  null
);
