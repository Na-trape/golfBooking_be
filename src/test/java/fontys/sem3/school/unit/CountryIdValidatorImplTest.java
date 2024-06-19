package fontys.sem3.school.unit;

import fontys.sem3.school.business.exception.InvalidCountryException;
import fontys.sem3.school.business.impl.CountryIdValidatorImpl;
import fontys.sem3.school.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CountryIdValidatorImplTest {
    private CountryRepository countryRepository;
    private CountryIdValidatorImpl countryIdValidator;

    @BeforeEach
    public void setup() {
        countryRepository = Mockito.mock(CountryRepository.class);
        countryIdValidator = new CountryIdValidatorImpl(countryRepository);
    }

    @Test
    public void validateId_ValidId_NoExceptionThrown() {
        Long validId = 1L;
        when(countryRepository.existsById(validId)).thenReturn(true);

        countryIdValidator.validateId(validId);
    }

    @Test
    public void validateId_InvalidId_InvalidCountryExceptionThrown() {
        Long invalidId = 2L;
        when(countryRepository.existsById(invalidId)).thenReturn(false);

        assertThrows(InvalidCountryException.class, () -> countryIdValidator.validateId(invalidId));
    }
}
