package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.search.PredicateBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PdfBookReportService implements BookReportService {

    private final BookRepository bookRepository;

    @Override
    public String export()  {

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();

            contentStream.setFont(PDType1Font.COURIER, 12);

            contentStream.newLineAtOffset(25, 500);

            PredicateBuilder<Book> predicateBuilder = new PredicateBuilder<>(Book.class).with("quantity", "<", "0");
            Iterable<Book> outOfStockBooks = bookRepository.findAll(predicateBuilder.build());

            for (Book b: outOfStockBooks) {
                contentStream.showText(b.getTitle() + " - " + b.getAuthor());
                contentStream.newLineAtOffset(0, -15);
            }

            contentStream.endText();
            contentStream.close();

            document.save( "report_" + new Date().toString().replace(' ', '-') + ".pdf");
            document.close();

        } catch (IOException e) {
            return "ERROR";
        }
        return "PDF";
    }

    @Override
    public BookReportType getType() {
        return BookReportType.PDF;
    }
}
