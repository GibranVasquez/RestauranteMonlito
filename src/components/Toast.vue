<template>
  <div class="toast-container">
    <transition-group name="toast">
      <div 
        v-for="notification in notifications" 
        :key="notification.id"
        class="toast"
        :class="notification.type"
      >
        <div class="toast-content">
          <span class="toast-icon">
            {{ getIcon(notification.type) }}
          </span>
          <span class="toast-message">{{ notification.message }}</span>
        </div>
        <button class="toast-close" @click="remove(notification.id)">×</button>
      </div>
    </transition-group>
  </div>
</template>

<script>
import { useNotification } from "../services/notification";

export default {
  setup() {
    const { state, remove } = useNotification();
    
    const getIcon = (type) => {
      switch(type) {
        case 'success': return '✅';
        case 'error': return '❌';
        case 'info': return 'ℹ️';
        default: return '🔔';
      }
    };

    return {
      notifications: state.notifications,
      remove,
      getIcon
    };
  }
}
</script>

<style scoped>
.toast-container {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  gap: 10px;
  pointer-events: none; /* Allows clicking underneath */
}

.toast {
  pointer-events: auto;
  min-width: 300px;
  background: white;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-left: 5px solid #ccc;
  animation: slideIn 0.3s ease-out;
}

.toast.success { border-left-color: #27ae60; }
.toast.error { border-left-color: #c0392b; }
.toast.info { border-left-color: #3498db; }

.toast-content {
  display: flex;
  align-items: center;
  gap: 10px;
}

.toast-message {
  font-size: 0.95rem;
  color: #333;
}

.toast-close {
  background: none;
  border: none;
  font-size: 1.2rem;
  color: #999;
  cursor: pointer;
  padding: 0 5px;
}

.toast-close:hover {
  color: #333;
}

/* Animations */
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

@keyframes slideIn {
  from { transform: translateX(100%); opacity: 0; }
  to { transform: translateX(0); opacity: 1; }
}
</style>
