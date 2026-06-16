import { getCsrfHeaderName, getCsrfToken } from "./csrf";

export interface CreatePostRequest {
  content: string;
  parentPostId: string;
  quotedPostId: string;
}

export async function getPost(postId: String) {
  const response = await fetch(`/api/post/${postId}`);

  if (!response.ok) {
    throw new Error("Post load failed");
  }

  return response.json();
}

export async function createPost(request: CreatePostRequest) {
  const response = await fetch("/api/post", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [getCsrfHeaderName()]: getCsrfToken(),
    },
    body: JSON.stringify(request),
  });

  if (!response.ok) {
    const error = await response.json();

    throw error;
  }

  return await response.json();
}
