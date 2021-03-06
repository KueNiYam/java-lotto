package lotto.domain;

import lotto.exceptions.LottoNumberIllegalArgumentException;

import java.util.Objects;

public class LottoNumber {
	private static final int MIN = 1;
	private static final int MAX = 60;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LottoNumber that = (LottoNumber) o;
		return lottoNumber == that.lottoNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lottoNumber);
	}

	private int lottoNumber;

	public LottoNumber(int lottoNumber) throws LottoNumberIllegalArgumentException {
		checkIsWithinRange(lottoNumber);
		this.lottoNumber = lottoNumber;
	}

	private void checkIsWithinRange(int number) {
		if (number < MIN || number > MAX) {
			throw new LottoNumberIllegalArgumentException(number);
		}
	}
}
