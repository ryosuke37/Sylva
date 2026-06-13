import ReactDOM from "react-dom/client";
import { Tree } from "./components/tree/Tree";
import { TreeProvider } from "./components/tree/TreeProvider";
import { CreateNewPostButton } from "./components/modal/PostCreateButton";
import { PostModal } from "./components/modal/PostModal";
import { PostModalProvider } from "./components/modal/PostModalProvider";

const root = document.getElementById("tree-root");

if (root) {
  const postId = root.dataset.postId!;
  ReactDOM.createRoot(root).render(
    <TreeProvider postId={postId}>
      <PostModalProvider>
        <PostModal />
        <CreateNewPostButton />
        <Tree />
      </PostModalProvider>
    </TreeProvider>
  );
}
