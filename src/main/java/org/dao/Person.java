package org.dao;

/**
 *
 */
public class Person {

    private String name;
    private int age;
    private int weight;

    /**
     *
     */
    public static class Builder {
        private Person person;

        /**
         *
         */
        public Builder() {
            person = new Person();
        }

        /**
         *
         * @param name
         * @return
         */
        public Builder withName(String name) {
            person.name = name;
            return this;
        }

        /**
         *
         * @param age
         * @return
         */
        public Builder withAge(int age) {
            person.age = age;
            return this;
        }

        /**
         *
         * @param weight
         * @return
         */
        public Builder withWeight(int weight) {
            person.weight = weight;
            return this;
        }

        /**
         *
         * @return
         */
        public Person build() {
            return person;
        }
    }
}
