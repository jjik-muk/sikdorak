import { CompanyName, Picture, Wrap, Region } from './CompanyProfile.styled';

const DEFAULT_IMG_URL = 'https://flyclipart.com/thumb2/profile-user-png-icon-free-download-196388.png';

function CompnayProfile({ imgUrl = DEFAULT_IMG_URL, company, region }: ProfileProps) {
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

export default CompnayProfile;

interface ProfileProps {
  imgUrl?: string;
  company: string;
  region: string;
}
