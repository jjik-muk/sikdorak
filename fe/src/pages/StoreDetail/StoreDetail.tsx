import { FEEDS } from 'constants/dummyData';
import Icon from 'common/Icon';
import CommonHeader from 'components/Common/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import {
  DimText,
  InfoWrap,
  MoreDim,
  OthersPicture,
  PictureWrap,
  Row,
  StoreInfo,
  Text,
  Title,
  Wrap,
} from './StoreDetail.styled';

function StoreDetail({ pictures }: StoreDetailProps) {
  const imgUrls = pictures.slice(0, 5);

  return (
    <>
      <CommonHeader />
      <Wrap>
        <PictureWrap>
          <div>
            <img src={imgUrls[0]} alt="음식" width={400} height={400} />
          </div>
          <OthersPicture>
            {imgUrls.slice(1).map((picture) => (
              <img src={picture} alt="음식" width={200} height={200} />
            ))}
          </OthersPicture>
          <MoreDim />
          <DimText>더 보기</DimText>
        </PictureWrap>
        <StoreInfo>
          <InfoWrap>
            <Title>치즈룸과 테이스팅룸 롯데월드몰</Title>
            <Row>
              <Icon icon="Star" stroke="#fff" fill="#f1c40f" width={15} height={15} />
              <Text>4.5 / 5</Text>
              <Text>리뷰 5,299</Text>
            </Row>
            <Row>
              <Icon icon="Location" width={15} height={15} fill="#000" />
              <Text>481 Effertz Walks</Text>
            </Row>
            <Row>
              <Icon icon="Phone" width={15} height={15} fill="#000" />
              <Text>02-3213-4550</Text>
            </Row>
          </InfoWrap>
        </StoreInfo>
        {FEEDS.map(({ author, contents, rating, store, likeCnt }) => (
          <Feed author={author} contents={contents} rating={rating} store={store} likeCnt={likeCnt} />
        ))}
      </Wrap>
    </>
  );
}

export default StoreDetail;

type StoreDetailProps = {
  pictures?: string[];
};
