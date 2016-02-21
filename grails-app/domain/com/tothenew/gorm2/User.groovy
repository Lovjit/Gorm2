package com.tothenew.gorm2

class User {

    String firstName
    String lastName
    String address
    Integer age

    static hasMany = [accounts: Account]

}
