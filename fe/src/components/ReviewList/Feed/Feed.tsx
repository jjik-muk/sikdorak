import Icon from 'common/Icon';
import Menu from 'components/ReviewDetail/Menu/Menu';
import CompnayProfile from 'components/ReviewDetail/RestaurantProfile/RestaurantProfile';
import TotalRating from 'components/ReviewDetail/TotalRating/TotalRating';
import UserProfile from 'components/ReviewDetail/UserProfile/UserProfile';
import { useState } from 'react';
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
  const [isActiveHeart, setIsActiveHeart] = useState(false);
  const [isActiveMenu, setIsActiveMenu] = useState(false);
  const [taste, price, service] = rating;
  const { name, region } = store;

  return (
    <Wrap>
      <ContentsWrap wrapWidth={750}>
        <Header>
          <UserProfile nickname={author} />
          <div onClick={handleMenu}>
            <Icon icon="MenuBtn" />
            {isActiveMenu && <Menu />}
          </div>
        </Header>
        <Main>
          <Contents>{contents}</Contents>
          <Pictures>
            {pictures.map((picture) => (
              <img src={picture} alt="음식" width={100} height={70} />
            ))}
          </Pictures>
          <TotalRating taste={taste} price={price} service={service} />
          <MainFooter>
            <CompnayProfile company={name} region={region} />
          </MainFooter>
        </Main>
        <ButtonWrapper>
          <div onClick={handleToggleHeart}>
            <IconWrap width={190} height={30}>
              <Icon icon="Heart" fill={isActiveHeart ? 'red' : '#FFF'} />
              {likeCnt}
            </IconWrap>
          </div>
          <div>
            <IconWrap width={190} height={30}>
              <Icon icon="TalkBubble" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
          <div onClick={handleCopyURL}>
            <IconWrap width={190} height={30}>
              <Icon icon="ShareArrow" fill={isActiveHeart ? 'red' : '#FFF'} />
            </IconWrap>
          </div>
        </ButtonWrapper>
      </ContentsWrap>
    </Wrap>
  );

  function handleMenu() {
    setIsActiveMenu(!isActiveMenu);
  }

  function handleToggleHeart() {
    setIsActiveHeart(!isActiveHeart);
  }

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
