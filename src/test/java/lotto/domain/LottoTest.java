package lotto.domain;

import lotto.exceptions.LottoException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoTest {
	@ParameterizedTest
	@MethodSource("generateLottoInput")
	void Lotto(String input, List<Integer> sorted) {
		// when
		Lotto lotto = Lotto.of(input);

		// then
		List<LottoNumber> expected = sorted.stream()
				.map(LottoNumber::of)
				.collect(Collectors.toList());

		Assertions.assertThat(lotto.getLottoNumbers())
				.isEqualTo(expected);
	}

	static Stream<Arguments> generateLottoInput() {
		return Stream.of(
				Arguments.of("1, 10, 3, 11, 5, 6",
						Arrays.asList(1, 3, 5, 6, 10, 11)),
				Arguments.of("1, 2, 3, 45, 44, 43",
						Arrays.asList(1, 2, 3, 43, 44, 45)),
				Arguments.of("6, 5, 4, 3, 2, 1",
						Arrays.asList(1, 2, 3, 4, 5, 6))
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
	void Lotto_NotSizeSix_ShouldThrowException(String input) {
		// then
		Assertions.assertThatThrownBy(() -> {
			// when
			Lotto.of(input);
		}).isInstanceOf(LottoException.class)
				.hasMessageMatching(LottoException.MESSAGE);
	}

	@ParameterizedTest
	@ValueSource(strings = {"1,2,3,4,4,5", "1,3,2,3,4,3", "1,2,3,1,2,3"})
	void SerialLottoNumber_DuplicatedNumbers_ShouldThrowException(String input) {
		// then
		Assertions.assertThatThrownBy(() -> {
			// when
			Lotto.of(input);
		}).isInstanceOf(LottoException.class)
				.hasMessageMatching(LottoException.MESSAGE);
	}
}
