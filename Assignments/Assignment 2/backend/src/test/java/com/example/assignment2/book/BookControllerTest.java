package com.example.assignment2.book;

import com.example.assignment2.BaseControllerTest;
import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.report.CsvBookReportService;
import com.example.assignment2.report.PdfBookReportService;
import com.example.assignment2.report.ReportFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.assignment2.UrlMapping.*;
import static com.example.assignment2.report.BookReportType.CSV;
import static com.example.assignment2.report.BookReportType.PDF;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {


    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportFactoryService reportFactoryService;

    @Mock
    private PdfBookReportService pdfReportService;

    @Mock
    private CsvBookReportService csvReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportFactoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {

        List<BookDTO> books = new ArrayList<>();

        BookDTO book1 = BookDTO.builder()
                .author("Isaac Asimov")
                .title("The Foundation")
                .genre("Sci-Fi")
                .id(1L)
                .price(20.0)
                .quantity(15)
                .build();

        BookDTO book2 = BookDTO.builder()
                .author("Stephen King")
                .title("The Shining")
                .genre("Horror")
                .id(2L)
                .price(20.0)
                .quantity(15)
                .build();

        BookDTO book3 = BookDTO.builder()
                .author("H.P. Lovecraft")
                .title("1000 pages of just the n-word")
                .genre("Horror")
                .id(3L)
                .price(20.0)
                .quantity(15)
                .build();

        BookDTO book4 = BookDTO.builder()
                .author("Stephen King")
                .title("It")
                .genre("Horror")
                .id(4L)
                .price(20.0)
                .quantity(0)
                .build();

        BookDTO book5 = BookDTO.builder()
                .author("Some Dude")
                .title("Title with foundation in it")
                .genre("Self help")
                .id(5L)
                .price(20.0)
                .quantity(0)
                .build();

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);

        List<BookDTO> r1 = new ArrayList<>();
        r1.add(book2);

        List<BookDTO> r2 = new ArrayList<>();
        r2.add(book2);
        r2.add(book3);
        r2.add(book4);

        List<BookDTO> r3 = new ArrayList<>();
        r3.add(book2);
        r3.add(book3);

        List<BookDTO> r4 = new ArrayList<>();
        r4.add(book4);
        r4.add(book5);

        when(bookService.findAll(null)).thenReturn(books);
        when(bookService.findAll("title:Shining")).thenReturn(r1);
        when(bookService.findAll("genre:horror")).thenReturn(r2);
        when(bookService.findAll("genre:horror,title:the")).thenReturn(r3);
        when(bookService.findAll("quantity:0")).thenReturn(r4);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

        response = mockMvc.perform(get(BOOKS + SEARCH + "title:Shining"));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(r1));

        response = mockMvc.perform(get(BOOKS + SEARCH + "genre:horror"));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(r2));

        response = mockMvc.perform(get(BOOKS + SEARCH + "genre:horror,title:the"));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(r3));

        response = mockMvc.perform(get(BOOKS + SEARCH + "quantity:0"));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(r4));
    }

    @Test
    void create() throws Exception {
        BookDTO book = TestCreationFactory.newBookDTO();

        when(bookService.create(book)).thenReturn(book);

        ResultActions result = performPostWithRequestBody(BOOKS, book);
        result.andExpect(status().isOk()).andExpect(jsonContentToBe(book));
    }

    @Test
    void edit() throws Exception {
        BookDTO book = TestCreationFactory.newBookDTO();
        long id = book.getId();

        when(bookService.edit(id, book)).thenReturn(book);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, book, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void getBook() throws Exception {
        BookDTO book = TestCreationFactory.newBookDTO();
        long id = book.getId();

        when(bookService.getBook(id)).thenReturn(book);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void delete() throws Exception {
        BookDTO book = TestCreationFactory.newBookDTO();
        long id = book.getId();

        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }

    @Test
    void exportReport() throws Exception {
        when(reportFactoryService.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportFactoryService.getReportService(CSV)).thenReturn(csvReportService);
        String pdf = "PDF";
        when(pdfReportService.export()).thenReturn(pdf);
        String csv = "CSV";
        when(csvReportService.export()).thenReturn(csv);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdf));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csv));

    }
}