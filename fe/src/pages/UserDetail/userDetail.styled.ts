import { STYLE } from 'constants/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: fit-content;
  height: 100%;
  margin: 0 auto;
`;

export const UserDetailWrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')};
  width: 800px;
  margin: 20px auto 20px auto;
  padding: 20px 0 20px 0;
  ${STYLE.BOX_CONTAINER};
`;

export const UserInfoWrap = styled.div`
  width: 500px;
  margin-left: 40px;
`;

export const UserInfoHeader = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
`;

export const ActivityInfoWrap = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  margin-top: 20px;
`;

export const ProfileInfoWrap = styled.div`
  margin-top: 20px;
`;

export const FeedWrap = styled.div`
  width: fit-content;
  margin: 0 auto;
`;
