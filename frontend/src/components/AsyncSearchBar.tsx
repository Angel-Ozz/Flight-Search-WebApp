/* eslint-disable @typescript-eslint/no-explicit-any */

import { useState } from "react";
import AsyncSelect from "react-select/async";
import makeAnimated from "react-select/animated";

type Props = {
  setState: any
}

const AsyncSearchBar = ({setState}:Props) => {


  const [query, setQuery] = useState("");


  const animatedComponents = makeAnimated();


  const loadOptions = () => {
    return fetch(`http://localhost:8080/flights/airport?keyword=${query}`)
    .then((res) => res.json()).then((data)=> {
        return data.data
    });
  };

  return (
    <>
      <AsyncSelect
      className="w-48 md:w-52 m-2"
        cacheOptions
        components={animatedComponents}
        getOptionLabel={(e:any) => e.iataCode + " | " + e.name}
        getOptionValue={(e:any) => e.iataCode}
        loadOptions={loadOptions}
        onInputChange={(value) => setQuery(value)}
        onChange={(value) => setState(value.iataCode)}
        required
      />
    </>
  );
};

export default AsyncSearchBar;