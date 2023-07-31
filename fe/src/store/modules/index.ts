import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { accountReducer } from './account';
import { userReducer } from './user';

const rootReducer = combineReducers({
  account: accountReducer,
  user: userReducer,
});

export default configureStore({
  reducer: rootReducer,
});
