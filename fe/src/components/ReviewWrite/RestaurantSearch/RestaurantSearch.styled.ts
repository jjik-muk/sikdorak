import styled from 'styled-components';

type RestaurantSearchWrapperProps = {
  type: string;
  placeholder: string;
  selectedRestaurant?: string;
};

export const RestaurantSearchInput = styled.input.attrs<RestaurantSearchWrapperProps>({
  type: 'text',
  placeholder: '식당을 검색해주세요.',
})`
  width: 100%;
  padding: 10px 0;
  font-size: 15px;
  border: 0;
  cursor: pointer;
  :focus {
    outline: none;
  }
`;

export const Wrap = styled.div`
  width: 100%;
  padding: 12px;
  position: relative;
`;
