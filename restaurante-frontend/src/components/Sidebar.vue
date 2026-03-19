<template>
  <div class="sidebar">
    <div class="brand">
      <h2>🌼 Cruz Amarilla</h2>
      <p>Comedor</p>
    </div>
    
    <nav class="menu">
      <button class="menu-item" @click="$emit('cambiarVista', 'home')">
        <span>🏠</span> Inicio
      </button>

      <!-- Orden Lógico: Primero defines A QUIÉN y QUÉ vendes -->
      <button class="menu-item" @click="$emit('cambiarVista', 'clientes')">
        <span>👥</span> Clientes
      </button>

      <button class="menu-item" @click="$emit('cambiarVista', 'categorias')">
        <span>🏷️</span> Categorías
      </button>

      <button class="menu-item" @click="$emit('cambiarVista', 'productos')">
        <span>🍔</span> Productos
      </button>

      <!-- Al final haces la venta -->
      <button class="menu-item" @click="$emit('cambiarVista', 'pedidos')">
        <span>📝</span> Pedidos
      </button>
    </nav>
    
    <div class="footer">
      <p>&copy; 2026 Cruz Amarilla</p>
    </div>
  </div>
</template>

<style scoped>
.sidebar {
  width: 250px;
  background: var(--secondary-color);
  color: white;
  height: 100vh;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
  flex-shrink: 0;
}

.brand {
  padding: 2rem;
  background-color: rgba(0,0,0,0.2);
  text-align: center;
}

.brand h2 {
  color: #f1c40f; /* Amarillo más suave */
  margin: 0;
  font-size: 1.5rem;
}

.brand p {
  opacity: 0.9;
  font-size: 0.9rem;
  color: white;
}

.menu {
  flex: 1;
  padding: 1rem 0;
}

.menu-item {
  width: 100%;
  padding: 15px 25px;
  border: none;
  background: transparent;
  color: rgba(255,255,255,0.9);
  cursor: pointer;
  text-align: left;
  font-size: 1rem;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 15px;
}

.menu-item span {
  font-size: 1.2rem;
}

.menu-item:hover {
  background: rgba(255,255,255,0.1);
  color: #f1c40f;
  padding-left: 30px;
}

.footer {
  padding: 1rem;
  text-align: center;
  font-size: 0.8rem;
  opacity: 0.5;
  border-top: 1px solid rgba(255,255,255,0.1);
}
</style>
