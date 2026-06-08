import { type UserDto } from "./UserDto";

export interface PostDto {
  id: string;
  content: string;
  user: UserDto;
  rootPost?: PostDto | null;
  parentPost?: PostDto | null;
  quotedPost?: PostDto | null;
  createdDate: string;
  updatedDate: string;
}
