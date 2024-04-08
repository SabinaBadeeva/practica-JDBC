import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TestDBNotExoticProduct extends StockExchangeDB {
    public TestDBNotExoticProduct() throws SQLException {

    }

    @Test
    @Description(" Получить начальные данные " +
            "(food_id, food_name, food_type, food_exotic (где 1 = true, 0 = false)" +
            " из таблицы Food " +
            "метод: Select * From food ")
    public void getProductDB() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //создать объект Statement
        Statement stmt = con.createStatement();
        // ввести запрос
        String sql = "SELECT * FROM food";
        // вернуть экземпляр ResultSet c помощью executeQuery
        //result - указатель на первую строку с выборки
        ResultSet rs = stmt.executeQuery(sql);
        //чтобы вывести данные используем метод next() ,
        // с помощью которого переходим к следующему элементу
        int count = 0;
        while (rs.next()) {
            Integer id = rs.getInt("food_id");
            String name = rs.getString("food_name");
            String type = rs.getString("food_type");
            String isExotic = rs.getString("food_exotic");
            //Вывести результат запроса
            String output = "Товар №%d: %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, id, name, type, isExotic));

        }
    }

    @Test
    @Description("Добавление товара по типу Овощ и Фрукт с не активным чекбоксом экзотический." +
            " INSERT INTO food (food_id, food_name, food_type, food_exotic )" +
            " VALUES (5, 'Морковь', 'Vegetable', 0 )" +
            "      и (6, 'Слива', 'Fruit', 0;" +
            "При успешном добавлении проверить: " +
            "что новый продукт  Морковь с типом Овощ и значением not exotic = false добавился в таблицу" +
            "что новый продукт  Слива с типом Fruit и значением not exotic = false добавился в таблицу")
    public void addNewProdNotExotic() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        String insert = "insert into food (food_id, food_name, food_type, food_exotic) values (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(insert);

        // Добавить данные
        Object[][] data = {
                {5, "Морковь", "Vegetable", false},
                {6, "Слива", "Fruit", false},

        };
        // Добавить batches параметры
        for (Object[] row : data) {
            ps.setInt(1, (int) row[0]); // id
            ps.setString(2, (String) row[1]); // name
            ps.setString(3, (String) row[2]); // type
            ps.setBoolean(4, (Boolean) row[3]); // exotic
            ps.addBatch();
        }
        // Выполнить batches
        int[] batchResults = ps.executeBatch();
        System.out.println("\n====================");
        System.out.println("Товары успешно добавлены");
//        // Проверить, что добавились нужные товары с нужными значениями
        String sql = "SELECT * FROM food order by food_id desc limit 2";
        //PreparedStatement для параметризованного SQL-запроса
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        //Проверить, что в таблице появился новый товар с необходимыми параметрами
        int count = 0;
        while (resultSet.next()) {
            String name = resultSet.getString("food_name");
            String type = resultSet.getString("food_type");
            int booleanExotic = resultSet.getInt("food_exotic");
            String output = "Товар №%d: %s - %s - %s";
            System.out.println(String.format(output, ++count, name, type, booleanExotic));
        }
    }


    @Test
    @Description("Удалить новый товар Слива из таблицы")
    public void deleteNewProductNotExoticFruit() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //Ввести запрос
        String sql = "DELETE FROM food WHERE food_id=?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, 6);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("\n====================");
            System.out.println("Товар успешно удален");
            System.out.println("\n====================");
        }
    }

    @Test
    @Description("Удалить новый товар Слива из таблицы")
    public void deleteNewProductNotExoticegeVegetable() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //Ввести запрос
        String sql = "DELETE FROM food WHERE food_id=?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, 5);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Товар успешно удален");
        }
    }

    @Test
    @Description("Проверить, что в таблице осталось " +
            "начальное количество продуктов = 4 и " +
            "начальное содержимое: Апельсин, Капуста, Помидо, Яблоко")
    public void initialCountProduct() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //создать объект Statement
        Statement stmt = con.createStatement();
        // ввести запрос
        String sql = "SELECT COUNT (*) FROM food";
        //Вернуть Result
        ResultSet rs = stmt.executeQuery(sql);
        //чтобы вывести данные используем метод next() ,
        rs.next();
        //Вывести количество строк
        System.out.println("\n====================\n");
        System.out.println("Таблица содержит " + rs.getInt("count(*)") + " строки");
        //Прверить сожержимое таблицы
        String sql1 = "SELECT * FROM food";
        ResultSet rs1 = stmt.executeQuery(sql1);
        //чтобы вывести данные используем метод next() ,
        int count = 0;
        while (rs1.next()) {
            Integer id = rs1.getInt("food_id");
            String name = rs1.getString("food_name");
            String type = rs1.getString("food_type");
            String isExotic = rs1.getString("food_exotic");
            //Вывести результат запроса
            String output = "Товар №%d: %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, id, name, type, isExotic));
        }
    }
}




