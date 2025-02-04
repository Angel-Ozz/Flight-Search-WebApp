import React, { ReactNode } from 'react';

interface FormInputSectionProps {
  children: ReactNode;
}

const FormInputSection: React.FC<FormInputSectionProps> = ({ children }) => {
  return (
    <div className='flex justify-between' >
      {children}
    </div>
  );
};

export default FormInputSection;
