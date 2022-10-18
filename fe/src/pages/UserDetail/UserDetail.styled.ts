import { STYLE } from 'styles/style';
import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 100%;
  height: 100vh;
  overflow-y: scroll;
`;

export const UserDetailWrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')};
  width: 600px;
  margin: 20px auto 20px auto;
  padding: 20px;
  ${STYLE.BOX_CONTAINER};

  @media (max-width: 600px) {
    max-width: 100%;
  }
`;

export const UserInfoWrap = styled.div`
  width: 500px;
  margin-left: 40px;
`;

export const UserInfoHeader = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  align-items: center;
`;

export const ActivityInfoWrap = styled.div`
  ${() => flexLayoutMixin('', 'space-between')};
  width: 90%;
  margin-top: 20px;
`;

export const ProfileInfoWrap = styled.div`
  margin-top: 20px;
`;
