package com.example.assignment2.book;

import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.book.dto.BookSaleRequest;
import com.example.assignment2.report.ReportFactoryService;
import com.example.assignment2.report.BookReportType;
import com.example.assignment2.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignment2.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;
    public final ReportFactoryService reportFactoryService;

    @GetMapping
    public List<BookDTO> findAll(@RequestParam(value = "search", required = false) String search) { return bookService.findAll(search); }

    @PostMapping
    public BookDTO create(@Valid @RequestBody BookDTO book) { return bookService.create(book);}

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @Valid @RequestBody BookDTO book) { return bookService.edit(id, book);}

    @GetMapping(ENTITY)
    public BookDTO getBook(@PathVariable Long id) { return bookService.getBook(id); }

    @PatchMapping(ENTITY)
    public ResponseEntity<?> sellBook(@PathVariable Long id, @Valid @RequestBody BookSaleRequest bookSaleRequest) {
        BookDTO book = bookService.sell(id, bookSaleRequest.getQuantity());
        if(book == null)
            return ResponseEntity.badRequest().body("Available stock less than requested quantity");
        else
            return ResponseEntity.ok(new MessageResponse("Book(s) sold"));
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { bookService.delete(id);}

    @GetMapping(
            value = EXPORT_REPORT,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] exportReport(@PathVariable BookReportType type) throws IOException {
        String file = reportFactoryService.getReportService(type).export();
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

}
