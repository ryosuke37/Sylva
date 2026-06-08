import { useContext, useLayoutEffect, useRef } from "react";

import { TreeContext } from "./TreeContext";

import { Post } from "../post/Post";

export function Tree() {
  const context = useContext(TreeContext);

  const targetRef = useRef<HTMLDivElement>(null);

  if (!context) {
    throw new Error("TreeProvider not found");
  }

  const tree = context.tree;
  const initialized = useRef(false);

  useLayoutEffect(() => {
    if (!tree) {
      return;
    }

    if (initialized.current) {
      return;
    }

    initialized.current = true;

    targetRef.current?.scrollIntoView({
      block: "start",
      behavior: "instant",
      inline: "start",
    });
  }, [tree]);

  if (!tree) {
    return <>Loading...</>;
  }

  return (
    <div className='post-tree'>
      {tree.ancestors.map((ancestor) => (
        <div key={ancestor.id} className='ancestor'>
          <Post post={ancestor} />
        </div>
      ))}

      <div ref={targetRef} className='target'>
        <Post post={tree.target} />
      </div>

      {tree.descendants.map((descendant) => (
        <div key={descendant.id} className='descendant'>
          <Post post={descendant} />
        </div>
      ))}
    </div>
  );
}
