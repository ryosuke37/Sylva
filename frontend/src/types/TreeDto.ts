import type { PostDto } from "./PostDto";

export interface TreeDto {
  ancestors: PostDto[];
  target: PostDto;
  descendants: PostDto[];
}
