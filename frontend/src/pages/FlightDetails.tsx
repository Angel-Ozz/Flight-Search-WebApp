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
    }, [])


    return (
        <div>
            {flight ? (
                <section className="w-full border min-h-screen bg- flex flex-col gap-8 p-4  bg-slate-100">
                    <div className="flex justify-between items-center">
                        <button className="bg-blue-800 cursor-pointer rounded-full text-white disabled:opacity-50 m-3 p-2 ml-10" onClick={() => navigate("/flights")}>Back to flights</button>
                    </div>
                    <div className={`rounded-xl grid grid-cols-12 border bg-slate-400 p-4 gap-4 ${flight.returnDepartureTime ? " grid-rows-2" : ""}`}>
                        {/* COL 1 */}
                        <div className="col-span-4">
                            <span>{`${flight.departureTime} - ${flight.arrivalTime}`}</span>
                            <p>{`${flight.departureIata} - ${flight.arrivalIata}`}</p>
                        </div>
                        {/* COL 2 */}
                        <div className="col-span-4">
                            {flight.stops.length != 0 ? flight.stops.map((stop: any, index: number) => (
                                <div key={index}>{`${stop.airportCode} - ${stop.duration.split("T")[1].toLowerCase()}`}</div>
                            )) : <p>NonStop</p>}
                        </div>
                        {/* COL 3 */}
                        <div className="col-span-4 row-span-2">
                            <p>{`$${flight.price} ${flight.currency}`}<br />total</p>
                            <br />
                        </div>
                        {flight.returnArrivalTime && (
                            <div className="col-span-4">
                                <span>{`${flight.returnDepartureTime} - ${flight.returnArrivalTime}`}</span>
                                <p>{`${flight.returnDepartureIata} - ${flight.returnArrivalIata}`}</p>
                            </div>
                        )}
                        <p>Airline: {flight.carrierCode}</p>
                    </div>
                    {flight.segments?.map((segment: any, index: any) => (
                        <div key={index} className='grid grid-cols-12 rounded-xl p-4 bg-slate-300'>
                            <div className='col-span-7'>
                                <h3>Segment {index + 1}</h3>
                                <p>{segment.departureTime} - {segment.arrivalTime}</p>
                                <p>Airline: {segment.airlineCode}</p>
                                <p>Aircraft type: {segment.aircraftType}</p>
                                <p>Cabin: {segment.cabin}</p>
                                <p>Flight class: {segment.flightClass}</p>
                                <p>Flight number: {segment.flightNumber}</p>
                                <p></p><p>Layover time: {segment.layoverTime != "PT0S" ? <span>{segment.layoverTime}</span>:<span>0</span>}</p>

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
                        <p>Base: ${flight.base}</p>
                        <p>Price: ${flight.price}</p>
                        <p>Price per Traveler: ${flight.pricePerTraveler[0]}</p>
                        <p>Fees</p>
                        {flight.fees?.map((fee:any, index:any) => (
                            <p key={index}>{fee.type.toLowerCase()} ${fee.amount}</p>
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