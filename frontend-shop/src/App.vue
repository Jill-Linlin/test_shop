<template>
  <div id="app">
    <header class="hero-header">
      <div class="container">
        <div class="header-left">
          <div class="buyer-center">
            <router-link to="/admin" class="nav-btn">賣家中心</router-link>
          </div>

          <div class="logo-text">
            <router-link to="/" class="nav-btn"><h1>玉山購物</h1></router-link>
          </div>
        </div>

        <div class="header-center">
          <div class="search-group">
            <input type="text" placeholder="搜尋商品，領取免運券！" />
            <button class="search-icon-btn">🔍</button>
          </div>
        </div>

        <div class="header-right">
          <div class="cart-wrapper">
            <span class="cart-icon">
              <i class="fa-solid fa-cart-plus"></i>
            </span>
            <span class="badge">0</span>
          </div>
        </div>
      </div>
    </header>

    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<style scoped src="@/assets/App.css"></style>
