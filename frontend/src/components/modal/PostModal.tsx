import { useContext, useState } from "react";

import { createPost } from "../../api/postApi";
import { PostModalContext } from "./PostModalContext";
import { createPortal } from "react-dom";

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

  async function post() {
    try {
      const createdPost = await createPost({
        content,
        parentPostId: modalProvider!.parentPostId,
        quotedPostId: modalProvider!.quotedPostId,
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
        <textarea
          className='tweet-box'
          placeholder='いまどうしてる？'
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />

        <ul className='error-messages'>
          {errors.map((error) => (
            <li key={error} className='error-message'>
              {error}
            </li>
          ))}
        </ul>
      </div>

      <div className='modal-footer'>
        <button id='post-submit-button' type='button' onClick={post}>
          投稿
        </button>
      </div>
    </dialog>,
    postModalRoot
  );
}
