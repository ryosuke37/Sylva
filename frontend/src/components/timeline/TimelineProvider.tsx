import { useEffect, useState } from "react";
import {
  loadInitialTimeline,
  loadOlderTimeline,
  loadNewerTimeline,
} from "../../api/timelineApi";
import type { PostDto } from "../../types/PostDto";
import { TimelineContext } from "./TimelineContext";

export function TimelineProvider({ children }: React.PropsWithChildren) {
  const [posts, setPosts] = useState<PostDto[]>([]);

  useEffect(() => {
    loadInitialTimeline().then(setPosts);
  }, []);

  async function loadOlder() {
    if (posts.length === 0) {
      return;
    }

    const oldest = posts[posts.length - 1];
    const older = await loadOlderTimeline(oldest.createdDate);
    setPosts((prev) => [...prev, ...older]);
  }

  async function loadNewer() {
    if (posts.length === 0) {
      return;
    }

    const newest = posts[0];
    const newer = await loadNewerTimeline(newest.createdDate);
    setPosts((prev) => [...newer, ...prev]);
  }

  function prependPost(post: PostDto) {
    if (post.parentPost == null) {
      setPosts((prev) => [post, ...prev]);
    }
  }

  return (
    <TimelineContext.Provider
      value={{
        posts: posts,
        prependPost: prependPost,
        loadOlder: loadOlder,
        loadNewer: loadNewer,
      }}
    >
      {children}
    </TimelineContext.Provider>
  );
}
