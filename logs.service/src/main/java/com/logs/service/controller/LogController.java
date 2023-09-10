package com.logs.service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Logs", description = "Manage Logs")
@RequestMapping("/log")
@RestController
public class LogController {
}
