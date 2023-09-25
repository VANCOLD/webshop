package com.waff.gameverse_backend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class KeyGeneratorTest {

    @Test
    void instanceTest() {
        assertNotNull(new KeyGenerator());
    }

    @Test
    void generateKeyPairTest() {

        // checking if generating works
        var keypair =   KeyGenerator.generateRsaKey();
        assertNotNull(keypair);

        // Checking if two generated keyPairs are valid
        var checkPair = KeyGenerator.generateRsaKey();
        assertThat(keypair).isNotEqualTo(checkPair);

    }
}
