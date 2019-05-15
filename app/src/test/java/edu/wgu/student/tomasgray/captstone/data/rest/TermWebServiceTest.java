package edu.wgu.student.tomasgray.captstone.data.rest;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import edu.wgu.student.tomasgray.captstone.data.model.Term;
import retrofit2.Response;

public class TermWebServiceTest
{
    private static final String LOG_TAG = "TermWSTest";

    private static TermWebService webService;

    @BeforeAll
    static void setup()
    {
        webService
            = RestClient
                .getInstance()
                .create(TermWebService.class);
    }

    @DisplayName("Retrieve all terms")
    @ParameterizedTest(name="Testing term: ({index}) {0}")
    @MethodSource("getAllTerms")
    void testAllTerms(Term term)
    {
        System.out.println("Got term:");
        System.out.println(term);

        // Validate all fields are present
        assert term.getTermId() != null;
        assert term.getTitle() != null;
        assert term.getStartDate() != null;
        assert term.getEndDate() != null;
    }

    @Nullable
    static private Stream<Arguments> getAllTerms()
    {
        try {
            Response<List<Term>> response
                    = webService
                        .getAllTerms()
                        .execute();

            // Extract response body
            List<Term> terms = response.body();

            return
                    terms
                        .stream()
                        .map(Arguments::of);
        } catch (IOException | NullPointerException e) {
            System.out.println("Could not fetch terms data");
            return null;
        }
    }

}
