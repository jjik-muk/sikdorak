import { ComponentStory, ComponentMeta } from '@storybook/react';
import Text from './Text';

export default {
  title: 'Text',
  component: Text,
} as ComponentMeta<typeof Text>;

const Template: ComponentStory<typeof Text> = (args) => <Text {...args}>{args.label}</Text>;

export const Large = Template.bind({});
Large.args = {
  size: 'LARGE',
  label: '흑마늘 라멘이라고 들어보셨나요? 9천원 차슈는 쏘쏘한데, 국물맛이 장난없습니다.',
};

export const Medium = Template.bind({});
Medium.args = {
  size: 'MEDIUM',
  label: '흑마늘 라멘이라고 들어보셨나요? 9천원 차슈는 쏘쏘한데, 국물맛이 장난없습니다.',
};

export const Small = Template.bind({});
Small.args = {
  size: 'SMALL',
  label: '흑마늘 라멘이라고 들어보셨나요? 9천원 차슈는 쏘쏘한데, 국물맛이 장난없습니다.',
};
