/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router'

const FlightDetails = () => {
    const navigate = useNavigate()
    const [flight, setFlightData] = useState<any>()

    useEffect(() => {
        const flightData = localStorage.getItem("flight_info") || ""
        if (!flightData) {
            navigate("/search")
        }
        setFlightData(JSON.parse(flightData))
        console.log(JSON.parse(flightData))
    }, [navigate])


    return (
        <div>
            {flight ? (
                <section className="w-full border min-h-screen bg- flex flex-col gap-8 p-4  bg-slate-100">
                    <div className="flex justify-between items-center">
                        <button className="bg-blue-800 cursor-pointer rounded-full text-white disabled:opacity-50 m-3 p-2 ml-10" onClick={() => navigate("/flights")}>Back to flights</button>
                    </div>
                    <div className={`rounded-xl grid grid-cols-12 border bg-slate-400 p-4 gap-4 ${flight.returnDepartureTime ? " grid-rows" : ""}`}>
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
                    {flight.segments?.map((segment: any, index: any) => (
                        <div key={index} className='grid grid-cols-12 rounded-xl p-4 bg-slate-300'>
                            <div className='col-span-7'>
                                <h3>Segment {index + 1}</h3>
                                <p>
                                    {`${new Date(segment.departureTime).toLocaleString('es-ES', { 
                                    day: '2-digit', month: '2-digit', year: 'numeric', 
                                    hour: '2-digit', minute: '2-digit' 
                                })} - 
                                    ${new Date(segment.arrivalTime).toLocaleString('es-ES', { 
                                    day: '2-digit', month: '2-digit', year: 'numeric', 
                                    hour: '2-digit', minute: '2-digit' 
                                })}`}
                                </p>
                                <p>Airline: {segment.airlineCode}</p>
                                <p>Aircraft type: {segment.aircraftType}</p>
                                <p>Cabin: {segment.cabin}</p>
                                <p>Flight class: {segment.flightClass}</p>
                                <p>Flight number: {segment.flightNumber}</p>
                                <p></p><p>Layover time: {segment.layoverTime != "PT0S" ? <span>{segment.layoverTime.split("T")[1].toLowerCase()}</span>:<span>0</span>}</p>

                            </div>
                            <div className='col-span-5'>
                                <h3 className='font-bold'>Amenities</h3>
                                {segment.amenities?.map((amenity:any)=>(
                                    <p>{amenity.description} {amenity.isChargeable ? <span className='font-bold'>- Chargeable </span> : <span className='font-bold'>- Included</span>}</p>
                                ))}
                            </div>
                        </div>
                    ))}
                    {flight.returnSegments?.map((returnSegment: any, index: any) => (
                        <div key={index} className='grid grid-cols-12 rounded-xl p-4 bg-slate-300'> 
                            <div className='col-span-7'>
                                <h3>Segment {index + 1}</h3>
                                <p>{returnSegment.departureTime} - {returnSegment.arrivalTime}</p>
                                <p>Airline: {returnSegment.airlineCode}</p>
                                <p>Aircraft type: {returnSegment.aircraftType}</p>
                                <p>Cabin: {returnSegment.cabin}</p>
                                <p>Flight class: {returnSegment.flightClass}</p>
                                <p>Flight number: {returnSegment.flightNumber}</p>
                                <p></p><p>Layover time: {returnSegment.layoverTime != "PT0S" ? <span>{returnSegment.layoverTime}</span>:<span>0</span>}</p>

                            </div>
                            <div className='col-span-5'>
                                <h3 className='font-bold'>Amenities</h3>
                                {returnSegment.amenities?.map((amenity:any)=>(
                                    <p>{amenity.description} {amenity.isChargeable ? <span className='font-bold'>- Chargeable </span> : <span className='font-bold'>- Included</span>}</p>
                                ))}
                            </div>
                        </div>
                    ))}
                    <div className='bg-slate-200 p-4 rounded-xl'>
                        <h2>Price Breakdown </h2>
                        <p>Base: ${flight.base + " " + flight.currency}</p>
                        <p>Price: ${flight.price + " " + flight.currency}</p>
                        <p>Price per Traveler: ${flight.pricePerTraveler[0] + " " +flight.currency}</p>
                        <p>Fees</p>
                        {flight.fees?.map((fee:any, index:any) => (
                            <p key={index}>{fee.type.toLowerCase()} ${fee.amount + " " + flight.currency}</p>
                            ))}
                        



                    </div>
                </section>
            ) : (
                <p>No flights found</p>
            )}
        </div>
    )
}

export default FlightDetails