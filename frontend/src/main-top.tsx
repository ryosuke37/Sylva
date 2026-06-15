import ReactDOM from "react-dom/client";
import { TimelineProvider } from "./components/timeline/TimelineProvider";
import { Timeline } from "./components/timeline/Timeline";
import { PostModalProvider } from "./components/modal/PostModalProvider";
import { PostModal } from "./components/modal/PostModal";
import { CreateNewPostButton } from "./components/modal/PostCreateButton";

const timelineRoot = document.getElementById("timeline-root");

if (timelineRoot) {
  ReactDOM.createRoot(timelineRoot).render(
    <TimelineProvider>
      <PostModalProvider>
        <PostModal />
        <CreateNewPostButton />
        <Timeline />
      </PostModalProvider>
    </TimelineProvider>
  );
}
