import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestExoticProduct extends StockExchangeDB {
    public TestExoticProduct() throws SQLException {
    }

    @Test
    @Description(" Получить начальные данные " +
            "( food_name, food_type, food_exotic (где 1 = true, 0 = false)" +
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
            String name = rs.getString("food_name");
            String type = rs.getString("food_type");
            String isExotic = rs.getString("food_exotic");
            //Вывести результат запроса
            String output = "Товар №%d: %s - %s - %s";
            System.out.println(String.format(output, ++count, name, type, isExotic));
        }
    }

    @Test
    @Description("Добавление товара по типу Овощ и Фрукт с активным чекбоксом экзотический." +
            " INSERT INTO food ( food_name, food_type, food_exotic )" +
            " VALUES ( 'Пепино', 'Vegetable', 1)" +
            "      и ( 'Арбуз', 'Fruit', 1;" +
            "При успешном добавлении проверить: " +
            "что новый продукт  Пепино с типом Овощ и значением  exotic = true добавился в таблицу" +
            "что новый продукт  Арбуз с типом Fruit и значением  exotic = true добавился в таблицу")
    public void addNewProdNotExotic() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        String insert = "insert into food ( food_name, food_type, food_exotic) values ( ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(insert);

        // Добавить данные
        Object[][] data = {
                {"Пепино", "Vegetable", true},
                {"Арбуз", "Fruit", true},

        };
        // Добавить batches параметры
        for (Object[] row : data) {
            ps.setString(1, (String) row[0]); // name
            ps.setString(2, (String) row[1]); // type
            ps.setBoolean(3, (Boolean) row[2]); // exotic
            ps.addBatch();
        }
        // Выполнить batches
        int[] batchResults = ps.executeBatch();
        System.out.println("\n====================\n");
        System.out.println("Товары успешно добавлены");
//        // Проверить, что добавились нужные товары с нужными значениями
        String sql = "SELECT * FROM food order by food_id desc limit 2";
        //PreparedStatement для параметризованного SQL-запроса
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
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
    @Description("Удалить новый товар Пепино из таблицы")
    public void deleteNewProductNotExoticFruit() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //Ввести запрос
        String sql = "DELETE FROM food WHERE food_name=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "Пепино");
        statement.executeUpdate();
        String sqlSel = "SELECT * FROM food";
        PreparedStatement pstamt = con.prepareStatement(sqlSel);
        ResultSet rsDel = pstamt.executeQuery();
        //Проверить,что товар удалился
        rsDel.last();
        Assertions.assertFalse("Пепино".equals(rsDel.getString("food_name"))
                        && ("Vegetable".equals(rsDel.getString("food_type")))
                        && (Boolean.valueOf(true).equals(rsDel.getBoolean("food_exotic"))),
                "Товар не удалился");
    }

    @Test
    @Description("Удалить новый товар Арбуз из таблицы")
    public void deleteNewProductNotExoticegeVegetable() throws SQLException {
        // подключить Базу Данных
        dataBaseConnect();
        //Ввести запрос
        String sql = "DELETE FROM food WHERE food_name=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "Арбуз");
        statement.executeUpdate();
        String sqlSel = "SELECT * FROM food";
        PreparedStatement pstamt = con.prepareStatement(sqlSel);
        ResultSet rsDel = pstamt.executeQuery();
        //Проверить,что товар удалился
        rsDel.last();
        Assertions.assertFalse("Арбуз".equals(rsDel.getString("food_name"))
                        && ("Fruit".equals(rsDel.getString("food_type")))
                        && (Boolean.valueOf(true).equals(rsDel.getBoolean("food_exotic"))),
                "Товар не удалился");
    }

    @Test
    @Description("Добавление товара по типу  Фрукт с активным чекбоксом экзотический " +
            "INSERT INTO food ( food_name, food_type, food_exotic )" +
            "VALUES ( 'Манго', 'Vegetable', 1) " +
            "При успешном добавлении проверить: " +
            "что новый продукт  Манго с типом Фрукт и значением exotic = true добавился в таблицу" +
            "Удалить товар из таблицы" +
            "Проверить,что товар успешно удален")
    public void addProd() throws SQLException {
        dataBaseConnect();
        //Добавить новый товар
        String sql = "insert into food (food_name, food_type, food_exotic) values ( ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, "Манго");
        pstmt.setString(2, "Fruit");
        pstmt.setBoolean(3, true);
        pstmt.executeUpdate();
        //Проверить,что товар добавился
        String sqlSel = "SELECT * FROM food";
        PreparedStatement pstamt = con.prepareStatement(sqlSel);
        ResultSet rs = pstamt.executeQuery();
        rs.last();
        Assertions.assertAll(" В таблице присутствует товар с значениями Манго, Fruit, true",
                () -> assertEquals(rs.getString("food_name"), "Манго"),
                () -> assertEquals(rs.getString("food_type"), "Fruit"),
                () -> assertEquals(rs.getBoolean("food_exotic"), true)
        );
    }

    @Test
    @Description("Удалить новый товар Манго из таблицы")
    public void deleteNewProductNotExotic() throws SQLException {
        dataBaseConnect();
        String sqlDel = "DELETE FROM food WHERE food_name=?";
        PreparedStatement statm = con.prepareStatement(sqlDel);
        statm.setString(1, "Манго");
       statm.executeUpdate();
        String sqlSel = "SELECT * FROM food";
        PreparedStatement pstamt = con.prepareStatement(sqlSel);
        ResultSet rsDel = pstamt.executeQuery();
      //Проверить,что товар удалился
        rsDel.last();
        Assertions.assertFalse("Манго".equals(rsDel.getString("food_name"))
                        && ("Fruit".equals(rsDel.getString("food_type")))
                        && (Boolean.valueOf(true).equals(rsDel.getBoolean("food_exotic"))),
                "Товар не удалился");
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
//        //чтобы вывести данные используем метод next() ,
        rs.next();
        //Вывести количество строк
        System.out.println("\n====================\n");
        System.out.println("Таблица содержит " + rs.getInt("count(*)") + " строк");
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
