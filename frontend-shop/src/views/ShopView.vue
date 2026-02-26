<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";

// 控制顯示 Modal
const showConfirmModal = ref(false);

// 商品清單
const products = ref([]);

// 存放顧客資訊 (必填)
const customer = ref({
  fullName: "",
  email: "",
});

// 圖片網址
const IMAGE_BASE_URL = "http://localhost:8080/images/";

// 自動計算已勾選商品的總金額
const totalAmount = computed(() => {
  return products.value
    .filter((p) => p.isSelected)
    .reduce((sum, item) => sum + item.price * item.orderQty, 0);
});

// 過濾出目前「有被勾選」的商品物件清單 (給 Modal 顯示用)
const selectedProducts = computed(() => {
  return products.value.filter((p) => p.isSelected);
});

// 計算目前選取的項目數量
const selectedCount = computed(() => {
  return selectedProducts.value.length;
});

// 從後端抓取商品資料
const fetchProducts = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/shop/products");
    products.value = response.data.map((p) => ({
      ...p,
      orderQty: 1, // 預設數量 1
      isSelected: false, // 預設不勾選
    }));
  } catch (error) {
    console.error("無法取得商品資料：", error);
    alert("後端連線失敗，請檢查 Spring Boot 是否啟動");
  }
};

// 最後正式送出訂單
const handleFinalSubmit = async () => {
  // 基本驗證
  if (!customer.value.fullName || !customer.value.email) {
    alert("請完整填寫收件人姓名與 Email");
    return;
  }

  // 整理要送出的資料
  const orderData = {
    email: customer.value.email,
    fullName: customer.value.fullName,
    items: selectedProducts.value.map((item) => ({
      productId: item.productId,
      quantity: item.orderQty,
    })),
  };

  try {
    const res = await axios.post(
      "http://localhost:8080/api/orders/create",
      orderData,
    );
    alert(`訂單建立成功！單號：${res.data}`);
    showConfirmModal.value = false; // 關閉彈窗
    location.reload(); // 刷頁面更新庫存
  } catch (err) {
    console.error("訂單建立失敗：", err);
    alert("結帳失敗：" + (err.response?.data || "伺服器錯誤"));
  }
};

onMounted(() => {
  fetchProducts();
});
</script>

<template>
  <div class="shop-content-wrapper">
    <!-- 商品內容 -->
    <main class="content-area">
      <section class="product-grid-container">
        <div class="product-card" v-for="p in products" :key="p.productId">
          <div class="check-container">
            <input type="checkbox" v-model="p.isSelected" /> 選擇商品
          </div>
          <div class="product-img-box">
            <img
              :src="
                p.imageUrl ? IMAGE_BASE_URL + p.imageUrl : '/images/images.png'
              "
              :alt="p.productId"
              class="product-item-img"
            />
          </div>
          <div class="product-info">
            <div class="name">{{ p.productName }}</div>
            <div class="price">${{ p.price }}</div>
            <div class="qty-area">
              數量：<input
                type="number"
                v-model.number="p.orderQty"
                min="1"
                :max="p.stockQuantity"
                class="qty-input"
              />
            </div>
          </div>
        </div>
      </section>
    </main>

    <div class="checkout-footer">
      <span>已選 {{ selectedCount }} 項，總金額：${{ totalAmount }}</span>
      <button @click="showConfirmModal = true" :disabled="selectedCount === 0">
        確認結帳
      </button>
    </div>

    <!-- 確認訂單談窗 -->
    <div v-if="showConfirmModal" class="modal-overlay">
      <div class="modal-content">
        <h3>確認訂單內容</h3>
        <ul class="confirm-list">
          <li v-for="item in selectedProducts" :key="item.productId">
            {{ item.productName }} x {{ item.orderQty }} — ${{
              item.price * item.orderQty
            }}
          </li>
        </ul>
        <div class="confirm-total">總計：${{ totalAmount }}</div>

        <div class="customer-form">
          <div class="form-group">
            <label>收件人姓名 <span style="color: red">*</span></label>
            <input v-model="customer.fullName" placeholder="請輸入姓名" />
          </div>
          <div class="form-group">
            <label>電子信箱 <span style="color: red">*</span></label>
            <input
              v-model="customer.email"
              type="email"
              placeholder="請輸入 Email"
            />
          </div>
        </div>

        <div class="modal-actions">
          <button class="btn-cancel" @click="showConfirmModal = false">
            取消
          </button>
          <button class="btn-submit" @click="handleFinalSubmit">
            送出訂單
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="@/assets/shopview.css"></style>
