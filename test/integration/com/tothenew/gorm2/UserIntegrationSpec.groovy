package com.tothenew.gorm2

import grails.test.spock.IntegrationSpec

class UserIntegrationSpec extends IntegrationSpec {

    void "test list of users"() {
        setup:
            String q = "test"
            Integer age = 20
            List<User> users = User.findUsers(q, age)
            Integer count = users.size()

        when:
            User user = new User(firstName: "test", age: 10, address: "test", lastName: "test")
            user.save()
            Integer newCount = User.findUsers(q, age).size()
        then:
            newCount - count == 1
    }
}
