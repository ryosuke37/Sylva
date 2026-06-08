import ReactDOM from "react-dom/client";
import { Tree } from "./components/tree/Tree";
import { TreeProvider } from "./components/tree/TreeProvider";

const root = document.getElementById("tree-root");

if (root) {
  const postId = root.dataset.postId!;
  ReactDOM.createRoot(root).render(
    <TreeProvider postId={postId}>
      <Tree />
    </TreeProvider>
  );
}
