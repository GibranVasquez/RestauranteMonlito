<template>
  <div class="container">
    
    <div style="margin-bottom: 20px;">
      <button @click="$emit('navegar', 'home')" class="btn btn-secondary">
        ⬅ Regresar al Inicio
      </button>
    </div>

    <div class="card">
      <h3>{{ cliente.id ? 'Editar Cliente' : 'Nuevo Cliente' }}</h3>
      
      <form @submit.prevent="guardar">
        <div class="form-row">
          <div class="form-group" style="flex: 1;">
            <input 
              v-model="cliente.nombre" 
              placeholder="Nombre Completo" 
              class="input"
              required
            />
          </div>
          <div class="form-group" style="flex: 1;">
            <input 
              v-model="cliente.telefono" 
              placeholder="Teléfono" 
              class="input"
              required
            />
          </div>
        </div>
        
        <div class="actions">
          <button type="submit" class="btn btn-primary">
            {{ cliente.id ? 'Actualizar' : 'Guardar' }}
          </button>
          
          <button 
            type="button" 
            v-if="cliente.id" 
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
      <h3>Listado de Clientes</h3>
      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Teléfono</th>
              <th style="text-align: right;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="clientes.length === 0">
              <td colspan="4" style="text-align: center;">No hay clientes registrados.</td>
            </tr>
            <tr v-for="c in clientes" :key="c.id">
              <td>#{{ c.id }}</td>
              <td>{{ c.nombre }}</td>
              <td>{{ c.telefono }}</td>
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
import { success, error } from "../services/notification"

export default {
  inject: ['confirmar'],
  data() {
    return {
      clientes: [],
      cliente: {
        id: null,
        nombre: "",
        telefono: ""
      }
    }
  },
  methods: {
    async cargar() {
      try {
        let res = await api.get("/clientes")
        this.clientes = res.data
      } catch (e) {
        error("Error cargando clientes")
      }
    },
    async guardar() {
      try {
        if (this.cliente.id) {
          await api.put("/clientes/" + this.cliente.id, this.cliente)
          success("Cliente actualizado")
        } else {
          await api.post("/clientes", this.cliente)
          success("Cliente guardado")
        }
        this.limpiar()
        this.cargar()
      } catch (e) {
        error("Error al guardar cliente")
      }
    },
    editar(c) {
      this.cliente = { ...c }
    },
    async eliminar(id) {
      const confirmado = await this.confirmar(
        "Eliminar Cliente", 
        "¿Estás seguro? Se eliminarán todos los pedidos asociados a este cliente."
      );
      
      if (confirmado) {
        try {
          await api.delete("/clientes/" + id)
          this.cargar()
          success("Cliente eliminado")
        } catch (e) {
          error("Error al eliminar cliente")
        }
      }
    },
    limpiar() {
      this.cliente = { id: null, nombre: "", telefono: "" }
    }
  },
  mounted() {
    this.cargar()
  }
}
</script>

<style scoped>
.actions {
  text-align: right;
}
</style>
