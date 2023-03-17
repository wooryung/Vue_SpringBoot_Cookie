import { route } from "quasar/wrappers";
import {
  createRouter,
  createMemoryHistory,
  createWebHistory,
  createWebHashHistory,
} from "vue-router";
import { useUserStore } from "src/stores/user-store";
import routes from "./routes";
import axios from "axios";

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : process.env.VUE_ROUTER_MODE === "history"
    ? createWebHistory
    : createWebHashHistory;

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.VUE_ROUTER_BASE),
  });

  const userStore = useUserStore();

  Router.beforeEach((to, from, next) => {
    if (!userStore.isLoggedIn) {
      // store에 user 정보가 없을 때
      axios
        .get("/api/relogin")
        .then((res) => {
          if (res.data.username != null) {
            // 쿠키가 존재하는 경우 store 복구
            console.log(res.data);
            userStore.username = res.data.username;
            if (to.fullPath == "/login") {
              next("/");
            } else {
              next();
            }
          }
        })
        .catch((err) => {
          // 쿠키가 존재하지 않는 경우
          next("/login");
        });
    } else {
      // store에 user 정보가 있을 때
      if (to.fullPath == "/login") {
        next("/");
      } else {
        next();
      }
    }
  });

  return Router;
});
