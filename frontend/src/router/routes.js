const routes = [
  {
    path: "/",
    component: () => import("layouts/RouteLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/HomePage.vue"),
      },
      {
        path: "login",
        component: () => import("pages/LoginPage.vue"),
      },
    ],
    // component: () => import("layouts/MainLayout.vue"),
    // children: [{ path: "", component: () => import("pages/IndexPage.vue") }],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
