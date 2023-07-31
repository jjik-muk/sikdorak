import { useId, useState } from 'react';
import { DETAIL } from 'styles/size';
import { createKey } from 'utils/utils';
import KeyboardArrowRightOutlinedIcon from '@mui/icons-material/KeyboardArrowRightOutlined';
import KeyboardArrowLeftOutlinedIcon from '@mui/icons-material/KeyboardArrowLeftOutlined';
import { Btns, Container, PrevBtn, NextBtn, Wrap } from './Carousel.styled';
import { TEST_ID } from 'constants/testID';
import { ALT } from 'constants/alt';

function Carousel({ urls }: { urls: string[] }) {
  const [idx, setIdx] = useState(0);
  const [isMoving, setIsMoving] = useState(false);
  const id = useId();
  const imagesLength = urls.length;
  const isFirstIdx = idx === 0;
  const isLastIdx = idx === imagesLength - 1;

  return (
    <Wrap>
      <Btns>
        <PrevBtn onClick={handlePrevBtn} disabled={isMoving || isFirstIdx} data-testid={TEST_ID.PREV_BTN}>
          <KeyboardArrowLeftOutlinedIcon />
        </PrevBtn>
        <NextBtn onClick={handleNextBtn} disabled={isMoving || isLastIdx} data-testid={TEST_ID.NEXT_BTN}>
          <KeyboardArrowRightOutlinedIcon />
        </NextBtn>
      </Btns>
      <Container
        idx={idx}
        onTransitionEnd={() => {
          setIsMoving(false);
        }}
      >
        {urls.map((url, i) => (
          <img key={createKey(id, i)} width={DETAIL.IMG.WIDTH} height={DETAIL.IMG.HEIGHT} src={url} alt={ALT.PHOTOGRAPH} />
        ))}
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
