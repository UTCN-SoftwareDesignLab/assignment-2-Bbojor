import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks(searchString) {
    if (searchString !== "") searchString = "/?search=" + searchString;
    return HTTP.get(BASE_URL + "/books" + searchString, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportReport(type) {
    return HTTP.get(BASE_URL + "/books/export/" + type, {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      //indian powa
      let fileURL = window.URL.createObjectURL(new Blob([response.data]));
      let fileLink = document.createElement("a");
      fileLink.href = fileURL;
      fileLink.setAttribute(
        "download",
        "report." + type.toString().toLowerCase()
      );
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  },
  create(book) {
    return HTTP.post(BASE_URL + "/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  sell(id, quantity) {
    return HTTP.patch(BASE_URL + "/books/" + id, quantity, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(book) {
    return HTTP.put(BASE_URL + "/books/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/books/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
