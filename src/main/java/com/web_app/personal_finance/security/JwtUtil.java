package com.web_app.personal_finance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
    @Value("${jwt.private-key-path}")
    private Resource privateKeyPath;

    @Value("${jwt.public-key-path}")
    private Resource publicKeyPath;
    
    
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void initKeys() {
        try {
            this.privateKey = loadPrivateKey();
            this.publicKey = loadPublicKey();
        } catch (Exception e) {
            logger.error("Error loading RSA keys", e);
            throw new IllegalStateException("Error loading RSA keys: " + e.getMessage(), e);
        }
    }

    private PrivateKey loadPrivateKey() throws Exception {
        String privateKeyPEM = new String(Files.readAllBytes(privateKeyPath.getFile().toPath()));
        // Remove the first and last lines
        String privateKeyPEMFormatted = privateKeyPEM
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", ""); // Remove whitespace
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEMFormatted);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey loadPublicKey() throws Exception {
        String publicKeyPEM = new String(Files.readAllBytes(publicKeyPath.getFile().toPath()));
        // Remove the first and last lines
        String publicKeyPEMFormatted = publicKeyPEM
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", ""); // Remove whitespace
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEMFormatted);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String generateToken(String username,String userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid JWT token: " + e.getMessage(), e);
        }
    }
}
