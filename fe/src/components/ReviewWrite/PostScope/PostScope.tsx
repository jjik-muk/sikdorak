import Icon from 'common/Icon';
import Modal from 'common/Modal/Modal';
import { useReviewWrite } from 'context/reviewWriteProvider';
import { useState } from 'react';
import {
  Circle,
  PostScopeWrapper,
  ScopeButtonWrapper,
  ScopeDescriptionArea,
  ScopeWrapper,
  Wrap,
} from './PostScope.styled';

const scope = {
  public: '이 게시물을 회원님의 팔로워가 아니더라도 볼 수 있도록 설정합니다.',
  private: '친구에게만 공개합니다.',
};

export default function PostScope() {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [reviewWriteState, dispatchReviewWriteState] = useReviewWrite();

  const { scope: currentScope } = reviewWriteState;

  const handleSetModal = () => {
    setIsOpenModal(!isOpenModal);
  };

  const handleSetScope = () => {
    const nextScope = currentScope === 'public' ? 'private' : 'public';
    dispatchReviewWriteState({ type: 'SET_SCOPE', scope: nextScope });
  };
  return (
    <Wrap>
      <PostScopeWrapper onClick={handleSetModal}>
        <div>게시물 공개 범위</div>
        <Icon icon={isOpenModal ? 'UpArrow' : 'DownArrow'} />
      </PostScopeWrapper>
      {isOpenModal && (
        <Modal height="150px">
          <ScopeWrapper>
            <div>{currentScope === 'public' ? '전체 공개' : '친구 공개'}</div>
            <ScopeButtonWrapper scope={currentScope} onClick={handleSetScope}>
              <Circle />
            </ScopeButtonWrapper>
          </ScopeWrapper>
          <ScopeDescriptionArea>{scope[currentScope]}</ScopeDescriptionArea>
        </Modal>
      )}
    </Wrap>
  );
}
