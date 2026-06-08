import { createContext } from "react";
import type { PostDto } from "../../types/PostDto";

export interface TimelineContextType {
  posts: PostDto[];

  prependPost(post: PostDto): void;
  loadOlder(): Promise<void>;
  loadNewer(): Promise<void>;
}

export const TimelineContext = createContext<TimelineContextType | null>(null);
