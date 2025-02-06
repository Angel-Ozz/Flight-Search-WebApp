import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
  base:"search",
  plugins: [react()],
  test: {
    globals: true, 
    environment: 'jsdom', 
    setupFiles: './src/tests/setup.ts', // conf file
  },
})
