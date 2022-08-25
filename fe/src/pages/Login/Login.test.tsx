import { render } from "@testing-library/react";
import Login from "./Login";

describe("App", () => {
  it("렌더", () => {
    const { container } = render(<Login />);

    expect(container).toHaveTextContent("아이디로 로그인");
  });
});
