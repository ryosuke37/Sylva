import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  build: {
    outDir: "../sylva-app/src/main/resources/static/react",
    emptyOutDir: true,
    rollupOptions: {
      input: {
        top: "src/main-top.tsx",
        tree: "src/main-tree.tsx",
      },
      output: {
        entryFileNames: "assets/[name].js",
        chunkFileNames: "assets/[name].js",
        assetFileNames: "assets/[name].[ext]",
      },
    },
  },
});
