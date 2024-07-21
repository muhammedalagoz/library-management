package com.library.management.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest{
    public String title;
    public String author;
    public String isbn;
    public LocalDate publishedDate;
    public Integer quantity;
    public String genre;
}
