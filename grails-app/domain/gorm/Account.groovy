package gorm

class Account {

    Integer balance = 0
    Date dateCreated

    static belongsTo = [branch: Branch, user: User]
    static hasMany = [transactions: Transaction]

    static constraints = {

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
