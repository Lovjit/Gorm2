package com.tothenew.gorm2

class Branch {

    String name
    String address

    static hasMany = [accounts: Account]

}
