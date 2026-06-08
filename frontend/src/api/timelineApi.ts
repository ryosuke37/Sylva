import type { PostDto } from "../types/PostDto";

const LIMIT = 10;

export async function loadInitialTimeline(): Promise<PostDto[]> {
  const response = await fetch(`/api/timeline/?limit=${LIMIT}`);

  if (!response.ok) {
    throw new Error("Timeline load failed");
  }

  return await response.json();
}

export async function loadOlderTimeline(point: string): Promise<PostDto[]> {
  const response = await fetch(
    `/api/timeline/older?limit=${LIMIT}&point=${encodeURIComponent(point)}`
  );

  if (!response.ok) {
    throw new Error("Timeline load failed");
  }

  return await response.json();
}

export async function loadNewerTimeline(point: string): Promise<PostDto[]> {
  const response = await fetch(
    `/api/timeline/newer?limit=${LIMIT}&point=${encodeURIComponent(point)}`
  );

  if (!response.ok) {
    throw new Error("Timeline load failed");
  }

  return await response.json();
}
