package lotto.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PurchasedLottoTicketsTest {
	@Test
	void PurchasedLottoTickets() {
		// given
		List<SerialLottoNumber> purchasedLottoTickets = new ArrayList<>();
		int[][] input = {{1, 10, 3, 11, 5, 6}, {5, 10, 45, 3, 17, 2}, {4, 7, 13, 19, 22, 37}};
		for (int[] element: input) {
			List<LottoNumber> lottoNumbers = Arrays.stream(element)
					.mapToObj(LottoNumber::new)
					.collect(Collectors.toList());
			SerialLottoNumber lottoTicket = new SerialLottoNumber(lottoNumbers);
			purchasedLottoTickets.add(lottoTicket);
		}

		// when
		PurchasedLottoTickets result = new PurchasedLottoTickets(purchasedLottoTickets);

		// then
		Assertions.assertThat(result.getPurchasedLottoTickets())
				.isEqualTo(purchasedLottoTickets);
	}

	@Test
	void createLottoResult() {
		// given
		List<SerialLottoNumber> purchasedLottoTickets = new ArrayList<>();
		int[][] input = {{1, 10, 3, 11, 5, 6}, {5, 10, 45, 3, 17, 2}, {4, 7, 13, 19, 22, 37}};
		for (int[] element: input) {
			List<LottoNumber> lottoNumbers = Arrays.stream(element)
					.mapToObj(LottoNumber::new)
					.collect(Collectors.toList());
			SerialLottoNumber lottoTicket = new SerialLottoNumber(lottoNumbers);
			purchasedLottoTickets.add(lottoTicket);
		}
		PurchasedLottoTickets given = new PurchasedLottoTickets(purchasedLottoTickets);

		List<LottoNumber> winning = Stream.of(1, 2, 3, 4, 5, 6)
				.map(LottoNumber::new)
				.collect(Collectors.toUnmodifiableList());
		SerialLottoNumber winningNumbers = new SerialLottoNumber(winning);
		LottoNumber bonus = new LottoNumber(7);
		WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(winningNumbers, bonus);

		// when
//		LottoResult lottoResult = given.findMatchingWinningTypesWith(winningLottoNumbers);

	}
}