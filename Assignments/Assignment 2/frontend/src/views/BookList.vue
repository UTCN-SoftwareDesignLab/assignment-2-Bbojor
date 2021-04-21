<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-if="!isAdmin"
        label="Title"
        single-line
        v-model="searchParams.title"
        hide-details
      ></v-text-field>
      <v-text-field
        v-if="!isAdmin"
        label="Author"
        single-line
        v-model="searchParams.author"
        hide-details
      ></v-text-field>
      <v-text-field
        v-if="!isAdmin"
        label="Genre"
        single-line
        v-model="searchParams.genre"
        hide-details
      ></v-text-field>
      <v-btn v-if="!isAdmin" @click="searchBooks" icon>
        <v-icon>mdi-magnify </v-icon></v-btn
      >
      <v-btn v-if="isAdmin" @click="addBook">Add Book</v-btn>
      <v-btn v-if="isAdmin" @click="genPdfReport">PDF Report</v-btn>
      <v-btn v-if="isAdmin" @click="genCsvReport">CSV Report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      @click:row="openDialog"
    ></v-data-table>
    <BookDialog
      :opened="editDialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></BookDialog>
    <BookSaleDialog
      :opened="saleDialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
      @close-dialog="closeDialog"
    ></BookSaleDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import BookSaleDialog from "../components/BookSaleDialog";

export default {
  name: "BookList",
  components: { BookDialog, BookSaleDialog },
  data() {
    return {
      items: [],
      searchParams: {
        title: "",
        author: "",
        genre: "",
      },
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      editDialogVisible: false,
      saleDialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    genPdfReport() {
      api.books.exportReport("PDF");
    },
    genCsvReport() {
      api.books.exportReport("CSV");
    },
    openDialog(book) {
      this.selectedBook = book;
      if (this.isAdmin) this.editDialogVisible = true;
      else this.saleDialogVisible = true;
    },
    addBook() {
      this.selectedBook = {};
      this.editDialogVisible = true;
    },
    async refreshList() {
      this.selectedBook = {};
      let searchString = "";
      if (this.searchParams.title)
        searchString += "title:" + this.searchParams.title + ",";
      if (this.searchParams.author)
        searchString += "author:" + this.searchParams.author + ",";
      if (this.searchParams.genre)
        searchString += "genre:" + this.searchParams.genre;
      this.items = await api.books.allBooks(searchString);
    },
    searchBooks() {
      this.refreshList();
    },
    closeDialog() {
      if (this.isAdmin) this.editDialogVisible = false;
      else this.saleDialogVisible = false;
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },
  computed: {
    isAdmin() {
      return this.$store.getters["auth/isAdmin"];
    },
  },
};
</script>

<style scoped></style>
