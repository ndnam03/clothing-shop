import { createRouter, createWebHistory } from "vue-router";

import home from "../pages/home.vue";

const routes = [
  {
    path: "/home",
    name: "home",
    component: home,
  },
  {
    path: "/login",
    name: "login",
    component: () =>
      import(/* webpackChunkName: "about" */ "../pages/login.vue"),
  },
  {
    path: "/admin",
    name: "admin",
    meta: { requiresAuth: true },
    component: () =>
      import(/* webpackChunkName: "admin" */ "../pages/admin/index.vue"),
    children: [
      {
        path: "category",
        name: "admin-category",
        component: () =>
          import(
            /* webpackChunkName: "admin" */ "../pages/admin/admin-category.vue"
          ),
      },
      // Thêm các route con khác cho trang quản trị (nếu cần)
    ],
  },
  // Thêm các route khác cho trang khách hàng (nếu cần)
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const isAuthenticated = true;

  if (requiresAuth && !isAuthenticated) {
    next("/"); // Chuyển hướng đến trang home nếu người dùng không đăng nhập
  } else {
    next();
  }
});

export default router;
