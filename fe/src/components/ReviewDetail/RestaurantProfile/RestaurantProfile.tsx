import { useNavigate } from 'react-router-dom';
import { CompanyName, Wrap, Region } from './RestaurantProfile.styled';
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu';
import { COLOR } from 'styles/color';
import { ICON } from 'styles/size';
import { TEST_ID } from 'constants/testID';

function RestaurantProfile({ company, region, storeId }: RestaurantProfileProps) {
  const navigate = useNavigate();

  return (
    <Wrap
      onClick={(e) => {
        e.stopPropagation();
        navigate(`/store/${storeId}`);
      }}
      data-testid={TEST_ID.RESTAURANT_PROFILE}
    >
      <RestaurantMenuIcon style={{ border: `1px solid ${COLOR.GREY[100]}`, borderRadius: '50%', width: `${ICON.MEDIUM}px`, height: `${ICON.MEDIUM}px` }} />
      <div>
        <CompanyName>{company}</CompanyName>
        <Region>{region}</Region>
      </div>
    </Wrap>
  );
}

export default RestaurantProfile;

type RestaurantProfileProps = {
  company: string;
  region: string;
  storeId?: number;
};
