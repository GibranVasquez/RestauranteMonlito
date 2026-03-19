import { reactive } from 'vue';

const state = reactive({
  notifications: []
});

let idCounter = 0;

export const useNotification = () => {
  const add = (message, type = 'info', duration = 3000) => {
    const id = idCounter++;
    state.notifications.push({ id, message, type });
    
    if (duration > 0) {
      setTimeout(() => {
        remove(id);
      }, duration);
    }
  };

  const remove = (id) => {
    const index = state.notifications.findIndex(n => n.id === id);
    if (index !== -1) {
      state.notifications.splice(index, 1);
    }
  };

  return {
    state,
    add,
    remove
  };
};

export const success = (msg) => useNotification().add(msg, 'success');
export const error = (msg) => useNotification().add(msg, 'error');
export const info = (msg) => useNotification().add(msg, 'info');
