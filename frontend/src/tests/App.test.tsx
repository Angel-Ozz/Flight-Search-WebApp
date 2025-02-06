import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router";
import { FlightsProvider } from "../context/FlightsContext";
import App from "../App";
import { describe, it, expect } from "vitest";
import React from "react";



describe("App Component", () => {

    it("renders Flight Search heading", () => {
        render(
        <FlightsProvider>
            <MemoryRouter>
            <App />
            </MemoryRouter>
        </FlightsProvider>
        );

        expect(screen.getByText(/Flight Search/i)).toBeInTheDocument();
    });

    it("updates dparture date", () => {
        render(
            <FlightsProvider>
                <MemoryRouter>
                    <App />
                </MemoryRouter>
            </FlightsProvider>
            );
        
        const departureDateInput = screen.getByLabelText(/Departure Date/i);
        const departureDate = "2025-03-20";
        fireEvent.change(departureDateInput, { target: { value: departureDate } });
        expect(departureDateInput).toHaveValue(departureDate);
        });

        
    it("updates return date", () => {
        render(
            <FlightsProvider>
                <MemoryRouter>
                    <App />
                </MemoryRouter>
            </FlightsProvider>
            );
        
        const departureDateInput = screen.getByLabelText(/Return Date/i);
        const departureDate = "2025-03-20";
        fireEvent.change(departureDateInput, { target: { value: departureDate } });
        expect(departureDateInput).toHaveValue(departureDate);
        });

    it("updates number of adults when input is changed", () => {
        render(
        <FlightsProvider>
            <MemoryRouter>
            <App />
            </MemoryRouter>
        </FlightsProvider>
        );


        const adultsInput = screen.getByLabelText(/Adults/i) as HTMLInputElement;
        fireEvent.change(adultsInput, { target: { value: "2" } });
        expect(adultsInput.value).toBe("2");
    });

    it("updates currency when user selects a different option", () => {
        render(
        <FlightsProvider>
            <MemoryRouter>
            <App />
            </MemoryRouter>
        </FlightsProvider>
        );

        const currencySelect = screen.getByLabelText(/Currency/i) as HTMLSelectElement;
        fireEvent.change(currencySelect, { target: { value: "EUR" } });
        expect(currencySelect.value).toBe("EUR");

    });

    it("toggles non-stop checkbox", () => {
        render(
        <FlightsProvider>
            <MemoryRouter>
            <App />
            </MemoryRouter>
        </FlightsProvider>
        );

        const checkbox = screen.getByLabelText(/Non-stop/i);
        fireEvent.click(checkbox);
        expect(checkbox).toBeChecked();
    });

});