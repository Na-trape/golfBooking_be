package fontys.sem3.school.unit;

import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.configuration.security.token.exception.InvalidAccessTokenException;
import fontys.sem3.school.configuration.security.token.impl.AccessTokenEncoderDecoderImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTokenEncoderDecoderImplTest {
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
    private Key key;

    @BeforeEach
    public void setUp() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key
        String secretKey = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        this.accessTokenEncoderDecoder = new AccessTokenEncoderDecoderImpl(secretKey);
    }

    @Test
    public void testDecodeValidToken() {
        String validToken = Jwts.builder()
                .setSubject("testUser")
                .claim("roles", List.of("PLAYER", "ADMIN"))
                .claim("playerId", 123L)
                .claim("userId", 456L)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        AccessToken accessToken = accessTokenEncoderDecoder.decode(validToken);

        assertNotNull(accessToken);
        assertEquals("testUser", accessToken.getSubject());
        assertEquals(123L, accessToken.getPlayerId());
        assertEquals(456L, accessToken.getUserId());
        assertTrue(accessToken.getRoles().contains("PLAYER"));
        assertTrue(accessToken.getRoles().contains("ADMIN"));
    }

    @Test
    public void testDecodeInvalidToken() {
        String invalidToken = "invalidToken";

        assertThrows(InvalidAccessTokenException.class, () -> {
            accessTokenEncoderDecoder.decode(invalidToken);
        });
    }

    @Test
    public void testDecodeExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .claim("roles", List.of("PLAYER", "ADMIN"))
                .claim("playerId", 123L)
                .claim("userId", 456L)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // Issued an hour ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60)) // Expired a minute ago
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(InvalidAccessTokenException.class, () -> {
            accessTokenEncoderDecoder.decode(expiredToken);
        });
    }

    @Test
    public void testDecodeTokenWithoutRoles() {
        String tokenWithoutRoles = Jwts.builder()
                .setSubject("testUser")
                .claim("playerId", 123L)
                .claim("userId", 456L)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        AccessToken accessToken = accessTokenEncoderDecoder.decode(tokenWithoutRoles);

        assertNotNull(accessToken);
        assertEquals("testUser", accessToken.getSubject());
        assertEquals(123L, accessToken.getPlayerId());
        assertEquals(456L, accessToken.getUserId());
        assertTrue(accessToken.getRoles().isEmpty());
    }
}
