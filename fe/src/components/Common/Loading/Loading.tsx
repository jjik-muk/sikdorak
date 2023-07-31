import { TEST_ID } from 'constants/testID';
import { Spinner, Wrap } from './Loading.styled';

function Loading() {
  return (
    <Wrap data-testid={TEST_ID.LOADING}>
      <Spinner />
    </Wrap>
  );
}

export default Loading;
