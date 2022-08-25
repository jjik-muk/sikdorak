import styled from 'styled-components';
import { flexLayoutMixin } from 'utils/utils';

export const Wrap = styled.div`
  width: 100%;
  height: 100%;
`;

export const UserDetailWrap = styled.div`
  ${() => flexLayoutMixin('', 'center', 'center')};
  margin-top: 40px;
`;

export const UserInfoWrap = styled.div`
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
