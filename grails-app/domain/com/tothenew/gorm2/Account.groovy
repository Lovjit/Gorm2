package com.tothenew.gorm2

class Account {

    Integer balance = 0
    Date dateCreated

    static belongsTo = [branch: Branch, user: User]

    static constraints = {
        dateCreated bindable: true
    }

    static mapping = {
        autoTimestamp false
    }

    static namedQueries = {
        maxBalance { Integer balance ->
            gt 'balance', balance
        }
    }
}
