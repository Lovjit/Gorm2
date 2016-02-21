package com.tothenew.gorm2

class User {

    String firstName
    String lastName
    String address
    Integer age

    static hasMany = [accounts: Account]

    static List<User> findUsers(String q, Integer age) {
        List<User> users = User.createCriteria().list() {
            ilike("firstName", "${q}%")
            ilike("address", "%${q}")
            le("age", age)
        }
        return users
    }

}
