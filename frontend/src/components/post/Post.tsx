import type { PostDto } from "../../types/PostDto";

type Props = {
  post: PostDto;
};

export function Post({ post }: Props) {
  const isEdited = post.createdDate !== post.updatedDate;

  const updatedDate = new Date(post.updatedDate).toLocaleString("ja-JP", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });

  return (
    <div className='post'>
      <div className='post-header'>
        <span hidden>{post.id}</span>
        <span className='user-nickname'>{post.user.nickname}</span>
        <span className='user-handle'>@{post.user.handle}</span>
        {isEdited && <span className='post-update-status'>編集済み</span>}
        <span className='post-updated-date'>{updatedDate}</span>
      </div>
      <div className='post-main'>
        <a href={`/tree/${post.id}`}>
          <pre>
            <span className='post-content'>{post.content}</span>
          </pre>
        </a>
        {post.quotedPost && (
          <div className='quoted-post'>
            <Post post={post.quotedPost} />
          </div>
        )}
      </div>
    </div>
  );
}
