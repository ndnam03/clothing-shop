<template>
  <form @submit.prevent="onSubmitCard">
    <label
      class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
      for="user_avatar"
      >Upload file</label
    >
    <input
      class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
      aria-describedby="user_avatar_help"
      id="user_avatar"
      type="file"
      @change="handleFileChange"
    />
    <div
      class="mt-1 text-sm text-gray-500 dark:text-gray-300"
      id="user_avatar_help"
    >
      <input class="w-full" title="text" v-model="brandName" />
      A profile picture is useful to confirm your are logged into your account
    </div>
    <button type="submit">Click</button>
  </form>

  <div class="w-auto my-20 mt-30 ml-80 shadow-lg sm:rounded-lg">
    <table
      class="w-full text-sm text-left mt-5 text-gray-500 dark:text-gray-400"
    >
      <thead class="text-xs text-gray-700 uppercase dark:text-gray-400">
        <tr class="mx-auto">
          <th scope="col" class="px-6 py-3 bg-gray-50 dark:bg-gray-800">ID</th>
          <th scope="col" class="px-6 py-3">NAME</th>
          <th scope="col" class="px-6 py-3 bg-gray-50 dark:bg-gray-800">
            IMAGE
          </th>
          <th scope="col" class="px-6 py-3 bg-gray-50 dark:bg-gray-800">
            Action
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="border-b border-gray-200 dark:border-gray-700"
          v-for="brand in brands"
          :key="brand"
        >
          <th
            scope="row"
            class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap bg-gray-50 dark:text-white dark:bg-gray-800"
          >
            {{ brand.id }}
          </th>
          <td class="px-6 py-4">{{ brand.name }}</td>
          <td class="px-6 py-4 bg-gray-50 dark:bg-gray-800">
            <img class="w-20" :src="`${brand.image}`" alt="" srcset="" />
          </td>
          <td class="px-6 py-4">
            <button
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 mx-5 px-4 rounded"
            >
              Update
            </button>
            <button
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { ref } from "vue";
import axios from "axios";

const brandName = ref("");
const image = ref(null);
const fileInput = ref(null);

const fetchData = () => {
  axios
    .get("http://localhost:8088/api/v1/brand/getAll")
    .then((response) => {
      console.log(response.data.data);
    })
    .catch((error) => {
      console.error(error);
    });
};

const handleFileChange = () => {
  if (fileInput.value && fileInput.value.files.length > 0) {
    image.value = fileInput.value.files[0];
  }
};

const onSubmitCard = () => {
  const myHeaders = {
    "Content-Type": "multipart/form-data",
    Authorization:
      "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYW0xMjMiLCJpYXQiOjE2OTIxNjY2OTEsImV4cCI6MTY5MzAzMDY5MX0.cF7QW9qwDE3yknPLZfIP4NJ-Jwxrb9wzcaaF7hc9WsWEynGJjLOhdQfvrnYl93Ee5g4Wr5wKFl_LomCjm-CDBw",
    Cookie: "JSESSIONID=9BE29DB5CD16617C63926A99578611DF",
  };

  const formdata = new FormData();
  formdata.append("file", image.value);
  formdata.append("name", brandName.value);

  const requestOptions = {
    method: "POST",
    headers: myHeaders,
    body: formdata,
    redirect: "follow",
  };

  fetch("http://localhost:8088/api/v1/brand/add-brand", requestOptions)
    .then((response) => response.json())
    .then((result) => console.log(result))
    .catch((error) => console.log("error", error));
};

fetchData();
</script>
