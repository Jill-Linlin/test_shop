<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const adminProducts = ref([]);
const showAddModal = ref(false); // 控制新增彈窗
const IMAGE_BASE_URL = "http://localhost:8080/images/";

// 新商品暫存資料
const newProduct = ref({
  productId: "",
  productName: "",
  price: 0,
  stockQuantity: 0,
});
const selectedFile = ref(null);

// 抓取後台清單
const fetchAdminProducts = async () => {
  try {
    const res = await axios.get("http://localhost:8080/api/admin/products/all");
    adminProducts.value = res.data;
  } catch (err) {
    console.error("讀取失敗", err);
  }
};

// 處理檔案選取
const onFileSelected = (event) => {
  selectedFile.value = event.target.files[0];
};

// 執行新增
const handleAddProduct = async () => {
  const formData = new FormData();
  formData.append("productId", newProduct.value.productId);
  formData.append("productName", newProduct.value.productName);
  formData.append("price", newProduct.value.price);
  formData.append("stockQuantity", newProduct.value.stockQuantity);
  formData.append("image", selectedFile.value);

  try {
    await axios.post(
      "http://localhost:8080/api/admin/products/create",
      formData,
    );
    alert("上架成功！");
    showAddModal.value = false; // 關閉彈窗
    fetchAdminProducts(); // 刷新列表
  } catch (err) {
    alert("新增失敗，請檢查資料是否重複");
  }
};

onMounted(fetchAdminProducts);
</script>

<template>
  <div class="seller-container">
    <div class="admin-header">
      <h2>賣家管理後台</h2>
      <button class="btn-add" @click="showAddModal = true">
        + 新增單個商品
      </button>
    </div>

    <table class="admin-table">
      <thead>
        <tr>
          <th>圖片</th>
          <th>商品 ID</th>
          <th>名稱</th>
          <th>價格</th>
          <th>庫存</th>
          <th>狀態</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in adminProducts" :key="p.productId">
          <td>
            <img
              :src="
                p.imageUrl ? IMAGE_BASE_URL + p.imageUrl : '/images/images.png'
              "
              class="admin-thumb"
            />
          </td>
          <td>{{ p.productId }}</td>
          <td>{{ p.productName }}</td>
          <td>${{ p.price }}</td>
          <td>{{ p.stockQuantity }}</td>
          <td>
            <span :class="p.stockQuantity > 0 ? 'status-on' : 'status-off'">
              {{ p.stockQuantity > 0 ? "販售中" : "已售罄" }}
            </span>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 確認上架彈窗 -->
    <div v-if="showAddModal" class="modal-overlay">
      <div class="modal-content">
        <h3>上架新商品</h3>
        <div class="form-group">
          <label>商品 ID:</label>
          <input v-model="newProduct.productId" placeholder="如: P011" />
        </div>
        <div class="form-group">
          <label>商品名稱:</label>
          <input v-model="newProduct.productName" />
        </div>
        <div class="form-group">
          <label>單價:</label>
          <input type="number" v-model.number="newProduct.price" />
        </div>
        <div class="form-group">
          <label>初始庫存:</label>
          <input type="number" v-model.number="newProduct.stockQuantity" />
        </div>
        <div class="form-group">
          <label>商品圖片:</label>
          <input type="file" @change="onFileSelected" accept="image/*" />
        </div>
        <div class="modal-actions">
          <button @click="showAddModal = false">取消</button>
          <button class="btn-confirm" @click="handleAddProduct">
            確認添加
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped src="@/assets/admin.css"></style>
<style scoped src="@/assets/shopview.css"></style>
