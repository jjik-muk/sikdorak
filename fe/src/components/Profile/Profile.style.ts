import styled from 'styled-components';

const Wrap = styled.div`
  display: flex;
  gap: 10px;
`;

// TODO: width, height 매직 넘버 추출
const Picture = styled.img`
  width: 42px;
  height: 42px;
`;

const Nickname = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;

export { Wrap, Picture, Nickname };
