import { useId, useState } from 'react';
import { DETAIL } from 'styles/size';
import { createKey } from 'utils/utils';
import KeyboardArrowRightOutlinedIcon from '@mui/icons-material/KeyboardArrowRightOutlined';
import KeyboardArrowLeftOutlinedIcon from '@mui/icons-material/KeyboardArrowLeftOutlined';
import { Btns, Container, PrevBtn, NextBtn, Wrap } from './Carousel.styled';
import { TEXT } from 'constants/text';

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
          <KeyboardArrowLeftOutlinedIcon />
        </PrevBtn>
        <NextBtn onClick={handleNextBtn} disabled={isMoving || isLastIdx}>
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
          <img key={createKey(id, i)} width={DETAIL.IMG.WIDTH} height={DETAIL.IMG.HEIGHT} src={url} alt={TEXT.ALT.PHOTOGRAPH} />
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
