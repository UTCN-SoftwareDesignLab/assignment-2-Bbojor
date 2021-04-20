<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="changePassword">Change Password</v-btn>
          <v-btn v-if="!isNew" @click="deleteUser">Delete</v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.auth
          .register({
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
          })
          .then(() => this.$emit("refresh"))
          .catch((error) => {
            alert(error.response.data);
          });
      } else {
        api.users
          .edit({
            id: this.user.id,
            username: this.user.username,
            email: this.user.email,
            roles: this.user.roles,
          })
          .then(() => this.$emit("refresh"))
          .catch((error) => {
            alert(error.response.data);
          });
      }
    },
    changePassword() {
      api.users
        .changePassword(this.user.id, {
          password: this.user.password,
        })
        .then(() => this.$emit("refresh"))
        .catch((error) => {
          alert(error.response.data);
        });
    },
    deleteUser() {
      api.users.delete(this.user.id).then(() => this.$emit("refresh"));
    },
    close() {
      this.$emit("close-dialog");
      this.refreshList();
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
