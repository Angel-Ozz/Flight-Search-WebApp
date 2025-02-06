import { render, screen, fireEvent } from "@testing-library/react";
import Switcher3 from "../components/Switch3";
import { describe, it, expect, vi } from "vitest";

describe("Switcher3 Component", () => {
  

    it("calls onChange when toggled", () => {
        const mockOnChange = vi.fn();
        render(<Switcher3 checked={false} onChange={mockOnChange} />);

        const switchElement = screen.getByRole("checkbox", { hidden: true });

        fireEvent.click(switchElement);

        expect(mockOnChange).toHaveBeenCalledTimes(1);
        expect(mockOnChange).toHaveBeenCalledWith(true); 
    });

    it("toggles correctly when clicked", () => {
        let checked = false;
        const mockOnChange = vi.fn((newChecked) => {
        checked = newChecked;
        });

        const { rerender } = render(<Switcher3 checked={checked} onChange={mockOnChange} />);

        const switchElement = screen.getByRole("checkbox", { hidden: true });

        fireEvent.click(switchElement);
        expect(mockOnChange).toHaveBeenCalledWith(true);


        rerender(<Switcher3 checked={true} onChange={mockOnChange} />);
        fireEvent.click(switchElement);
        expect(mockOnChange).toHaveBeenCalledWith(false);
    });
});
