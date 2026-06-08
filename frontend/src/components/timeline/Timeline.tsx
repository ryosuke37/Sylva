import { useContext } from "react";
import { TimelineContext } from "./TimelineContext";
import { Post } from "../post/Post";

export function Timeline() {
  const context = useContext(TimelineContext);

  if (!context) {
    throw new Error("TimelineProvider not found");
  }

  return (
    <div className='timeline'>
      {context.posts.map((post) => (
        <Post key={post.id} post={post} />
      ))}
    </div>
  );
}
