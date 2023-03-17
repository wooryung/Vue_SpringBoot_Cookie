<template>
  <q-page>
    <router-link to="/">Home</router-link>
    <div class="q-pa-md" align="center">
      <q-card class="my-card" style="max-width: 500px">
        <q-card-section>
          <h5>Login</h5>
        </q-card-section>
        <q-card-section style="max-width: 400px">
          <p>
            <q-input v-model="email" type="text" label="Email" />
          </p>
          <p>
            <q-input v-model="password" type="password" label="Password" />
          </p>
        </q-card-section>
        <q-card-section>
          <q-btn @click="login">Login</q-btn>
        </q-card-section>
        <br />
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import axios from "axios";
import { useUserStore } from "src/stores/user-store";
import { ref } from "vue";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const email = ref("");
const password = ref("");

const login = () => {
  axios
    .post("/api/login", { email: email.value, password: password.value })
    .then((res) => {
      console.log(res.data);
      if (res.data.authenticated == true) {
        userStore.username = res.data.username;
        router.push("/");
      }
    })
    .catch((err) => {
      console.error(err);
    });
};
</script>
