package lotto.domain;

import lotto.exceptions.WinningLottoNumbersIllegalArgumentException;

import java.util.Objects;

public class WinningLottoNumbers {
	private final Lotto winningLottoNumbers;
	private final LottoNumber bonus;

	public WinningLottoNumbers(final Lotto winningLottoNumbers, final LottoNumber bonus) {
		checkWinningNumbersContainsBonus(winningLottoNumbers, bonus);

		this.winningLottoNumbers = winningLottoNumbers;
		this.bonus = bonus;
	}

	private void checkWinningNumbersContainsBonus(Lotto winningLottoNumbers, LottoNumber bonus) {
		if (winningLottoNumbers.contains(bonus)) {
			throw new WinningLottoNumbersIllegalArgumentException();
		}
	}

	public WinningType findMatchingWinningTypeWith(Lotto serialLottoNumber) {
		int sameNumberCount = serialLottoNumber.countMatching(winningLottoNumbers);
		boolean isContainsBonus = serialLottoNumber.contains(bonus);

		return WinningType.getWinningType(sameNumberCount, isContainsBonus);
	}

	public Lotto getWinningLottoNumbers() {
		return winningLottoNumbers;
	}

	public LottoNumber getBonus() {
		return bonus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WinningLottoNumbers that = (WinningLottoNumbers) o;
		return Objects.equals(winningLottoNumbers, that.winningLottoNumbers) &&
				Objects.equals(bonus, that.bonus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(winningLottoNumbers, bonus);
	}
}
