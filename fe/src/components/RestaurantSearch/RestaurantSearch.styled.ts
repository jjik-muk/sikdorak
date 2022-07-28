import styled from 'styled-components';

interface RestaurantSearchWrapperProps {
  type: string;
  placeholder: string;
}

export const RestaurantSearchWrapper = styled.input.attrs<RestaurantSearchWrapperProps>({
  type: 'text',
  placeholder: '식당을 검색해주세요.',
})`
  width: 100%;
  padding: 10px;
  font-size: 15px;
  border-color: #d4d4d4;
  :focus {
    outline: none;
  }
`;
