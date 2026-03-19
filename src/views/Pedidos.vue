<template>
  <div class="container">
    
    <!-- Botón Regresar al Inicio -->
    <div style="margin-bottom: 20px;">
      <button @click="$emit('navegar', 'home')" class="btn btn-secondary">
        ⬅ Regresar al Inicio
      </button>
    </div>

    <div class="card">
      <h3>Crear Nuevo Pedido</h3>

      <form @submit.prevent="guardarPedido">
        <!-- Selección de Cliente -->
        <div class="form-group">
          <label>Cliente</label>
          <select v-model="nuevoPedido.cliente" class="select" required>
            <option :value="null" disabled>-- Seleccionar Cliente --</option>
            <option v-for="c in clientes" :key="c.id" :value="c">
              {{ c.nombre }}
            </option>
          </select>
        </div>

        <hr style="margin: 20px 0; border: 0; border-top: 1px solid #eee;">

        <!-- Selección de Productos -->
        <div class="form-row" style="align-items: flex-end;">
          <div class="form-group" style="flex: 1;">
            <label>Categoría</label>
            <select v-model="filtroCategoria" class="select">
              <option :value="null">Todas</option>
              <option v-for="c in categorias" :key="c.id" :value="c.id">
                {{ c.nombre }}
              </option>
            </select>
          </div>
          
          <div class="form-group" style="flex: 2;">
            <label>Producto</label>
            <select v-model="productoAAgregar" class="select">
              <option :value="null" disabled>-- Seleccionar Producto --</option>
              <option v-for="p in productosFiltrados" :key="p.id" :value="p">
                {{ p.nombre }} - ${{ p.precio }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <button type="button" @click="agregarProducto" class="btn btn-primary" :disabled="!productoAAgregar">
              + Agregar
            </button>
          </div>
        </div>

        <!-- Resumen del Pedido Actual -->
        <div v-if="nuevoPedido.productos.length > 0" style="margin-bottom: 20px; background: #f9f9f9; padding: 15px; border-radius: 8px;">
          <h4>Resumen de productos</h4>
          <ul style="padding-left: 20px;">
            <li v-for="(p, index) in nuevoPedido.productos" :key="index" style="margin-bottom: 5px;">
              {{ p.nombre }} - ${{ p.precio }} 
              <button type="button" @click="removerProducto(index)" style="color: red; border: none; background: none; cursor: pointer; font-weight: bold;">(Quitar)</button>
            </li>
          </ul>
          <p style="text-align: right; font-weight: bold; font-size: 1.2rem; margin-top: 10px;">
            Total: ${{ totalPedido.toFixed(2) }}
          </p>
        </div>

        <div class="actions" style="text-align: right;">
          <button type="submit" class="btn btn-success" :disabled="!pedidoValido" style="width: 100%;">
            ✅ Confirmar y Guardar Pedido
          </button>
        </div>
      </form>
    </div>

    <div class="card">
      <h3>Historial de Pedidos</h3>
      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Fecha</th>
              <th>Cliente</th>
              <th>Productos (Detalle)</th>
              <th>Total</th>
              <th style="text-align: right;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="pedidos.length === 0">
              <td colspan="6" style="text-align: center; padding: 20px; color: #888;">No hay pedidos registrados aún.</td>
            </tr>
            <tr v-for="p in pedidos" :key="p.id">
              <td>#{{ p.id }}</td>
              <td>{{ p.fecha ? new Date(p.fecha).toLocaleDateString() : '-' }}</td>
              <td>
                <strong>{{ p.cliente ? p.cliente.nombre : 'Sin Cliente' }}</strong>
              </td>
              <td>
                <span v-for="(prod, i) in p.productos" :key="i">
                  {{ prod.nombre }}{{ i < p.productos.length - 1 ? ', ' : '' }}
                </span>
              </td>
              <td>
                <strong>${{ calcularTotalPedido(p) }}</strong>
              </td>
              <td style="text-align: right;">
                <button @click="eliminar(p.id)" class="btn btn-sm btn-danger">
                  Eliminar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import api from "../services/api"
import { success, error } from "../services/notification"

export default {
  data() {
    return {
      pedidos: [],
      clientes: [],
      categorias: [],
      productos: [],
      
      nuevoPedido: {
        cliente: null,
        productos: []
      },
      
      filtroCategoria: null,
      productoAAgregar: null
    }
  },

  computed: {
    productosFiltrados() {
      if (!this.filtroCategoria) return this.productos;
      return this.productos.filter(p => p.categoria && p.categoria.id === this.filtroCategoria);
    },
    totalPedido() {
      return this.nuevoPedido.productos.reduce((sum, p) => sum + p.precio, 0);
    },
    pedidoValido() {
      return this.nuevoPedido.cliente && this.nuevoPedido.productos.length > 0;
    }
  },

  mounted() {
    this.cargarDatos();
  },

  methods: {
    async cargarDatos() {
        try {
            await Promise.all([
                this.cargarPedidos(),
                this.cargarClientes(),
                this.cargarCategorias(),
                this.cargarProductos()
            ]);
        } catch(e) {
            error("Error al cargar los datos");
        }
    },

    async cargarPedidos() {
        const res = await api.get("/pedidos")
        // Ordenar: Los más recientes primero
        this.pedidos = res.data.sort((a,b) => b.id - a.id);
    },
    async cargarClientes() {
        const res = await api.get("/clientes")
        this.clientes = res.data
    },
    async cargarCategorias() {
        const res = await api.get("/categorias")
        this.categorias = res.data
    },
    async cargarProductos() {
        const res = await api.get("/productos")
        this.productos = res.data
    },

    agregarProducto() {
      if (this.productoAAgregar) {
        this.nuevoPedido.productos.push(this.productoAAgregar);
        this.productoAAgregar = null; // Limpiar selección para agregar otro rápido
      }
    },

    removerProducto(index) {
      this.nuevoPedido.productos.splice(index, 1);
    },

    calcularTotalPedido(pedido) {
        if(!pedido.productos) return "0.00";
        const total = pedido.productos.reduce((sum, p) => sum + p.precio, 0);
        return total.toFixed(2);
    },

    async guardarPedido() {
      try {
        const payload = {
            // Enviamos solo el ID para que el backend enlace la relación existente
            // sin intentar crear/actualizar el objeto cliente completo.
            cliente: { id: this.nuevoPedido.cliente.id },
            productos: this.nuevoPedido.productos.map(p => ({ id: p.id })),
            fecha: new Date().toISOString()
        };
        
        await api.post("/pedidos", payload);
        
        // Limpiar formulario
        this.nuevoPedido = { cliente: null, productos: [] };
        
        // Recargar lista inmediatamente
        await this.cargarPedidos();
        
        success("Pedido guardado correctamente");
      } catch (e) {
        console.error(e);
        error("Error al guardar pedido");
      }
    },

    async eliminar(id) {
      if (confirm("¿Seguro que deseas eliminar este pedido?")) {
        try {
          await api.delete("/pedidos/" + id)
          await this.cargarPedidos()
          success("Pedido eliminado");
        } catch (e) {
          error("Error al eliminar");
        }
      }
    }
  }
}
</script>

<style scoped>
label { display: block; margin-bottom: 5px; font-weight: 500; color: #555; }
.btn-success { background-color: #27ae60; color: white; border: none; }
.btn-success:hover { background-color: #219150; }
</style>
