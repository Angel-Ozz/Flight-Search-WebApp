/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState } from "react"
import { useFlights } from "../context/FlightsContext"
import { useNavigate } from "react-router"
import Switcher3 from "../components/Switch3"


//React Context wasnt working even tho the values were setted and i verified that, they werent retrieved here, thats why the use of localstorage as an auxiliar measure
function Flights() {
  const {
    depAirport, setDepAirport,
    arrAirport, setArrAirport,
    depDate, setDepDate,
    returnDate, setReturnDate,
    adults, setAdults,
    currency, setCurrency,
    nonStop, setNonStop
  } = useFlights()

  const [pages, setPages] = useState<number>(0)
  const [sortByPrice, setSortByPrice] = useState<string | null>(null)
  const [sortByDuration, setSortByDuration] = useState<string | null>(null)
  const sortBy = [sortByPrice, sortByDuration].filter(Boolean).join(",") || null

  const [loading, setLoading] = useState<boolean>(false)
  const [flights, setFlights] = useState<any>(null)
  const navigate = useNavigate()


  useEffect(() => {
    setLoading(true)

    setDepAirport(JSON.parse(localStorage.getItem("depAirport") || "null"))
    setArrAirport(JSON.parse(localStorage.getItem("arrAirport") || "null"))
    setDepDate(JSON.parse(localStorage.getItem("depDate") || "null"))
    setReturnDate(JSON.parse(localStorage.getItem("returnDate") || "null"))
    setAdults(JSON.parse(localStorage.getItem("adults") || "1"))
    setCurrency(JSON.parse(localStorage.getItem("currency") || `"USD"`))
    setNonStop(JSON.parse(localStorage.getItem("nonStop") || "false"))


    const storedSortBy = localStorage.getItem("sortBy")
      if (storedSortBy) {
        const sortArray = storedSortBy.split(",")
        setSortByPrice(sortArray.includes("price") ? "price" : null)
        setSortByDuration(sortArray.includes("duration") ? "duration" : null)
      }

    const storedData = localStorage.getItem("flightsData")
      if (storedData) {
        setFlights(JSON.parse(storedData));
      } else {
        navigate("/search");
        return
      }
    }, [])


  useEffect(() => {
    const sortArray = [sortByPrice, sortByDuration].filter(Boolean).join(",")
    localStorage.setItem("sortBy", sortArray)
  }, [sortByPrice, sortByDuration])


  useEffect(() => {

    const flightEndpointSortPage = `http://localhost:8080/flights/search?originLocationCode=${depAirport}&destinationLocationCode=${arrAirport}&departureDate=${depDate}&returnDate=${returnDate || ""}&adults=${adults}&nonStop=${nonStop}&currencyCode=${currency}${sortBy ? `&sortBy=${sortBy}` : ""}&page=${pages}`

    const fetchFlights = async () => {
      try {
        setLoading(true);
        const response = await fetch(flightEndpointSortPage)
        if (!response.ok) throw new Error("No Flights found")
        const data = await response.json();
        console.log("API Response:", data);
        localStorage.setItem("flightsData", JSON.stringify(data))
        setFlights(data)
      } catch (err: any) {
        console.error(err.message);
      } finally {
        setLoading(false)
      }
    };

    fetchFlights()
  }, [pages, sortBy, navigate])

  return (
    <>
      {loading ? (
        <div className="h-screen flex items-center justify-center">
          <h1 className="text-2xl font-bold">Loading flights...</h1>
        </div>
      ) : (
        <div className="h-screen bg-slate-300">
          <h1 className="font-bold mt-1 ml-10 text-6xl">Flight Results</h1>
          <button
            className="bg-blue-800 cursor-pointer rounded-full text-white disabled:opacity-50 m-3 p-2 ml-10" onClick={() => navigate("/")}>Back to search</button>
          <div className="flex items-center justify-end space-x-4 p-4">
            <label className="mr-2 font-semibold">Sort By:</label>
            <div className="flex items-center space-x-2">
              <label>Price</label>
              <Switcher3 checked={sortByPrice === "price"} onChange={(checked) => setSortByPrice(checked ? "price" : null)} />
            </div>
            <div className="flex items-center space-x-2">
              <label>Duration</label>
              <Switcher3 checked={sortByDuration === "duration"} onChange={(checked) => setSortByDuration(checked ? "duration" : null)} />
            </div>
          </div>

          {flights ? (
            <section className="w-full border min-h-screen bg-slate-200 flex flex-col gap-8 p-4">
              {flights.map((flight: any, index: number) => (
                <div key={index} className={`rounded-xl grid grid-cols-12 border bg-slate-100 p-4 gap-4`}>
                  {/* COL 1 */}
                  <div className="col-span-4">
                    <span>
                      {`${new Date(flight.departureTime).toLocaleString('es-ES', { 
                          day: '2-digit', month: '2-digit', year: 'numeric', 
                          hour: '2-digit', minute: '2-digit' 
                      })} - 
                        ${new Date(flight.arrivalTime).toLocaleString('es-ES', { 
                          day: '2-digit', month: '2-digit', year: 'numeric', 
                          hour: '2-digit', minute: '2-digit' 
                      })}`}
                    </span>

                    <p>{`${flight.departureIata} - ${flight.arrivalIata}`}</p>
                  </div>
                  {/* COL 2 */}
                  <div className="col-span-4">
                    <span>{flight.totalDuration.split("T")[1].toLowerCase()}</span>
                    <p>{flight.stops.length === 0 ? "(Non-Stop)" : `Stops: ${flight.stops.length}`}</p>
                    {flight.stops.map((stop: any, index: number) => (
                      <div key={index}>{`${stop.airportCode} - ${stop.duration.split("T")[1].toLowerCase()}`}</div>
                    ))}
                    <p>-----------</p>
                    <span>{flight.returnArrivalTime && flight.returnTotalDuration.split("T")[1].toLowerCase()}</span>
                    <p>{flight.returnStops.length === 0 ? "" : ` Return Stops: ${flight.returnStops.length}`}</p>
                    {flight.returnStops.map((returnStop: any, index: number) => (
                      <div key={index}>{`${returnStop.airportCode} - ${returnStop.duration.split("T")[1].toLowerCase()}`}</div>
                    ))}
                  </div>
                  {/* COL 3 */}
                  <div className="col-span-4 row-span-2">
                    <p>{`$${flight.price} ${flight.currency}`}<br />total</p>
                    <br />
                    <p>{`$${flight.pricePerTraveler[0]} ${flight.currency}`}<br />per Traveler</p>
                    <button className="rounded-xl text-white bg-blue-400 hover:bg-blue-600 px-4 py-2 transition-colors" onClick={() => {
                      localStorage.setItem("flight_info", JSON.stringify(flight))
                      navigate("/details")
                    }}>Details</button>
                  </div>
                  {flight.returnArrivalTime && (
                    <div className="col-span-4">
                      <span>
                      {`${new Date(flight.returnDepartureTime).toLocaleString('es-ES', { 
                          day: '2-digit', month: '2-digit', year: 'numeric', 
                          hour: '2-digit', minute: '2-digit' 
                      })} - 
                        ${new Date(flight.returnArrivalTime).toLocaleString('es-ES', { 
                          day: '2-digit', month: '2-digit', year: 'numeric', 
                          hour: '2-digit', minute: '2-digit' 
                      })}`}
                    </span>
                      <p>{`${flight.returnDepartureIata} - ${flight.returnArrivalIata}`}</p>
                    </div>
                  )}
                  <p>Airline: {flight.carrierCode}</p>
                </div>
              ))}

              <div className="flex items-center justify-end space-x-4 p-4">
                {pages > 0 && (
                  <button className="bg-blue-800 cursor-pointer rounded-full text-white m-3 pl-5 pr-5 p-2 ml-10" onClick={() => setPages((prev) => prev - 1)}>
                    Prev Page
                  </button>
                )}
                <button className="bg-blue-800 cursor-pointer rounded-full text-white m-3 pl-5 pr-5 p-2 ml-10" onClick={() => setPages((prev) => prev + 1)}>
                  Next Page
                </button>
              </div>
            </section>
          ) : (
            <p>No flights found</p>
          )}
        </div>
      )}
    </>
  );
}

export default Flights;
