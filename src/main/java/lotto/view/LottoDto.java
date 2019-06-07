package lotto.view;

import java.util.ArrayList;
import java.util.List;

public class LottoDto {
    private List<String> numbers;

    public void setNumbers(final List<String> numbers) {
        this.numbers = new ArrayList<>(numbers);
    }

    public List<String> getNumbers() {
        return new ArrayList<>(numbers);
    }
}