/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.data.rest;

import androidx.annotation.Nullable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import edu.wgu.student.tomasgray.capstone.data.model.Term;
import retrofit2.Response;

public class TermWebServiceTest {
    private static final String LOG_TAG = "TermWSTest";

    private static TermWebService webService;
    private static final String userId = "39a8b804-3184-4f61-b976-f16e857fb10a";
    private static final String authKey = userId + ":W23LFtYwkj1dR1rl4bhPYxdNmsvJZkZSZ6PP8po/jC4=";

    @BeforeAll
    static void setup() {
        webService
                = RestClient
                .getInstance()
                .create(TermWebService.class);
    }

    @DisplayName("Retrieve all terms")
    @ParameterizedTest(name = "Testing term: ({index}) {0}")
    @MethodSource("getAllTerms")
    void testAllTerms(Term term) {
        System.out.println("Got term:");
        System.out.println(term);

        // Validate all fields are present
        assert term.getStartDate().isAfter(LocalDate.of(2000, 1, 1))
                && term.getStartDate().isBefore(LocalDate.of(2020, 1, 1));
        assert term.getEndDate() != null;
    }

    @Nullable
    static private Stream<Arguments> getAllTerms() {
        try {
            Response<List<Term>> response
                    = webService
                    .getAllTerms(authKey, userId)
                    .execute();

            // Extract response body
            List<Term> terms = response.body();
            assert terms != null;
            return terms
                    .stream()
                    .map(Arguments::of);
        } catch (IOException | NullPointerException e) {
            System.out.println("Could not fetch terms data");
            return null;
        }
    }

}
