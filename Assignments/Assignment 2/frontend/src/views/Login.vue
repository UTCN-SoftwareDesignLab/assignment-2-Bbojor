<template>
  <v-container>
    <v-layout row justify-center pt-3>
      <v-flex xs4 class="grey lighten-4">
        <v-container class="text-xs-center">
          <v-card flat>
            <v-card-title primary-title>
              <h4>Login</h4>
            </v-card-title>
            <v-form>
              <v-text-field
                prepend-icon="person"
                name="Username"
                label="Username"
                v-model="login.username"
                validate-on-blur
              ></v-text-field>
              <v-text-field
                prepend-icon="lock"
                name="Password"
                label="Password"
                type="password"
                v-model="login.password"
                validate-on-blur
              ></v-text-field>
              <v-card-actions>
                <v-container>
                  <v-layout row justify-center>
                    <v-btn primary large block @click="attemptLogin"
                      >Login</v-btn
                    >
                  </v-layout>
                  <v-layout v-if="isLoggedIn" row justify-center>
                    <v-btn @click="logout"> Logout </v-btn>
                  </v-layout>
                </v-container>
              </v-card-actions>
            </v-form>
          </v-card>
        </v-container>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import router from "../router";

export default {
  name: "Login",

  data: () => ({
    mode: "login",
    login: {
      email: "",
      username: "",
      password: "",
    },
  }),
  methods: {
    attemptLogin() {
      this.$store
        .dispatch("auth/login", this.login)
        .then(() => {
          console.log("here");
          if (this.$store.state.auth.status.loggedIn) {
            if (this.$store.getters["auth/isAdmin"]) {
              router.push("/users");
            } else {
              router.push("/books");
            }
          } else {
            alert("Invalid credentials!");
          }
        })
        .catch(() => {
          alert("Invalid credentials!");
        });
    },
    logout() {
      this.$store.dispatch("auth/logout");
    },
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
  },
};
</script>
