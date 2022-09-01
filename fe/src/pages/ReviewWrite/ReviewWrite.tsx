import { WRITE } from 'constants/size';
import Profile from 'components/ReviewDetail/UserProfile/UserProfile';
import ImgUpload from 'components/ReviewWrite/ImgUpload/ImgUpload';
import PostScope from 'components/ReviewWrite/PostScope/PostScope';
import RegistrationBtn from 'components/ReviewWrite/RegistrationBtn/RegistrationBtn';
import RestaurantSearch from 'components/ReviewWrite/RestaurantSearch/RestaurantSearch';
import SelectDay from 'components/ReviewWrite/SelectDay/SelectDay';
import TagContainer from 'components/ReviewWrite/Tag/TagContainer/TagContainer';
import Textarea from 'components/ReviewWrite/Textarea/Textarea';
import WriteRating from 'components/ReviewWrite/WriteRating/WriteRating';
import { useMyUserInfo } from 'context/MyUserInfoProvider';
import ReviewWriteProvider from 'context/ReviewWriteProvider';
import { useState } from 'react';
import { Content, Img, ImgWrap, InputWrap, Title, Wrap } from './ReviewWrite.styled';

function ReviewWrite() {
  const [selectedImg, setSelectedImg] = useState(null);
  const [userInfo] = useMyUserInfo();
  const { nickname, profileImageUrl } = userInfo;

  return (
    <ReviewWriteProvider>
      <Wrap>
        <Title>리뷰 작성하기</Title>
        <Content>
          <ImgWrap>
            {selectedImg ? (
              <Img
                width={WRITE.IMG.WIDTH}
                height={WRITE.IMG.HEIGHT}
                src={URL.createObjectURL(selectedImg)}
                alt="업로드 사진"
              />
            ) : (
              <ImgUpload setSelectedImg={setSelectedImg} />
            )}
          </ImgWrap>
          <InputWrap>
            <Profile imgUrl={profileImageUrl} nickname={nickname} />
            <Textarea />
            <RestaurantSearch />
            <SelectDay />
            <WriteRating />
            <TagContainer />
            <PostScope />
            <RegistrationBtn />
          </InputWrap>
        </Content>
      </Wrap>
    </ReviewWriteProvider>
  );
}

export default ReviewWrite;
