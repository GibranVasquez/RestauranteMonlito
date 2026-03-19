<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal-card">
      <div class="modal-header">
        <h3>{{ titulo }}</h3>
      </div>
      <div class="modal-body">
        <p>{{ mensaje }}</p>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" @click="cancelar">
          Cancelar
        </button>
        <button class="btn btn-danger" @click="confirmar">
          Confirmar
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      titulo: 'Confirmar Acción',
      mensaje: '¿Estás seguro?',
      resolve: null,
      reject: null
    };
  },
  methods: {
    mostrar(titulo, mensaje) {
      this.titulo = titulo || 'Confirmación';
      this.mensaje = mensaje || '¿Continuar?';
      this.visible = true;
      return new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      });
    },
    confirmar() {
      this.visible = false;
      if (this.resolve) this.resolve(true);
    },
    cancelar() {
      this.visible = false;
      if (this.resolve) this.resolve(false);
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  animation: fadeIn 0.2s ease-out;
}

.modal-card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  transform: translateY(0);
  animation: slideUp 0.3s ease-out;
}

.modal-header h3 {
  margin: 0;
  color: var(--secondary-color);
}

.modal-body {
  margin: 20px 0;
  color: #555;
  font-size: 1rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideUp {
  from { transform: translateY(20px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}
</style>
