/* eslint-disable @typescript-eslint/no-explicit-any */
import { useState } from "react"
import { useNavigate } from "react-router"
import { useFlights } from "./context/FlightsContext"
import AsyncSearchBar from "./components/AsyncSearchBar"
import FormInput from "./components/FormInput"
import FormInputSection from "./components/FormInputSection"

//React Context wasnt working even tho the values were setted and i verified that, they werent retrieved here, thats why the use of localstorage as an auxiliar measure
function App() {
  const {
    depAirport, setDepAirport,
    arrAirport, setArrAirport,
    depDate, setDepDate,
    returnDate, setReturnDate,
    adults, setAdults,
    currency, setCurrency,
    nonStop, setNonStop
  } = useFlights()

  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)
  const navigate = useNavigate()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    
    setLoading(true) 
    setError(null) 

    const flightsEndpoint = `http://localhost:8080/flights/search?originLocationCode=${depAirport}&destinationLocationCode=${arrAirport}&departureDate=${depDate}&returnDate=${returnDate || ""}&adults=${adults}&nonStop=${nonStop}&currencyCode=${currency}`

    try {
      const response = await fetch(flightsEndpoint)
      if (!response.ok) throw new Error("No Flights found")
      const data = await response.json()
      console.log("API Response:", data)

      
      localStorage.setItem("flightsData", JSON.stringify(data))
      localStorage.setItem("depAirport", JSON.stringify(depAirport));
      localStorage.setItem("arrAirport", JSON.stringify(arrAirport));
      localStorage.setItem("depDate", JSON.stringify(depDate));
      localStorage.setItem("returnDate", JSON.stringify(returnDate));
      localStorage.setItem("adults", JSON.stringify(adults));
      localStorage.setItem("currency", JSON.stringify(currency));
      localStorage.setItem("nonStop", JSON.stringify(nonStop));

    
        navigate("/flights")
        
      } catch (err: any) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
 


  return (
    <>
      {loading ? ( 
        <div className="h-screen flex items-center justify-center">
          <h1 className="text-2xl font-bold">Loading flights...</h1>
        </div>
      ) : (
        <main className="flex border flex-col gap-4 bg-slate-200 items-center justify-center h-screen">
          <h1 id= "Flight Search" className="font-bold text-6xl">Flight Search</h1>
          <section className="bg-slate-50 border rounded-xl shadow-lg md:w-1/2 xl:w-[28%] p-8 w-full">
            <form className="flex flex-col w-full" onSubmit={handleSubmit}>
              <FormInputSection>
                <label>Departure Airport</label>
                <AsyncSearchBar setState={setDepAirport} />
              </FormInputSection>

              <FormInputSection>
                <label>Arrival Airport</label>
                <AsyncSearchBar setState={setArrAirport} />
              </FormInputSection>

              <FormInputSection>
                <label htmlFor="Departure date">Departure date</label>
                <FormInput id ="Departure date" type="date" name="departure-date" setState={setDepDate} min={new Date().toISOString().split("T")[0]} required />
              </FormInputSection>

              <FormInputSection>
                <label htmlFor="Return Date">Return date</label>
                <FormInput id="Return Date"type="date" name="return-date" setState={setReturnDate} min={depDate ?? ""} />
              </FormInputSection>

              <FormInputSection>
                <label htmlFor="adults">Adults</label>
                <FormInput id="adults" type="number" name="adults" setState={setAdults} defaultValue={1} min={1} />
              </FormInputSection>

              <FormInputSection>
                <label htmlFor="currency">Currency</label>
                <select id="currency" name="currency" className="border w-48 md:w-52 m-2" onChange={(e) => setCurrency(e.target.value)} value={currency}>
                  <option value="USD">USD</option>
                  <option value="EUR">EUR</option>
                  <option value="MXN">MXN</option>
                </select>
              </FormInputSection>

              <div className="self-start">
                <input id="Non-stop" type="checkbox" name="nonStop" className="mr-1" checked={nonStop} onChange={(e) => setNonStop(e.target.checked)} />
                <label htmlFor="Non-stop">Non-stop</label>
              </div>

              {error && <p className="text-red-500">{error}</p>} {}
              
              <input value="Search Flights" aria-label="Search Flights" type="submit" className="bg-blue-800 cursor-pointer rounded-full text-white disabled:opacity-50 mt-3 p-2" disabled={loading} />
            </form>
          </section>
        </main>
      )}
    </>
  )
}

export default App;
