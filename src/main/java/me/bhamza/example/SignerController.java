package me.bhamza.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SignerController {
    @Autowired
    public SignerService signerService;

    @GetMapping("/")
    public String index() {
        return "Hello signer!";
    }

    @RequestMapping(path = "/sign", consumes = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST})
    public String sign(@RequestBody Map<String, String> payload) {
        return signerService.sign(payload.getOrDefault("message", ""));
    }
}
