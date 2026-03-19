<template>
  <div class="layout">
    <Toast />
    <ModalConfirm ref="modalConfirm" />
    <Sidebar @cambiarVista="cambiarVista" />
    
    <div class="main-content">
      <header class="top-bar">
        <h1>{{ tituloVista }}</h1>
        <div class="user-info">
          <span>Admin</span>
          <div class="avatar">A</div>
        </div>
      </header>

      <div class="page-content">
        <transition name="fade" mode="out-in">
          <component :is="vistaActualComponente" @navegar="cambiarVista" />
        </transition>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from "./components/Sidebar.vue"
import Toast from "./components/Toast.vue"
import ModalConfirm from "./components/ModalConfirm.vue"
import HomeView from "./views/Home.vue"
import ClientesView from "./views/Clientes.vue"
import CategoriasView from "./views/Categorias.vue"
import ProductosView from "./views/Productos.vue"
import PedidosView from "./views/Pedidos.vue"
import { computed } from 'vue'

export default {
  components: {
    Sidebar,
    Toast,
    ModalConfirm,
    HomeView,
    ClientesView,
    CategoriasView,
    ProductosView,
    PedidosView
  },
  provide() {
    return {
      confirmar: this.invocarConfirmacion
    }
  },
  data() {
    return {
      vista: "home"
    }
  },
  computed: {
    vistaActualComponente() {
      switch (this.vista) {
        case 'home': return 'HomeView';
        case 'clientes': return 'ClientesView';
        case 'categorias': return 'CategoriasView';
        case 'productos': return 'ProductosView';
        case 'pedidos': return 'PedidosView';
        default: return 'HomeView';
      }
    },
    tituloVista() {
      if(this.vista === 'home') return 'Panel de Control';
      return this.vista.charAt(0).toUpperCase() + this.vista.slice(1);
    }
  },
  methods: {
    cambiarVista(nuevaVista) {
      this.vista = nuevaVista;
    },
    invocarConfirmacion(titulo, mensaje) {
      return this.$refs.modalConfirm.mostrar(titulo, mensaje);
    }
  }
}
</script>

<style>
.layout {
  display: flex;
  min-height: 100vh;
  width: 100%;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: var(--bg-color);
  overflow-y: auto;
}

.top-bar {
  background-color: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.top-bar h1 {
  margin: 0;
  font-size: 1.5rem;
  color: var(--secondary-color);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--primary-color);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}

.page-content {
  padding: 2rem;
  flex: 1;
}

</style>
