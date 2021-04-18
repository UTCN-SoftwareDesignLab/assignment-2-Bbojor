package com.example.assignment2.book;

import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.report.ReportFactoryService;
import com.example.assignment2.report.BookReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public BookDTO create(@RequestBody BookDTO book) { return bookService.create(book);}

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) { return bookService.edit(id, book);}

    @GetMapping(ENTITY)
    public BookDTO getBook(@PathVariable Long id) { return bookService.getBook(id); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { bookService.delete(id);}

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable BookReportType type) {
        return reportFactoryService.getReportService(type).export();
    }
}
