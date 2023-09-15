<template>
  <nav>
    <header-view v-if="isCheck && !isAdminRoute" />
  </nav>
  <hr />

  <slot />
  <hr />
  <router-view></router-view>
  <footer-view v-if="isCheck && !isAdminRoute" />
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router"; // Import useRoute từ vue-router
import HeaderView from "./components/HeaderView.vue";
import FooterView from "./components/FooterView.vue";

const isCheck = ref(true);

const route = useRoute(); // Sử dụng useRoute để lấy thông tin về route hiện tại

// Lấy đường dẫn hiện tại từ route
const currentRoute = computed(() => route.path);

// Kiểm tra xem đường dẫn có bắt đầu bằng "/admin" hay không
const isAdminRoute = computed(() => currentRoute.value.startsWith("/admin"));
</script>
