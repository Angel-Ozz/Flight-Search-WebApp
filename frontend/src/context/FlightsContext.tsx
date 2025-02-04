import { createContext, useState, ReactNode, useContext } from "react"

interface FlightsContextType {
  depAirport: string | null
  setDepAirport: (value: string | null) => void
  arrAirport: string | null
  setArrAirport: (value: string | null) => void
  depDate: string | null
  setDepDate: (value: string | null) => void
  returnDate: string | null;
  setReturnDate: (value: string | null) => void;
  adults: number;
  setAdults: (value: number) => void;
  currency: string;
  setCurrency: (value: string) => void;
  nonStop: boolean;
  setNonStop: (value: boolean) => void;
}

const FlightsContext = createContext<FlightsContextType | undefined>(undefined);

export const FlightsProvider = ({ children }: { children: ReactNode }) => {
  const [depAirport, setDepAirport] = useState<string | null>(null)
  const [arrAirport, setArrAirport] = useState<string | null>(null)
  const [depDate, setDepDate] = useState<string | null>(null)
  const [returnDate, setReturnDate] = useState<string | null>(null)
  const [adults, setAdults] = useState<number>(1)
  const [currency, setCurrency] = useState<string>("USD")
  const [nonStop, setNonStop] = useState<boolean>(false)

  return (
    <FlightsContext.Provider value={{ depAirport, setDepAirport, arrAirport, setArrAirport, depDate, setDepDate, returnDate, setReturnDate, adults, setAdults, currency, setCurrency, nonStop, setNonStop }}>
      {children}
    </FlightsContext.Provider>
  )
}


export const useFlights = () => {
  const context = useContext(FlightsContext)
  if (!context) {
    throw new Error("useFlights needs a flightprovider")
  }
  return context
}
