import { FEEDS } from 'constants/dummyData';
import Feed from 'components/ReviewList/Feed/Feed';
import { Wrap } from './ReviewList.styled';

function ReviewList() {
  return (
    <Wrap>
      {FEEDS.map(({ author, contents, rating, pictures, store, likeCnt }) => (
        <Feed author={author} contents={contents} rating={rating} pictures={pictures} store={store} likeCnt={likeCnt} />
      ))}
    </Wrap>
  );
}

export default ReviewList;
