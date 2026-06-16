import { createContext } from "react";

import type { TreeDto } from "../../types/TreeDto";
import type { PostDto } from "../../types/PostDto";

export interface TreeContextType {
  tree?: TreeDto;

  refresh(): Promise<void>;

  prependPost(post: PostDto): void;
}

export const TreeContext = createContext<TreeContextType | null>(null);
