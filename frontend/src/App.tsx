import { useState } from "react"
import AsyncSearchBar from "./components/AsyncSearchBar"
import FormInput from "./components/FormInput"
import FormInputSection from "./components/FormInputSection"


function App() {

  const [depAirport,setDepAirport] = useState()
  const [arrAirport,setArrAirport] = useState()
  const [depDate,setDepDate] = useState()
  const [returnDate, setReturnDate] = useState<string | null>(null)
  const [adults, setAdults] = useState<number>(1)
  const [currency,setCurrency] = useState<string>("USD")
  const [nonStop, setNonStop] = useState<boolean>(false)
  const flightsEndpoint = `http://localhost:8080/flights/search?originLocationCode=${depAirport}&destinationLocationCode=${arrAirport}&departureDate=${depDate}&returnDate=${returnDate}&adults=${adults}&nonStop=${nonStop}&currencyCode=${currency}`

  
  return (
    <>
      <main className="flex border flex-col gap-4 items-center justify-center h-screen">
        <h1 className="font-bold text-2xl">Flight Search</h1>
        <section className="border md:w-1/2 xl:w-[28%] p-8 w-full">
          <form className="flex flex-col w-full" onSubmit={(e)=>{
            e.preventDefault()
            console.log(depAirport)
            console.log(arrAirport)
            console.log(depDate)
            console.log(returnDate)
            console.log(adults)
            console.log(currency)
            console.log(nonStop)
            fetch(flightsEndpoint)
            .then((res) => res.json())
            .then((data) => {
              console.log("API Response:", data); 
          })
          .catch((error) => console.error("Error fetching data:", error));
          }}>

            <FormInputSection>
              <label>Departure Airport</label>
              <AsyncSearchBar setState={setDepAirport}/>
            </FormInputSection>

            <FormInputSection>
              <label>Arrival Airport</label>
              <AsyncSearchBar setState={setArrAirport} />
            </FormInputSection>

            <FormInputSection>
              <label>Departure date</label>
              <FormInput type="date" name="departure-date" setState={setDepDate} min={new Date().toISOString().split("T")[0]} required />
            </FormInputSection>

            <FormInputSection>
              <label>Return date</label>
              <FormInput type="date" name="return-date" returnDate={true} setState={setReturnDate} min={depDate}/>
            </FormInputSection>

            <FormInputSection>
              <label>Adults</label>
              <FormInput type="number" name="adults" setState={setAdults} defaultValue={1} min={1}/>
            </FormInputSection>

            <FormInputSection>
              <label>Currency</label>
              <select name="currency" className="border w-48 md:w-52 m-2" onChange={(e) => setCurrency(e.target.value)} value={currency} >

                <option value="USD">USD</option>
                <option value="EUR">EUR</option>
                <option value="MXN">MXN</option>
              </select>
            </FormInputSection>

            <div className="self-start">
            <input type="checkbox" name="nonStop" className="mr-1" checked={nonStop} onChange={(e) => setNonStop(e.target.checked)}
/>
              <label>Non-stop</label>
            </div>
            <input type="submit" className="bg-blue-800 cursor-pointer rounded-full text-white"/>
          </form>
        </section>
      </main>
    </>
  )
}

export default App
