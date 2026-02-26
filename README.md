# 🛒 玉山購物商城與賣家管理系統

## 🚀 技術棧 (Tech Stack)

### 前端 (Frontend)

- **Vue 3**
- **Vite**
- **Axios**
- **CSS3**

### 後端 (Backend)

- **Spring Boot 3**
- **Spring Data JPA**
- **MariaDB / MySQL**
- **Stored Procedure**

---

## 專案核心功能

1. 新增商品功能:(後台)
   電商購物中心管理人員,可以透過新增商品功能新增商品,商品欄位包含 [商品ID] [商品名稱] [單價] [初始庫存] 。

2. 新增訂單功能:(前台)
   顯示庫存量大於零的商品清單。
   顧客可以勾選多個商品,並設定購買的數量,購買數量不能大於該商品庫存量。
   顯示顧客訂單內容,包含各商品金額小計以及訂單總金額。
   顧客按下建立訂單按鈕後,在資料庫新增訂單資料,並更新商品庫存數量。

---

## 環境安裝與執行步驟(Maven)

### 1. 資料庫建置 (Database)

請先匯入本專案 `/DB` 資料夾下的腳本：

1. 執行 `DDL.sql` 建立資料表。
2. 執行 `DML.sql` 建立預設資料及儲存程序。

### 2. 後端啟動 (Spring Boot)

1. 開啟 `src/main/resources/application.properties`。
2. 修改資料庫連線資訊：

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/your_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

```

3. 執行 `mvn spring-boot:run`。

### 3. 前端啟動 (Vue 3)

1. 進入前端資料夾。
2. 執行 `npm install` 安裝相應套件。
3. 執行 `npm run dev` 啟動開發伺服器。
4. 開啟瀏覽器訪問 `http://localhost:5173`。

---

---
