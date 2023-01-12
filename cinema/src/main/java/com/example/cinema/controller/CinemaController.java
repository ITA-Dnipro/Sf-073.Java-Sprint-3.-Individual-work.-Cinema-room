package com.example.cinema.controller;

import com.example.cinema.model.CinemaRoom;
import org.springframework.web.bind.annotation.GetMapping;

public class CinemaController {

    @GetMapping("/seats")
    CinemaRoom foo() {
        return new CinemaRoom();
    }
}
