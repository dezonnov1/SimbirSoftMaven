package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* Task
С помощью JDBC реализовать создание БД, наполнение ее данными и выборку и
изменение этих данных. В качестве субд выбрать любую удобную для разворачивания на
локальной машине (например PostgreSQL).
host = localhost
port = 5432
maintanance DB = postgres
username = postgres
password = PasJava17
* */
public class JdbcApp {

    private static Connection connection;
    private static Statement statement;
    private final static String USERNAME_DB = "postgres";
    private final static String PASSWORD_DB = "PasJava17";
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        try {
            connect();
            createTable();
            insertStudent(new Student("Иван","ИТСС-O-23/1",2));
            insertStudent(new Student("Сергей","ИC-O-22/1",3));
            insertStudent(new Student("vitya","ИТСС-O-23/1",2));
            insertStudent(new Student("Константин","ПО-О-24/1",1));

            System.out.println("full");
            students = readStudents();
            for (Student student : students){
                student.printStudent();
            }
            System.out.println("----");

            System.out.println("On 2-th course");
            students = readStudents(new Student(0,"","",2));
            for (Student student : students){
                student.printStudent();
            }
            System.out.println("----");

            System.out.println("Edit student");
            students = readStudents();
            updateStudent(students.get(0), new Student("Ванек","ИТСС-O-23/1",2));
            students = readStudents();
            for (Student student : students){
                student.printStudent();
            }
            System.out.println("----");

            System.out.println("del student");
            deleteStudent(students.get(0));
            students = readStudents();
            for (Student student : students){
                student.printStudent();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dropTable();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            disconnect();
        }
    }

    /**
     * Подключение к базе данных
     * @throws SQLException Если не удалось подключиться к БД
     */
    private static void connect() throws SQLException {
        System.out.println("Открывается соединение с БД");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", USERNAME_DB, PASSWORD_DB);
        statement = connection.createStatement();
        System.out.println("Соединение открыто");
    }
    /**
     * Отключение от базы данных
     */
    private static void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Соединение закрыто");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создание таблицы
     * @throws SQLException Неудалось создать таблицу
     */
    private static void createTable() throws SQLException {
        statement.executeUpdate("BEGIN;\n" +
                "CREATE TABLE IF NOT EXISTS student(\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    student_name TEXT NOT NULL,\n" +
                "    student_group TEXT NOT NULL,\n" +
                "    student_course INTEGER NOT NULL\n" +
                ");\n" +
                "COMMIT;");
        System.out.println("Таблица создана");
    }

    /**
     * Удаление таблицы
     * @throws SQLException
     */
    private static void dropTable() throws SQLException {
        statement.executeUpdate("BEGIN;" +
                "DROP TABLE IF EXISTS evaluations;" +
                "DROP TABLE IF EXISTS student; " +
                "COMMIT;");
        System.out.println("Таблица удалена");
    }

    /**
     * Получение записей из таблицы
     * @return List<Student> записи из таблицы
     * @throws SQLException Ошибка при чтении
     */
    private static List<Student> readStudents() throws SQLException {
        List<Student> Students = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery("SELECT * FROM student;")) {
            while (rs.next()) {
                Students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("student_group"),
                        rs.getInt("student_course")));
            }
            return Students;
        }
    }

    /**
     * Получение записей из таблицы индентичные передаваемому классу student
     * @param student экземпляр по которому ищем подобные записи
     * @return List<Student> записи из таблицы
     * @throws SQLException Ошибка при чтении
     */
    private static List<Student> readStudents(Student student) throws SQLException {
        List<Student> students = new ArrayList<>();
        // Создаем динамический SQL-запрос
        String query = "SELECT * FROM student WHERE 1=1";
        // Добавляем параметры в запрос, если они не равны null
        if (student.getId() != 0 && student.getId() != null) {
            query += " AND id = ?";
        }
        if (student.getName() != null && !student.getName().isEmpty()) {
            query += " AND student_name = ?";
        }
        if (student.getGroup() != null && !student.getGroup().isEmpty()) {
            query += " AND student_group = ?";
        }
        if (student.getCourse() != 0 && student.getCourse() != null) {
            query += " AND student_course = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int paramIndex = 1;

            // Устанавливаем значения параметров в запросе
            if (student.getId() != 0 && student.getId() != null) {
                preparedStatement.setInt(paramIndex++, student.getId());
            }
            if (student.getName() != null && !student.getName().isEmpty()) {
                preparedStatement.setString(paramIndex++, student.getName());
            }
            if (student.getGroup() != null && !student.getGroup().isEmpty()) {
                preparedStatement.setString(paramIndex++, student.getGroup());
            }
            if (student.getCourse() != 0 && student.getCourse() != null) {
                preparedStatement.setInt(paramIndex++, student.getCourse());
            }

            // Выполняем запрос и обрабатываем результат
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("student_name"),
                            rs.getString("student_group"),
                            rs.getInt("student_course")
                    ));
                }
            }
        }
        return students;
    }

    /**
     * Обновление данных
     * @param oldStudent ищем те записи, которые будут изменены
     * @param newStudent экземпляр, который будет записан
     */
    private static void updateStudent(Student oldStudent, Student newStudent) {
        StringBuilder query = new StringBuilder("UPDATE student SET ");
        boolean firstSet = true;

        // Добавляем новые значения в SET
        if (newStudent.getName() != null && !newStudent.getName().isEmpty()) {
            query.append("student_name = ?");
            firstSet = false;
        }
        if (newStudent.getGroup() != null && !newStudent.getGroup().isEmpty()) {
            if (!firstSet) query.append(", ");
            query.append("student_group = ?");
            firstSet = false;
        }
        if (newStudent.getCourse() != null) {
            if (!firstSet) query.append(", ");
            query.append("student_course = ?");
        }
        //firstSet = true;
        query.append(" WHERE id = ?");

        // Добавляем условия для WHERE (старые значения)
        if (oldStudent.getName() != null && !oldStudent.getName().isEmpty()) {
            query.append(" AND student_name = ?");
        }
        if (oldStudent.getGroup() != null && !oldStudent.getGroup().isEmpty()) {
            query.append(" AND student_group = ?");
        }
        if (oldStudent.getCourse() != null) {
            query.append(" AND student_course = ?");
        }

        try (PreparedStatement prepUpdate = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // SET
            if (newStudent.getName() != null && !newStudent.getName().isEmpty()) {
                prepUpdate.setString(paramIndex++, newStudent.getName());
            }
            if (newStudent.getGroup() != null && !newStudent.getGroup().isEmpty()) {
                prepUpdate.setString(paramIndex++, newStudent.getGroup());
            }
            if (newStudent.getCourse() != null) {
                prepUpdate.setInt(paramIndex++, newStudent.getCourse());
            }

            // WHERE
            prepUpdate.setInt(paramIndex++, oldStudent.getId());
            if (oldStudent.getName() != null && !oldStudent.getName().isEmpty()) {
                prepUpdate.setString(paramIndex++, oldStudent.getName());
            }
            if (oldStudent.getGroup() != null && !oldStudent.getGroup().isEmpty()) {
                prepUpdate.setString(paramIndex++, oldStudent.getGroup());
            }
            if (oldStudent.getCourse() != null) {
                prepUpdate.setInt(paramIndex, oldStudent.getCourse());
            }

            int rowsAffected = prepUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные обновлены");
            } else {
                System.out.println("Обновление не выполнено. Проверь условия.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Очистка таблицы
     * @throws SQLException Очистка не получилась
     */
    private static void clearTableStudent() throws SQLException {
        statement.executeUpdate("DELETE FROM student;");
    }

    /**
     * Удаление записи
     * @param student подобные записи в БД, будут удалены
     */
    private static void deleteStudent(Student student){
        String query = "DELETE FROM student WHERE ";
        boolean firstSet = true;
        // WHERE
        if(student.getId() != null && student.getId() != 0){
            query += "id = ?";
            firstSet = false;
        }
        if (student.getName() != null && !student.getName().isEmpty()) {
            if(!firstSet){ query +=" AND";}
            query += " student_name = ?";
            firstSet = false;
        }
        if (student.getGroup() != null && !student.getGroup().isEmpty()) {
            if(!firstSet){ query +=" AND";}
            query += " student_group = ?";
            firstSet = false;
        }
        if (student.getCourse() != null && student.getCourse() != 0) {
            if(!firstSet){ query +=" AND";}
            query += " student_course = ?";
        }

        try (PreparedStatement prepUpdate = connection.prepareStatement(query)) {
            int paramIndex = 1;
            // WHERE
            if (student.getId() != null && student.getId() != 0) {
            prepUpdate.setInt(paramIndex++, student.getId());
            }
            if (student.getName() != null && !student.getName().isEmpty()) {
                prepUpdate.setString(paramIndex++, student.getName());
            }
            if (student.getGroup() != null && !student.getGroup().isEmpty()) {
                prepUpdate.setString(paramIndex++, student.getGroup());
            }
            if (student.getCourse() != null) {
                prepUpdate.setInt(paramIndex++, student.getCourse());
            }

            int rowsAffected = prepUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные удалены");
            } else {
                System.out.println("Удаление не выполнено. Проверь условия.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Вставка записи
     * @param student записываемый в БД экземпляр
     */
    private static void insertStudent(Student student){
        String query = "INSERT INTO student(";
        Boolean[] params = {false,false,false,false};
        Integer signs = 1;
        // Добавляем параметры в запрос, если они не равны null
        if (student.getId() != 0 && student.getId() != null) {
            query += "id,";
            params[0]=true;
            signs++;
        }
        if (student.getName() != null && !student.getName().isEmpty()) {
            query += "student_name,";
            params[1]=true;
            signs++;
        }
        if (student.getGroup() != null && !student.getGroup().isEmpty()) {
            query += "student_group,";
            params[2]=true;
            signs++;
        }
        if (student.getCourse() != 0 && student.getCourse() != null) {
            query += "student_course,";
            params[3]=true;
            signs++;
        }
        query = query.substring(0, query.length()-1);
        query += ") VALUES(" + "?,".repeat(signs-1);
        query = query.substring(0, query.length()-1);
        query += ");";
        try (PreparedStatement prepInsert = connection.prepareStatement(query)) {
            if(params[3] == true){prepInsert.setInt(--signs, student.getCourse());}
            if(params[2] == true){prepInsert.setString(--signs, student.getGroup());}
            if(params[1] == true){prepInsert.setString(--signs, student.getName());}
            if(params[0] == true){prepInsert.setInt(--signs, student.getId());}
            prepInsert.executeUpdate();
            System.out.println("Студент " + student.getStudent() + " создан в БД");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
