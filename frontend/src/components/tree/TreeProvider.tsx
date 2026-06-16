import {
  type PropsWithChildren,
  useCallback,
  useEffect,
  useState,
} from "react";
import { getTree } from "../../api/treeApi";
import type { TreeDto } from "../../types/TreeDto";
import type { PostDto } from "../../types/PostDto";
import { TreeContext } from "./TreeContext";

type Props = PropsWithChildren<{
  postId: string;
}>;

export function TreeProvider({ postId, children }: Props) {
  const [tree, setTree] = useState<TreeDto>();

  const refresh = useCallback(async () => {
    const result = await getTree(postId);

    setTree(result);
  }, [postId]);

  useEffect(() => {
    refresh();
  }, [refresh]);

  function prependPost(post: PostDto) {
    if (post.parentPost?.id == tree?.target.id) {
      prependDescendant(post);
    }
  }

  function prependDescendant(post: PostDto) {
    setTree((prev) => {
      if (!prev) {
        return prev;
      }

      return {
        ...prev,
        descendants: [post, ...prev.descendants],
      };
    });
  }

  return (
    <TreeContext.Provider
      value={{
        tree,
        refresh,
        prependPost: prependPost,
      }}
    >
      {children}
    </TreeContext.Provider>
  );
}
