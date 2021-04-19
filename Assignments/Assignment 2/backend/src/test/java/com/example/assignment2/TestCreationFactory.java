package com.example.assignment2;

import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import com.example.assignment2.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomDouble())
                .quantity(randomBoundedInt(1000))
                .build();
    }

    public static BookDTO newBookDTO() {
        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomDouble())
                .quantity(randomBoundedInt(1000))
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static double randomDouble() {
        return new Random().nextDouble();
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
