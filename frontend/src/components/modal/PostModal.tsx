import { useContext, useEffect, useState } from "react";

import { createPost } from "../../api/postApi";
import { PostModalContext } from "./PostModalContext";
import { createPortal } from "react-dom";
import { Post } from "../post/Post";

export function PostModal() {
  const postModalRoot = document.getElementById("post-modal-root");
  const modalProvider = useContext(PostModalContext);

  if (!postModalRoot) {
    throw new Error("PostModalRoot not found");
  }

  if (!modalProvider) {
    throw new Error("PostModalProvider not found");
  }

  const [content, setContent] = useState("");
  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {
    if (!modalProvider.isOpen) {
      return;
    }

    if (!modalProvider!.isLoggedIn) {
      setErrors(["投稿するにはログインが必要です"]);
    }
  }, [modalProvider.isOpen]);

  async function post() {
    try {
      const parentPostId =
        modalProvider!.parentPost === null ? "" : modalProvider!.parentPost.id;
      const quotedPostId =
        modalProvider!.quotedPost === null ? "" : modalProvider!.quotedPost.id;
      const createdPost = await createPost({
        content,
        parentPostId: parentPostId,
        quotedPostId: quotedPostId,
      });

      setContent("");
      setErrors([]);

      modalProvider!.onPostCreated(createdPost);
      modalProvider!.close();
    } catch (e: any) {
      if (e?.details && Array.isArray(e.details)) {
        setErrors(e.details.map((detail: any) => detail.message));
      } else {
        setErrors([e?.message ?? "投稿に失敗しました"]);
      }
    }
  }

  function handleClose() {
    setContent("");
    setErrors([]);
  }

  return createPortal(
    <dialog id='post-modal' ref={modalProvider.dialogRef} onClose={handleClose}>
      <div className='modal-header'>
        <button
          id='post-modal-close-button'
          type='button'
          onClick={() => modalProvider.close()}
        >
          <span className='material-symbols-rounded'>close</span>
        </button>
      </div>

      <div className='modal-body'>
        {modalProvider!.parentPost && (
          <Post post={modalProvider!.parentPost} needFooter={false} />
        )}
        <textarea
          className='tweet-box'
          placeholder='いまどうしてる？'
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        {modalProvider!.quotedPost && (
          <Post post={modalProvider!.quotedPost} needFooter={false} />
        )}

        <ul className='error-messages'>
          {errors.map((error) => (
            <li key={error} className='error-message'>
              {error}
            </li>
          ))}
        </ul>
      </div>

      <div className='modal-footer'>
        <button
          id='post-submit-button'
          type='button'
          disabled={!modalProvider!.isLoggedIn}
          onClick={post}
        >
          投稿
        </button>
      </div>
    </dialog>,
    postModalRoot
  );
}
