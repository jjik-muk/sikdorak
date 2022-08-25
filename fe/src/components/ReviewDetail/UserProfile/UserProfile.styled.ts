import styled from 'styled-components';

export const Wrap = styled.div`
  display: flex;
  gap: 10px;
  padding: 10px;
  cursor: pointer;
`;

// TODO: width, height 매직 넘버 추출
export const Picture = styled.img`
  width: 42px;
  height: 42px;
`;

export const Nickname = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`;
