<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
    :persistent="true"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Sell book </v-toolbar>
        <p>Title: {{ book.title }}</p>
        <p>Author : {{ book.author }}</p>
        <p>Genre: {{ book.genre }}</p>
        <p>Price: {{ book.price }}</p>
        <p>Stock: {{ book.quantity }}</p>
        <v-form>
          <v-text-field v-model="quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">Sell</v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookSaleDialog",
  data() {
    return {
      quantity: 0,
    };
  },
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.books
        .sell(this.book.id, { quantity: this.quantity })
        .then(() => this.$emit("refresh"))
        .catch((error) => {
          alert(error.response.data);
        });
    },
    close() {
      this.$emit("close-dialog");
    },
  },
};
</script>

<style scoped></style>
