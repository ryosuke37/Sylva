import ReactDOM from "react-dom/client";
import { TimelineProvider } from "./components/timeline/TimelineProvider";
import { Timeline } from "./components/timeline/Timeline";

const root = document.getElementById("timeline-root");

if (root) {
  ReactDOM.createRoot(root).render(
    <TimelineProvider>
      <Timeline />
    </TimelineProvider>
  );
}
