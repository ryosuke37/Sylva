import { useContext, useState } from "react";

import { createPost } from "../../api/postApi";
import { PostModalContext } from "./PostModalContext";

export function PostModal() {
  const modal = useContext(PostModalContext);

  const [content, setContent] = useState("");
  const [errors, setErrors] = useState<string[]>([]);

  if (!modal) {
    throw new Error("PostModalProvider not found");
  }

  async function submit() {
    try {
      await createPost({
        content,
        parentPostId: modal!.parentPostId,
        quotedPostId: modal!.quotedPostId,
      });

      setContent("");
      setErrors([]);

      modal!.close();
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

  return (
    <dialog id='post-modal' ref={modal.dialogRef} onClose={handleClose}>
      <div className='modal-header'>
        <button
          id='post-modal-close-button'
          type='button'
          onClick={() => modal.close()}
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
        <button id='post-submit-button' type='button' onClick={submit}>
          投稿
        </button>
      </div>
    </dialog>
  );
}
