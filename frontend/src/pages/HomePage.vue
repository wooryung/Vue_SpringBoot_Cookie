<template>
  <div class="q-pa-md">
    <q-toolbar
      class="row no-wrap items-center q-mt-md q-pa-sm bg-grey-3 rounded-borders"
    >
      <q-space />
      {{ username }}님 안녕하세요!
      <q-space />
      <router-link to="/login">Login</router-link>
    </q-toolbar>
  </div>
  <q-page>
    <div class="q-pa-md">
      <q-table
        title="User List"
        :rows="userListRows"
        :columns="userListColumns"
      />
      <br />
      <q-btn @click="logout">Logout</q-btn>
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

const username = ref("");
const userList = ref([]);
const userListRows = ref([]);
const userListColumns = ref([]);

const fetchData = async () => {
  const res = await axios.get("/api/");
  console.log(res.data);

  username.value = res.data.username;
  userList.value = res.data.userList;

  userStore.username = username;

  userListRows.value = userList.value.map((user) => ({
    id: user.id,
    name: user.name,
    username: user.username,
    email: user.email,
    address: user.address,
    phone: user.phone,
    website: user.website,
    company: user.company,
  }));

  userListColumns.value = [
    { name: "id", label: "Id", field: "id" },
    { name: "name", label: "Name", field: "name" },
    { name: "username", label: "Username", field: "username" },
    { name: "email", label: "Email", field: "email" },
    { name: "address", label: "Address", field: "address" },
    { name: "phone", label: "Phone", field: "phone" },
    { name: "website", label: "Website", field: "website" },
    { name: "company", label: "Company", field: "company" },
  ];

  return {
    username,
    userListRows,
    userListColumns,
  };
};

fetchData();

const logout = () => {
  axios.post("/api/logout").then((res) => {
    console.log(res.data);
    if (res.data.loggedOut == true) {
      userStore.username = null;
      router.push("/login");
    }
  });
};
</script>
