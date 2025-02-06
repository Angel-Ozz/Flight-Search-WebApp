/* eslint-disable @typescript-eslint/no-explicit-any */
import React from 'react';

interface FormInput {
    type: React.HTMLInputTypeAttribute
    name: string
    returnDate?: boolean
    setState: any
    min?: string | number 
    required?: boolean
    defaultValue?: string | number
}

const FormInput: React.FC<FormInput> = ({ type, name, setState, min, required, defaultValue }) => {
    return (
    <input className='border w-48 md:w-52 m-2' type={type} name={name}  
    min={type === "number" ? (min as number) : (min as string)}  required={required} onChange={(value) => setState(value.target.value)} defaultValue = {defaultValue} />
    );
}

export default FormInput;
