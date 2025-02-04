import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import Flights from './pages/Flights.tsx'
import { BrowserRouter, Route, Routes } from 'react-router'

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Routes>
      <Route path="/search" element={<App />} />
      <Route path="/flights" element={<Flights />} />
    </Routes>
  </BrowserRouter>
)
