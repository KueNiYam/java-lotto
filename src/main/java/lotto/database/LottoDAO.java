package lotto.database;

import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.generator.ManualLottoNumbersGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LottoDAO {
    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int NUMBER_START_INDEX = 1;
    private static final int ROUND_INDEX = 7;
    private static final int SELECT_LOTTOS_QUERY_ROUND_INDEX = 1;
    private static final String INSERT_LOTTO_QUERY =
            "INSERT INTO lotto(number1, number2, number3, number4, number5, number6, round)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LOTTOS_QUERY = "SELECT * FROM lotto WHERE round = ?";

    private static LottoDAO lottoDAO;
    private static Connection connection;

    private LottoDAO() {
    }

    public static LottoDAO getInstance(Connection connection) {
        if (lottoDAO == null) {
            lottoDAO = new LottoDAO();
        }
        LottoDAO.connection = connection;
        return lottoDAO;
    }

    public void addAllLottos(Lottos lottos, int round) throws SQLException {
        for (Lotto lotto : lottos.getLottos()) {
            lottoDAO.addLotto(lotto, round);
        }
    }

    public void addLotto(Lotto lotto, int round) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_LOTTO_QUERY)) {
            List<Integer> lottoNumbers = lotto.getLottoNumbers();
            for (int i = 0; i < LOTTO_NUMBER_COUNT; i++) {
                pstmt.setInt(NUMBER_START_INDEX + i, lottoNumbers.get(i));
            }
            pstmt.setInt(ROUND_INDEX, round);
            pstmt.executeUpdate();
        }
    }

    public Lottos findLottosByRound(int round) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(SELECT_LOTTOS_QUERY)) {
            pstmt.setInt(SELECT_LOTTOS_QUERY_ROUND_INDEX, round);
            return makeLottos(pstmt);
        }
    }

    private Lottos makeLottos(PreparedStatement pstmt) throws SQLException {
        try (ResultSet resultSet = pstmt.executeQuery()) {
            Lottos lottos = new Lottos();
            while (resultSet.next()) {
                lottos.add(makeLotto(resultSet));
            }
            return lottos;
        }
    }

    private Lotto makeLotto(ResultSet resultSet) throws SQLException {
        List<Integer> lottoNumbers = new ArrayList<>();
        for (int i = 0; i < LOTTO_NUMBER_COUNT; i++) {
            lottoNumbers.add(resultSet.getInt(NUMBER_START_INDEX + i));
        }
        return new Lotto(ManualLottoNumbersGenerator.getInstance(lottoNumbers).generate());
    }
}