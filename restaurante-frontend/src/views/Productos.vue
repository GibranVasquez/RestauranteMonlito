<template>
  <div class="container">
    
    <div style="margin-bottom: 20px;">
      <button @click="$emit('navegar', 'home')" class="btn btn-secondary">
        ⬅ Regresar al Inicio
      </button>
    </div>

    <div class="card">
      <h3>{{ modoEditar ? 'Editar Producto' : 'Nuevo Producto' }}</h3>

      <form @submit.prevent="guardar">
        <div class="form-row">
          <div class="form-group" style="flex: 2;">
            <input 
              v-model="producto.nombre" 
              placeholder="Nombre del Producto" 
              class="input"
              required
            >
          </div>
          <div class="form-group" style="flex: 1;">
            <input 
              v-model="producto.precio" 
              type="number" 
              step="0.01"
              placeholder="Precio ($)" 
              class="input"
              required
            >
          </div>
        </div>

        <div class="form-group">
            <label style="font-size: 0.9rem; color: #666;">Categoría</label>
            <select v-model="producto.categoria" class="select" required>
                <option :value="null" disabled>-- Seleccionar Categoría --</option>
                <option v-for="c in categorias" :key="c.id" :value="c">
                    {{ c.nombre }}
                </option>
            </select>
        </div>

        <div class="actions" style="text-align: right;">
          <button class="btn btn-primary">
            {{ modoEditar ? "Actualizar" : "Guardar" }}
          </button>
          
          <button 
            type="button" 
            v-if="modoEditar" 
            @click="limpiar" 
            class="btn btn-secondary" 
            style="margin-left: 10px;"
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>

    <div class="card">
      <h3>Menú de Productos</h3>
      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Categoría</th>
              <th>Precio</th>
              <th style="text-align: right;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="productos.length === 0">
              <td colspan="5" style="text-align: center;">No hay productos registrados.</td>
            </tr>
            <tr v-for="p in productos" :key="p.id">
              <td>#{{ p.id }}</td>
              <td>
                <strong>{{ p.nombre }}</strong>
              </td>
              <td>
                  <span class="badge">{{ p.categoria ? p.categoria.nombre : 'Sin Categoría' }}</span>
              </td>
              <td>${{ parseFloat(p.precio).toFixed(2) }}</td>
              <td style="text-align: right;">
                <button @click="editar(p)" class="btn btn-sm btn-secondary" style="margin-right: 5px;">
                  ✏️
                </button>
                <button @click="eliminar(p.id)" class="btn btn-sm btn-danger">
                  🗑️
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

export default {
  data() {
    return {
      productos: [],
      categorias: [],
      producto: { id: null, nombre: "", precio: "", categoria: null },
      modoEditar: false
    }
  },

  mounted() {
    this.cargar()
    this.cargarCategorias()
  },

  methods: {
    async cargar() {
      try {
        const res = await api.get("/productos")
        this.productos = res.data
      } catch (e) {
        console.error(e)
      }
    },

    async cargarCategorias() {
        try {
            const res = await api.get("/categorias")
            this.categorias = res.data
        } catch (e) {
            console.error(e)
        }
    },

    async guardar() {
      try {
        if (this.modoEditar) {
          await api.put("/productos/" + this.producto.id, this.producto)
        } else {
          await api.post("/productos", this.producto)
        }
        this.limpiar()
        this.cargar()
      } catch (e) {
        alert("Error al guardar producto")
      }
    },

    editar(p) {
      this.producto = { ...p }
      this.modoEditar = true
    },

    async eliminar(id) {
      if (confirm("¿Eliminar producto?")) {
        try {
          await api.delete("/productos/" + id)
          this.cargar()
        } catch (e) {
          alert("Error al eliminar")
        }
      }
    },

    limpiar() {
      this.producto = { id: null, nombre: "", precio: "", categoria: null }
      this.modoEditar = false
    }
  }
}
</script>

<style scoped>
.badge {
    background-color: #eee;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 0.85rem;
    color: #555;
}
</style>
