import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import CommonHeader from 'components/Common/CommonHeader/CommonHeader';
import RestaurantListTab from 'components/Map/RestaurantListTab/RestaurantListTab';
import UserReviewTab from 'components/Map/UserReviewTab/UserReviewTab';
import useAuth from 'hooks/useAuth';
import useReviews from 'hooks/useReviews';
import { useState } from 'react';
import { createKey } from 'utils/utils';
import { ContentArea } from './Map.styled';

const TABS = [{ label: '가게 목록' }, { label: '유저 리뷰' }];

function Map() {
  const [activeTabIdx, setActiveTabIdx] = useState(0);
  const { dispatchReviews } = useReviews();
  useAuth();
  const isSelectedRestaurantList = activeTabIdx === 0;
  const isSelectedUserReviews = activeTabIdx === 1;

  return (
    <>
      <CommonHeader dispatchReviews={dispatchReviews} />
      <ContentArea>
        <Tabs orientation="vertical" variant="scrollable" value={activeTabIdx} sx={{ borderRight: 1, borderColor: 'divider' }}>
          {TABS.map(({ label }, idx) => (
            <div key={createKey(label, idx)}>
              <Tab
                label={label}
                onClick={() => {
                  setActiveTabIdx(idx);
                }}
              />
            </div>
          ))}
        </Tabs>
        {isSelectedRestaurantList && <RestaurantListTab activeTabIdx={activeTabIdx} />}
        {isSelectedUserReviews && <UserReviewTab />}
      </ContentArea>
    </>
  );
}

export default Map;
