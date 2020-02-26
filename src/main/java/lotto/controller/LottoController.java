package lotto.controller;

import lotto.domain.*;
import lotto.domain.LottoTicketFactory.RandomLottoTicketFactory;
import lotto.exceptions.LottoNumberIllegalArgumentException;
import lotto.exceptions.PurchaseMoneyIllegalArgumentException;
import lotto.exceptions.WinningLottoNumbersIllegalArgumentException;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class LottoController {
	public static void run() {
		PurchaseMoney purchaseMoney = prepareLotto();

		PurchaseMoney manualTicketMoney = createManualTicketMoney();
		PurchaseMoney autoTicketMoney
				= purchaseMoney.subtract(manualTicketMoney);

		PurchasedLottoTickets purchasedLottoTickets
				= purchaseLottoTickets(manualTicketMoney, autoTicketMoney);

		WinningLottoNumbers winningLottoNumbers = createWinningLottoNumbers();

		produceLottoResult(purchaseMoney, purchasedLottoTickets, winningLottoNumbers);
	}

	private static PurchasedLottoTickets purchaseLottoTickets(
			PurchaseMoney manualTicketMoney, PurchaseMoney autoTicketMoney) {

		PurchasedLottoTickets manualTickets = purchaseManualLotto(manualTicketMoney);
		PurchasedLottoTickets autoLottoTickets = purchaseAutoLotto(autoTicketMoney);
		OutputView.printPurchasedLottoTickets(manualTickets, autoLottoTickets);
		return manualTickets.addAll(autoLottoTickets);
	}

	private static PurchaseMoney createManualTicketMoney() {
		int manualTicketNumber = InputView.inputManualTicketNumber();
		return PurchaseMoney.of(manualTicketNumber);
	}

	private static void produceLottoResult(
			PurchaseMoney purchaseMoney,
			PurchasedLottoTickets purchasedLottoTickets,
			WinningLottoNumbers winningLottoNumbers) {
		LottoResult lottoResult = LottoResult.of(purchasedLottoTickets, winningLottoNumbers);
		OutputView.printLottoResult(lottoResult);
		OutputView.printEarningRate(lottoResult.calculateEarningPercentage(purchaseMoney));
	}

	private static PurchasedLottoTickets purchaseManualLotto(
			PurchaseMoney manualTicketMoney) {

		List<SerialLottoNumber> serialLottoNumbers = new ArrayList<>();
		for (int i = 0; i < manualTicketMoney.countPurchasedTickets(); i++) {
			String input = InputView.inputManualLottoNumbers(i == 0);
			serialLottoNumbers.add(SerialLottoNumber.of(input));
		}

		return new PurchasedLottoTickets(serialLottoNumbers);
	}

	private static PurchasedLottoTickets purchaseAutoLotto(
			PurchaseMoney purchaseMoney) {

		return PurchasedLottoTickets.of(purchaseMoney,
				new RandomLottoTicketFactory());
	}

	private static PurchaseMoney prepareLotto() {
		PurchaseMoney purchaseMoney = createPurchaseMoney();
		OutputView.printPurchasedLottoTicketsCount(purchaseMoney);
		return purchaseMoney;
	}

	private static WinningLottoNumbers createWinningLottoNumbers() {
		WinningLottoNumbers winningLottoNumbers;
		do {
			winningLottoNumbers = createWinningLottoNumbersIfValid();
		} while (winningLottoNumbers == null);

		return winningLottoNumbers;
	}

	private static WinningLottoNumbers createWinningLottoNumbersIfValid() {
		try {
			return new WinningLottoNumbers(createWinningNumber(), createBonusNumber());
		} catch (WinningLottoNumbersIllegalArgumentException e) {
			OutputView.printWarningMessage(e.getMessage());
			return null;
		}
	}

	private static PurchaseMoney createPurchaseMoney() {
		PurchaseMoney purchaseMoney;
		do {
			purchaseMoney = createPurchaseMoneyIfValid();
		} while (purchaseMoney == null);

		return purchaseMoney;
	}

	private static PurchaseMoney createPurchaseMoneyIfValid() {
		try {
			return new PurchaseMoney(InputView.inputPurchaseMoney());
		} catch (PurchaseMoneyIllegalArgumentException e) {
			OutputView.printWarningMessage(e.getMessage());
			return null;
		}
	}

	private static SerialLottoNumber createWinningNumber() {
		SerialLottoNumber winningNumber;
		do {
			winningNumber = createWinningNumbersIfValid();
		} while (winningNumber == null);

		return winningNumber;
	}

	private static SerialLottoNumber createWinningNumbersIfValid() {
		try {
			return SerialLottoNumber.of(InputView.inputWinningNumbers());
		} catch (IllegalArgumentException e) {
			OutputView.printWarningMessage(e.getMessage());
			return null;
		}
	}

	private static LottoNumber createBonusNumber() {
		LottoNumber bonusNumber;
		do {
			bonusNumber = createBonusNumberIfValid();
		} while (bonusNumber == null);

		return bonusNumber;
	}

	private static LottoNumber createBonusNumberIfValid() {
		try {
			return new LottoNumber(InputView.inputBonusNumber());
		} catch (LottoNumberIllegalArgumentException e) {
			OutputView.printWarningMessage(e.getMessage());
			return null;
		}
	}
}
