import { Spinner, Wrap } from './Loading.styled';

function Loading() {
  return (
    <Wrap data-testid="spinner-wrap">
      <Spinner />
    </Wrap>
  );
}

export default Loading;
