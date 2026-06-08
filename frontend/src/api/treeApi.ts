import type { TreeDto } from "../types/TreeDto";

export async function getTree(postId: string): Promise<TreeDto> {
  const response = await fetch(`/api/tree/${postId}`);

  if (!response.ok) {
    throw new Error("Tree load failed");
  }

  return response.json();
}
