import styled from 'styled-components';

type RestaurantSearchWrapperProps = {
  type: string;
  placeholder: string;
  selectedRestaurant?: string;
};

export const RestaurantSearchWrapper = styled.input.attrs<RestaurantSearchWrapperProps>({
  type: 'text',
  placeholder: '식당을 검색해주세요.',
})`
  width: 100%;
  padding: 10px 0;
  font-size: 15px;
  border: 0;
  :focus {
    outline: none;
  }
`;

export const Wrap = styled.div`
  width: 100%;
  padding: 12px;
`;

export const SearchResult = styled.div`
  cursor: pointer;
  border-bottom: 1px solid #000;
  padding: 10px;

  &:last-child {
    border-bottom: none;
    padding-bottom: 0;
  }
`;
