import styled from 'styled-components';

export const Wrap = styled.div`
  width: 600px;
  height: 600px;
  overflow: hidden;
`;

const PICTURE_SIZE = 600;

export const Container = styled.ul<{ idx: number }>`
  display: flex;
  width: fit-content;
  transform: ${(props) => `translateX(${-PICTURE_SIZE * props.idx}px)`};
  transition: 0.25s;
`;

export const Btns = styled.div`
  display: flex;
  justify-content: space-between;
  position: absolute;
  top: 300px;
  left: 30px;
  width: 580px;
  z-index: 1;
`;

export const LeftBtn = styled.button`
  border-radius: 100%;
  width: 30px;
  height: 30px;
`;

export const RightBtn = styled.button`
  border-radius: 100%;
  width: 30px;
  height: 30px;
`;
