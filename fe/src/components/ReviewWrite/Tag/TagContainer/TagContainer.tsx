import { STYLE } from 'constants/style';
import { useReviewWrite } from 'context/ReviewWriteProvider';
import { useOutsideClick } from 'hooks/useOutsideClick';
import { useRef, useState } from 'react';
import styled from 'styled-components';
import { createKey } from 'utils/utils';
import TagInput from '../TagInput/TagInput';
import { Item, Wrap } from './TagContainer.styled';

function TagContainer() {
  const [reviewWriteState] = useReviewWrite();
  const { tags } = reviewWriteState;

  return (
    <Wrap>
      {tags.map((tag: string, idx: number) => (
        <div key={createKey(tag, idx)}>
          <TagItem idx={idx} value={tag} />
        </div>
      ))}
      <TagInput />
    </Wrap>
  );
}

export default TagContainer;

function TagItem({ idx, value }: any) {
  const [isModifing, setIsModifing] = useState(false);
  const [inputValue, setInputValue] = useState(value);
  const inputRef = useRef(null);
  const [, dispatchReviewWrite] = useReviewWrite();
  useOutsideClick(inputRef, () => {
    setIsModifing(false);
  });
  return isModifing ? (
    <Input
      ref={inputRef}
      value={inputValue}
      onChange={(e) => setInputValue(e.target.value)}
      onKeyDown={(e) => {
        const isPressEnter = e.key === 'Enter';
        if (isPressEnter) {
          dispatchReviewWrite({ type: 'MODIFY_TAG', tagIdx: idx, newTag: inputValue });
        }
      }}
    />
  ) : (
    <Item
      onClick={() => {
        setIsModifing(true);
      }}
    >
      #{value}
    </Item>
  );
}

const Input = styled.input`
  width: 120px;
  ${STYLE.BOX_CONTAINER}
  height: 30px;
`;
