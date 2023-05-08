import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { accountReducer } from './account';

const rootReducer = combineReducers({
  account: accountReducer,
});

export default configureStore({
  reducer: rootReducer,
});
