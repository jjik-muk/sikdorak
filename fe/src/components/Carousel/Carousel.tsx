import { useState } from 'react';
import { Btns, Container, LeftBtn, RightBtn, Wrap } from './Carousel.styled';

const PICTURE_SIZE = 600;

function Carousel() {
  const [idx, setIdx] = useState(0);
  const [isMoving, setIsMoving] = useState(false);
  const isFirstIdx = idx === 0;
  const isLastIdx = idx === 2;

  return (
    <Wrap>
      <Btns>
        <LeftBtn
          onClick={() => {
            setIsMoving(true);
            if (isFirstIdx) {
              return;
            }
            setIdx(idx - 1);
          }}
          disabled={isMoving || isFirstIdx}
        >
          왼
        </LeftBtn>
        <RightBtn
          onClick={() => {
            setIsMoving(true);
            if (isLastIdx) {
              return;
            }
            setIdx(idx + 1);
          }}
          disabled={isMoving || isLastIdx}
        >
          오
        </RightBtn>
      </Btns>
      <Container
        idx={idx}
        onTransitionEnd={() => {
          setIsMoving(false);
        }}
      >
        <img
          width={PICTURE_SIZE}
          height={PICTURE_SIZE}
          src="https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fpup-review-phinf.pstatic.net%2FMjAyMjA4MDZfMTcz%2FMDAxNjU5Nzc1MjY5NTM1.32oPwiMedK1mju63VblxPU5HmHcUBBgKmmAVoyautagg.K-5xc3519k7Gssf7KsQmTzO0SYVOgnALzCbq0KqcEKMg.JPEG%2Fupload_38bd8f36f2e2f5171c919ad023d781ec.jpg"
          alt="food"
        />
        <img
          width={PICTURE_SIZE}
          height={PICTURE_SIZE}
          src="https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fpup-review-phinf.pstatic.net%2FMjAyMjA4MDZfMTA1%2FMDAxNjU5Nzc1MzE2NDcy.q763Kdof6MWq8uks_dQYn_MuWBpUmVu6TsT0m_webY0g.29IDOtAZ3OeL-ki7mib1vSrCDqImSN9h0yb-qwgNr68g.JPEG%2Fupload_c6f3874bbc244100e94e689cf65b2e62.jpg"
          alt="food"
        />
        <img
          width={PICTURE_SIZE}
          height={PICTURE_SIZE}
          src="https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjA3MjRfMjQ2%2FMDAxNjU4NjY1MDM5MDI1.qlYgPI8MpYbRrP-xlA_MFm4eaAxgpwNFWqLYpaixKUAg.8pFxhYlrVrIRVi0vL6JM7JOSeiMzZ6YrnWU5P-fEkaMg.JPEG.jiyxxnism%2F220509_3.jpg"
          alt="food"
        />
      </Container>
    </Wrap>
  );
}

export default Carousel;
