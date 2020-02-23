package lotto.domain;

import lotto.domain.LottoTicketFactory.LottoTicketFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PurchasedLottoTickets {
	private final List<SerialLottoNumber> purchasedLottoTickets;

	public PurchasedLottoTickets(final List<SerialLottoNumber> purchasedLottoTickets) {
		this.purchasedLottoTickets = Collections.unmodifiableList(purchasedLottoTickets);
	}

	public static PurchasedLottoTickets of(PurchaseMoney purchaseMoney,
											   LottoTicketFactory LottoTicketFactory) {
		List<SerialLottoNumber> purchasedLottoTickets = new ArrayList<>();

		int count = purchaseMoney.countPurchasedTickets();
		for (int i = 0; i < count; i++) {
			purchasedLottoTickets.add(LottoTicketFactory.createLottoTicket());
		}

		return new PurchasedLottoTickets(purchasedLottoTickets);
	}

	public List<SerialLottoNumber> getPurchasedLottoTickets() {
		return Collections.unmodifiableList(purchasedLottoTickets);
	}

	public List<WinningType> findMatchingWinningTypesWith(WinningLottoNumbers winningLottoNumbers) {
		return purchasedLottoTickets.stream()
				.map(winningLottoNumbers::findMatchingWinningTypeWith)
				.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchasedLottoTickets that = (PurchasedLottoTickets) o;
		return Objects.equals(purchasedLottoTickets, that.purchasedLottoTickets);
	}

	@Override
	public int hashCode() {
		return Objects.hash(purchasedLottoTickets);
	}
}
