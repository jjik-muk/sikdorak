import { toast } from 'react-toastify';

export function openSuccessToast(message) {
  toast.success(message, {
    position: 'top-center',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: false,
    draggable: true,
    progress: undefined,
    theme: 'light',
  });
}

export function openWarningToast(message) {
  toast.warn(message, {
    position: 'top-center',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: false,
    draggable: true,
    progress: undefined,
    theme: 'light',
  });
}

export function openErrorToast(message) {
  toast.error(message, {
    position: 'top-center',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: false,
    draggable: true,
    progress: undefined,
    theme: 'light',
  });
}
