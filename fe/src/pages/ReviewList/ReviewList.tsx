import { FEEDS } from 'constants/dummyData';
import CommonHeader from 'components/Common/CommonHeader';
import Feed from 'components/ReviewList/Feed/Feed';
import { useId } from 'react';
import { createKey } from 'utils/utils';
import { Wrap } from './ReviewList.styled';

function ReviewList() {
  const id = useId();

  return (
    <>
      <CommonHeader />
      <Wrap>
        {FEEDS.map(({ author, contents, pictures, store, likeCnt }, i) => (
          <Feed
            key={createKey(id, i)}
            author={author}
            contents={contents}
            pictures={pictures}
            store={store}
            likeCnt={likeCnt}
          />
        ))}
      </Wrap>
    </>
  );
}

export default ReviewList;
