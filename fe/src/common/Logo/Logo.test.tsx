import { render } from "@testing-library/react";
import Logo from "./Logo";

describe("Logo", () => {
  it("로고", () => {
    const { container } = render(<Logo />);
    expect(container).toHaveTextContent("Sikdorak");
  });
});
