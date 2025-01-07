package org.example;

public class Student {
    private Integer id = 0;
    private String name = "";
    private String group = "";
    private Integer course = 0;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    /**
     * Полный конструктор класса
     * @param id id
     * @param name имя
     * @param group группа
     * @param course номер курса
     */
    public Student(Integer id,String name,String group,Integer course){
        this.id = id;
        this.name = name;
        this.group = group;
        this.course = course;
    }

    /**
     * Конструктор класса
     * @param name имя
     * @param group группа
     * @param course номер курса
     */
    public Student(String name,String group,Integer course){
        this.name = name;
        this.group = group;
        this.course = course;
    }

    /**
     * Вывод информации о классе
     */
    public void printStudent(){
        System.out.println(this.getStudent());
    }

    /**
     * Получение строки с информацией о классе
     * @return строка с информацией о классе
     */
    public String getStudent(){
        return (String.format("ID: %d, Name: %s, Group: %s, Course: %d",
                this.id, this.name, this.group, this.course));
    }
}
