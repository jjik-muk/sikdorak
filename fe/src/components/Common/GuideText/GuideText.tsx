import styled from 'styled-components';

function GuideText({ text }: GuideTextProps) {
  return (
    <Wrap>
      <Text>{text}</Text>
    </Wrap>
  );
}

export default GuideText;

interface GuideTextProps {
  text: string;
}

const Wrap = styled.div``;

const Text = styled.span`
  font-size: 20px;
  font-weight: bold;
`;
