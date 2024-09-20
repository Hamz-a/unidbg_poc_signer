package me.bhamza.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SignerService {
    private Signer unidbgSigner;

    public SignerService(@Value("${so-file}") String so_file) {
        this.unidbgSigner = new Signer(so_file);
    }

    String sign(String message) {
        return this.unidbgSigner.sign(message);
    }
}
