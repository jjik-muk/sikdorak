import { CompanyName, Picture, Wrap, Region } from './RestaurantProfile.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function RestaurantProfile({ imgUrl = DEFAULT_IMG_URL, company, region }: RestaurantProfileProps) {
  return (
    <Wrap>
      <Picture src={imgUrl} />
      <div>
        <CompanyName>{company}</CompanyName>
        <Region>{region}</Region>
      </div>
    </Wrap>
  );
}

export default RestaurantProfile;

type RestaurantProfileProps = {
  imgUrl?: string;
  company: string;
  region: string;
};
