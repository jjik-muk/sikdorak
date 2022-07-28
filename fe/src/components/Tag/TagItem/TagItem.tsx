import { Wrap } from './TagItem.styled';

function TagItem({ name }: { name: string }) {
  return <Wrap>{name}</Wrap>;
}
export default TagItem;
