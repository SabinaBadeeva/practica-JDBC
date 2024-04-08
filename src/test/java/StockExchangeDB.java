import java.sql.*;

/**
 * Класс для подключения к DB
 */
public class StockExchangeDB extends DBConstant {


    public StockExchangeDB() throws SQLException {
    }

    // JDBC variables for opening and managing connection
    static Connection con; // Переменная для открытия и управления СУБД

    protected static void dataBaseConnect() {


        try {
            con = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASSWORD);//соединениесБД
            //System.out.println("Соединение с СУБД выполнено.");
        } catch (
                SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASSWORD)) {
            //System.out.println("Соединение с СУБД отключено.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}}




