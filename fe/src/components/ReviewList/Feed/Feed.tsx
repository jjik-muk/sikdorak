import { DETAIL, FEED } from 'constants/size';
import Icon from 'common/Icon';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import TotalRating from 'components/ReviewDetail/TotalRating/TotalRating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import useToggle from 'hooks/useToggle';
import { createKey } from 'utils/utils';
import {
  ButtonWrapper,
  Contents,
  ContentsWrap,
  Header,
  IconWrap,
  Main,
  MainFooter,
  Pictures,
  Wrap,
} from './Feed.styled';

function Feed({ author, contents, rating, pictures, store, likeCnt }: FeedProps) {
  const [isActiveHeart, toggleIsActiveHeart] = useToggle(false);
  const [isActiveMenu, toggleIsActiveMenu] = useToggle(false);
  const [taste, price, service] = rating;
  const { name, region } = store;

  return (
    <Wrap>
      <ContentsWrap wrapWidth={DETAIL.WRAP.WIDTH_NO_IMG}>
        <Header>
          <UserProfile nickname={author} />
          <div onClick={toggleIsActiveMenu}>
            <Icon icon="MenuBtn" />
            {isActiveMenu && <Menu />}
          </div>
        </Header>
        <Main>
          <Contents>{contents}</Contents>
          <Pictures>
            {pictures &&
              pictures.map((picture, i) => (
                <img
                  key={createKey(picture, i)}
                  src={picture}
                  alt="음식"
                  width={FEED.IMG.WIDTH}
                  height={FEED.IMG.HEIGHT}
                />
              ))}
          </Pictures>
          <TotalRating taste={taste} price={price} service={service} />
          <MainFooter>
            <CompnayProfile company={name} region={region} />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <div onClick={toggleIsActiveHeart}>
            <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
              {likeCnt}
            </IconWrap>
          </div>
          <div>
            <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
              <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={handleCopyURL}>
            <IconWrap width={FEED.BTN.WIDTH_NO_IMG} height={FEED.BTN.HEIGHT}>
              <Icon icon="ShareArrow" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
        </ButtonWrapper>
      </ContentsWrap>
    </Wrap>
  );

  function handleCopyURL() {
    const curURL = window.location.href;
    const { clipboard } = navigator;
    clipboard.writeText(curURL);
  }
}

export default Feed;

type FeedProps = {
  author: string;
  contents: string;
  rating: number[];
  pictures?: string[];
  store: { name: string; region: string };
  likeCnt: number;
};
