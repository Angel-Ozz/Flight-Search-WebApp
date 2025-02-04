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
      navigate("/");
    }
  }, [navigate]);

  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <h1 className="text-2xl font-bold">Flight Results</h1>
      {flights ? (
        <pre className="text-left">{JSON.stringify(flights, null, 2)}</pre>
      ) : (
        <p>No flights found</p>
      )}
    </div>
  );
}

export default Flights;
