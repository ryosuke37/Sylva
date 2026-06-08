import ReactDOM from "react-dom/client";
import { PostModal } from "./components/modal/PostModal";
import { PostModalProvider } from "./components/modal/PostModalProvider";
import { CreateNewPostButton } from "./components/modal/PostCreateButton";

const postModalRoot = document.getElementById("post-modal-root");

if (postModalRoot) {
  ReactDOM.createRoot(postModalRoot).render(
    <PostModalProvider>
      <PostModal />
      <CreateNewPostButton />
    </PostModalProvider>
  );
}
