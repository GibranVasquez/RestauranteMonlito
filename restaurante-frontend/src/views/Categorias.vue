<template>
  <div class="container">
    
    <div style="margin-bottom: 20px;">
      <button @click="$emit('navegar', 'home')" class="btn btn-secondary">
        ⬅ Regresar al Inicio
      </button>
    </div>

    <div class="card">
      <h3>{{ modoEditar ? 'Editar Categoría' : 'Nueva Categoría' }}</h3>
      
      <form @submit.prevent="guardar">
        <div class="form-row">
          <div class="form-group" style="flex: 1;">
            <input 
              v-model="categoria.nombre" 
              placeholder="Nombre de la Categoría" 
              class="input"
              required
            >
          </div>
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
      <h3>Listado de Categorías</h3>
      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th style="text-align: right;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="categorias.length === 0">
              <td colspan="3" style="text-align: center;">No hay categorías registradas.</td>
            </tr>
            <tr v-for="c in categorias" :key="c.id">
              <td>#{{ c.id }}</td>
              <td>{{ c.nombre }}</td>
              <td style="text-align: right;">
                <button @click="editar(c)" class="btn btn-sm btn-secondary" style="margin-right: 5px;">
                  ✏️
                </button>
                <button @click="eliminar(c.id)" class="btn btn-sm btn-danger">
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
      categorias: [],
      categoria: { id: null, nombre: "" },
      modoEditar: false
    }
  },
  
  mounted() {
    this.cargar()
  },
  
  methods: {
    async cargar() {
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
          await api.put("/categorias/" + this.categoria.id, this.categoria)
        } else {
          await api.post("/categorias", this.categoria)
        }
        this.limpiar()
        this.cargar()
      } catch (e) {
        alert("Error al guardar categoría")
      }
    },
    
    editar(c) {
      this.categoria = { ...c }
      this.modoEditar = true
    },
    
    async eliminar(id) {
      if (confirm("¿Eliminar categoría?")) {
        try {
          await api.delete("/categorias/" + id)
          this.cargar()
        } catch (e) {
          alert("Error al eliminar")
        }
      }
    },
    
    limpiar() {
      this.categoria = { id: null, nombre: "" }
      this.modoEditar = false
    }
  }
}
</script>
