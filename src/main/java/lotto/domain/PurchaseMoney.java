package lotto.domain;

import lotto.exceptions.PurchaseMoneyIllegalArgumentException;

import java.util.Objects;

public class PurchaseMoney {
	public static final int POSITIVE_THRESHOLD = 0;
	private static final int LOTTO_PRICE = 1000;

	private final int purchaseMoney;

	public PurchaseMoney(final int purchaseMoney) {
		checkIsLessOrEqualThanZero(purchaseMoney);
		checkIsMultipleOfThousand(purchaseMoney);

		this.purchaseMoney = purchaseMoney;
	}

	private void checkIsMultipleOfThousand(int purchaseMoney) {
		if (purchaseMoney % LOTTO_PRICE != 0) {
			throw new PurchaseMoneyIllegalArgumentException(purchaseMoney);
		}
	}

	private void checkIsLessOrEqualThanZero(int purchaseMoney) {
		if (purchaseMoney <= POSITIVE_THRESHOLD) {
			throw new PurchaseMoneyIllegalArgumentException(purchaseMoney);
		}
	}

	public int countPurchasedTickets() {
		return purchaseMoney / LOTTO_PRICE;
	}

	public double divideBy(double totalEarning) {
		return totalEarning / purchaseMoney;
	}

	public int getPurchaseMoney() {
		return purchaseMoney;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchaseMoney that = (PurchaseMoney) o;
		return purchaseMoney == that.purchaseMoney;
	}

	@Override
	public int hashCode() {
		return Objects.hash(purchaseMoney);
	}
}
