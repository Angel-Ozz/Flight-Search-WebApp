import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

function Flights() {
  const [flights, setFlights] = useState<any>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedData = localStorage.getItem("flightsData");
    if (storedData) {
      setFlights(JSON.parse(storedData));
    } else {
      navigate("/search");
    }
  }, [navigate]);

  return (
    <div className="h-screen bg-slate-300">
      <h1 className=" font-bold mt-1 ml-10 text-6xl">Flight Results</h1>
      <button className="bg-blue-800 cursor-pointer rounded-full text-white disabled:opacity-50 m-3 p-2 ml-10" onClick={() => navigate("/search")}>Back to search</button>
      {flights ? (
        <section className="w-full border min-h-screen bg-slate-200 flex flex-col gap-8 p-4">
          {flights.map((flight: any) => (
            <div className={`rounded-xl grid grid-cols-12 border bg-slate-100 p-4 gap-4 ${flight.returnDepartureTime != null ? " grid-rows-2" : ""} `}>
              {/*COL 1*/}
              <div className="col-span-4">
                <span>{`${flight.departureTime} - ${flight.arrivalTime}`}</span>
                <p>{`${flight.departureIata} - ${flight.arrivalIata}`}</p>
              </div>
              {/*COL 2*/}
              <div className="col-span-4">
                <span>{flight.totalDuration.split("T")[1].toLowerCase()}</span>
                <p>
                  {flight.stops.length === 0
                    ? "(Non-Stop)"
                    : `Stops: ${flight.stops.length} `}
                </p>
                {flight.stops.length > 0 && flight.stops.map((stop: any, index: number) => (
                  <div key={index}>{`${stop.airportCode} - ${stop.duration.split("T")[1].toLowerCase()}`}</div>
                ))}
              </div>
              {/*COL 3*/}
              <div className="col-span-4 row-span-2">
                <p>{`$${flight.price} ${flight.currency}`}<br />total</p>
                <br />
                <p>{`$${flight.pricePerTraveler[0]} ${flight.currency}`}<br />per Traveler</p>
              </div>
              
              {flight.returnArrivalTime != null && <div className="col-span-4">
                <span>{`${flight.returnDepartureTime} - ${flight.returnArrivalTime}`}</span>
                <p>{`${flight.returnDepartureIata} - ${flight.returnArrivalIata}`}</p>
              </div>}
              <p>Airline: {flight.carrierCode}</p>
            </div>
          ))}
        </section>
      ) : (
        <p>No flights found</p>
      )}
    </div>
  );
}

export default Flights;
