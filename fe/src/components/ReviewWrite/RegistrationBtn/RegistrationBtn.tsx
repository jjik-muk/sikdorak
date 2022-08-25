import { DOMAIN } from 'constants/dummyData';
import { useReviewWrite } from 'context/reviewWriteProvider';
import { httpRequest } from 'utils/utils';
import { Wrap } from './RegistrationBtn.styled';

function RegistrationBtn() {
  const [reviewWriteState] = useReviewWrite();
  return (
    <Wrap>
      <div onClick={registerPost}>게시</div>
    </Wrap>
  );

  function registerPost() {
    const { content, year, month, date, rating, scope, tags, id } = reviewWriteState;

    if (year === 0 && month === 0 && date === 0) {
      // TODO: 방문일 선택 경고 만들어서 추가하기
      alert('방문일 선택하세요^^');
      return;
    }
    if (!id) {
      // TODO: 식당 미입력 경고 만들어서 추가하기
      alert('식당 선택하세요^^');
      return;
    }

    const bodyData = {
      reviewContent: content,
      storeId: id,
      reviewScore: rating,
      reviewVisibility: scope,
      visitedDate: `${year}-${month.padStart(2, '0')}-${date.padStart(2, '0')}`,
      tags,
      images: [],
    };
    const accessToken = localStorage.getItem('accessToken');
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    httpRequest(`${DOMAIN}/api/reviews`, { headers, method: 'POST', bodyData });
  }
}

export default RegistrationBtn;
