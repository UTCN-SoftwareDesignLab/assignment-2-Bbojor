package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class PdfBookReportService extends BookReportService {


    public PdfBookReportService(BookRepository bookRepository) {
        super(bookRepository);
    }

    @Override
    public String export() throws IOException {

        String fileName = "report_" + new Date().toString().replace(' ', '-') + ".pdf";

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        contentStream.newLineAtOffset(25, 700);

        Iterable<Book> outOfStockBooks = getOutOfStockBooks();

        for (Book b: outOfStockBooks) {
            contentStream.showText(b.getTitle() + " - " + b.getAuthor());
            contentStream.newLineAtOffset(0, -15);
        }

        contentStream.endText();
        contentStream.close();

        document.save(fileName);
        document.close();

        return fileName;
    }

    @Override
    public BookReportType getType() {
        return BookReportType.PDF;
    }
}
