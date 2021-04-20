<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-btn v-if="isAdmin" @click="switchRoute">
        {{ currentPage() === "Books" ? "Users" : "Books" }}
      </v-btn>

      <v-spacer></v-spacer>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";

export default {
  name: "TopBar",
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    switchRoute() {
      console.log(router.currentRoute.name);
      if (router.currentRoute.name === "Users") router.push("/books");
      else router.push("/users");
      this.$forceUpdate();
    },
    currentPage() {
      return router.currentRoute.name;
    },
  },
  computed: {
    isAdmin() {
      return this.$store.getters["auth/isAdmin"];
    },
  },
};
</script>

<style scoped></style>
