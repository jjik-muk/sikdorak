import { ComponentStory, ComponentMeta } from '@storybook/react';
import Button from './Button';

export default {
  title: 'Button',
  component: Button,
} as ComponentMeta<typeof Button>;

const Template: ComponentStory<typeof Button> = (args) => <Button {...args}>{args.label}</Button>;

export const Large = Template.bind({});
Large.args = {
  label: 'Large button',
  size: 'LARGE',
  backgroundColor: 'YELLOW',
};

export const Small = Template.bind({});
Small.args = {
  label: 'Small button',
  size: 'SMALL',
  backgroundColor: 'BLUE',
};
