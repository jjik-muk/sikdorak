import { useId, useState } from 'react';
import { createKey } from 'utils/utils';
import { Btns, Container, PrevBtn, NextBtn, Wrap } from './Carousel.styled';

function Carousel({ urls }: { urls: string[] }) {
  const [idx, setIdx] = useState(0);
  const [isMoving, setIsMoving] = useState(false);
  const id = useId();
  const IMAGE_CNT = urls.length;
  const isFirstIdx = idx === 0;
  const isLastIdx = idx === IMAGE_CNT - 1;

  return (
    <Wrap>
      <Btns>
        <PrevBtn onClick={handlePrevBtn} disabled={isMoving || isFirstIdx}>
          {'<'}
        </PrevBtn>
        <NextBtn onClick={handleNextBtn} disabled={isMoving || isLastIdx}>
          {'>'}
        </NextBtn>
      </Btns>
      <Container
        idx={idx}
        onTransitionEnd={() => {
          setIsMoving(false);
        }}
      >
        {urls.map((url, i) => {
          const PICTURE_SIZE = 600;
          return <img key={createKey(id, i)} width={PICTURE_SIZE} height={PICTURE_SIZE} src={url} alt="food" />;
        })}
      </Container>
    </Wrap>
  );

  function handlePrevBtn() {
    if (isFirstIdx) {
      return;
    }
    setIsMoving(true);
    setIdx(idx - 1);
  }
  function handleNextBtn() {
    if (isLastIdx) {
      return;
    }
    setIsMoving(true);
    setIdx(idx + 1);
  }
}

export default Carousel;
