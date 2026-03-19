<template>
  <div class="container">
    <div class="header-section">
      <h2>👋 Hola, bienvenido</h2>
      <p>Este es el panel principal del Comedor Cruz Amarilla.</p>
    </div>

    <!-- Tarjetas de Resumen -->
    <div class="dashboard-grid">
      <div class="card stat-card blue">
        <div class="icon">👥</div>
        <div class="info">
          <h3>{{ totalClientes }}</h3>
          <p>Clientes</p>
        </div>
      </div>

      <div class="card stat-card orange">
        <div class="icon">🍔</div>
        <div class="info">
          <h3>{{ totalProductos }}</h3>
          <p>Productos</p>
        </div>
      </div>

      <div class="card stat-card green">
        <div class="icon">📝</div>
        <div class="info">
          <h3>{{ pedidosHoy }}</h3>
          <p>Pedidos Hoy</p>
        </div>
      </div>

      <div class="card stat-card purple">
        <div class="icon">💰</div>
        <div class="info">
          <h3>${{ ingresosHoy.toFixed(2) }}</h3>
          <p>Venta del Día</p>
        </div>
      </div>
    </div>

    <div class="content-grid">
      <!-- Tabla de Últimos Pedidos -->
      <div class="card">
        <h3>🕒 Últimos Pedidos</h3>
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Hora</th>
              <th>Cliente</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="p in ultimosPedidos" :key="p.id">
              <td>#{{ p.id }}</td>
              <td>{{ p.fecha ? new Date(p.fecha).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'}) : '-' }}</td>
              <td>{{ p.cliente ? p.cliente.nombre : 'Anónimo' }}</td>
              <td>${{ calcularTotal(p) }}</td>
            </tr>
            <tr v-if="ultimosPedidos.length === 0">
              <td colspan="4" style="text-align: center; color: #888;">No hay pedidos registrados.</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Acciones Rápidas -->
      <div class="card">
        <h3>⚡ Acciones</h3>
        <div class="quick-actions">
          <button class="btn btn-primary full-width" @click="$emit('navegar', 'pedidos')" style="padding: 15px; font-size: 1.1rem;">
            📝 Crear Nuevo Pedido
          </button>
          
          <hr style="margin: 15px 0; border: 0; border-top: 1px solid #eee;">
          
          <button class="btn btn-secondary full-width" @click="$emit('navegar', 'clientes')">
            👥 Registrar Cliente
          </button>
          <button class="btn btn-secondary full-width" @click="$emit('navegar', 'productos')">
            🍔 Agregar Producto
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "../services/api"

export default {
  data() {
    return {
      clientes: [],
      productos: [],
      pedidos: []
    }
  },
  computed: {
    totalClientes() { return this.clientes.length },
    totalProductos() { return this.productos.length },
    
    pedidosHoy() {
      const hoy = new Date().toDateString(); // Fecha de hoy local (ej: "Thu Mar 12 2026")
      return this.pedidos.filter(p => {
        if (!p.fecha) return false;
        // Convertimos la fecha del pedido a la fecha local del navegador para comparar
        const fechaPedido = new Date(p.fecha).toDateString();
        return fechaPedido === hoy;
      }).length;
    },
    
    ingresosHoy() {
      const hoy = new Date().toDateString();
      return this.pedidos
        .filter(p => {
          if (!p.fecha) return false;
          return new Date(p.fecha).toDateString() === hoy;
        })
        .reduce((total, p) => total + this.calcularTotalNum(p), 0);
    },

    ultimosPedidos() {
      // Retorna los últimos 5 pedidos ordenados del más nuevo al más viejo
      return [...this.pedidos].sort((a, b) => b.id - a.id).slice(0, 5);
    }
  },
  mounted() {
    this.cargarDatos();
  },
  methods: {
    async cargarDatos() {
      try {
        const [resClientes, resProductos, resPedidos] = await Promise.all([
          api.get("/clientes"),
          api.get("/productos"),
          api.get("/pedidos")
        ]);
        this.clientes = resClientes.data;
        this.productos = resProductos.data;
        this.pedidos = resPedidos.data;
      } catch (e) {
        console.error("Error cargando dashboard", e);
      }
    },
    calcularTotalNum(pedido) {
      if (!pedido.productos) return 0;
      return pedido.productos.reduce((sum, prod) => sum + prod.precio, 0);
    },
    calcularTotal(pedido) {
      return this.calcularTotalNum(pedido).toFixed(2);
    }
  }
}
</script>

<style scoped>
.header-section { margin-bottom: 2rem; }
.header-section h2 { color: var(--secondary-color); font-size: 1.8rem; }
.header-section p { color: #666; }

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
  border-left: 5px solid;
  border-radius: 8px;
  background: white;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
}

.stat-card.blue { border-color: #3498db; }
.stat-card.orange { border-color: #e67e22; }
.stat-card.green { border-color: #27ae60; }
.stat-card.purple { border-color: #9b59b6; }

.stat-card .icon {
  font-size: 2.5rem;
  margin-right: 20px;
}

.stat-card h3 { margin: 0; font-size: 2rem; color: #333; }
.stat-card p { margin: 0; color: #777; font-size: 0.9rem; }

.content-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}

@media (max-width: 768px) {
  .content-grid { grid-template-columns: 1fr; }
}

.quick-actions button {
  margin-bottom: 10px;
}
.full-width { width: 100%; }
</style>
