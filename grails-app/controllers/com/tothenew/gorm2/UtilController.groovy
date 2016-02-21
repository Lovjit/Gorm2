package com.tothenew.gorm2

import com.tothenew.gorm2.vo.AccountInfoVO

class UtilController {

    static defaultAction = "list"

    def list() {
        List<User> users = User.findUsers("Test", 30)
        render "Result -> ${users.size()} ${users.firstName} ${users.age}"
    }

    def listPaginate() {
        List<User> users = User.createCriteria().list(max: 10, offset: 10) {
            ilike("firstName", "Test%")
            le("age", 30)
            between("age", 18, 60)
        }
        render "Result -> ${users.size()} ${users*.id} totalCount ${users.totalCount}"
    }

    def listDistinct() {
        List<User> users = User.createCriteria().listDistinct() {
            ilike("firstName", "Test 1%")
            le("age", 50)
            between("age", 18, 60)
            maxResults 10
            firstResult 0
            order("age", "desc")
        }
        render "Result ->${users.size()} ${users*.id}"
    }

    def get() {
        User user = User.createCriteria().get {
            eq("id", 1L)
        }
        render user
    }

    def count() {
        List list = [10, 25, 30]
        Integer userCount = User.createCriteria().count() {
            ilike("firstName", "Test%")
            le("age", 30)
            if (list) {
                inList("age", list)
            }
        }
        render "Result -> ${userCount}"
    }

    def and() {
        List<Account> accounts = Account.createCriteria().list() {
            and {
                'branch' {
                    eq("name", "London")
                }
                or {
                    between("balance", 5000, 10000)
                    'user' {
                        ilike("firstName", "Test 2%")
                    }

                }
            }
        }
        render "Result -> ${accounts*.balance} ${accounts*.branch*.name} ${accounts*.user.firstName}"
    }

    def or() {
        List<Account> accounts = Account.createCriteria().list() {
            or {
                between("balance", 5000, 10000)
                'branch' {
                    eq("name", "London")
                }
            }
        }
        render "Result -> ${accounts*.balance} ${accounts*.branch*.name}"
    }

    def not() {
        List<Account> accounts = Account.createCriteria().list() {
            not {
                between("balance", 5000, 10000)

                'branch' {
                    eq("name", "London")
                }
            }
        }
        render "Result -> ${accounts*.balance} ${accounts*.branch*.name}"
    }

    def property() {
        def dates = User.createCriteria().list() {
            projections {
                property("age", 'userage')
                'account' {
                    property("balance")
                }
            }
            ilike("firstName", "Test%")
            le("age", 50)
            between("age", 18, 60)
            'account' {
                order('balance', 'desc')
            }
        }
        render "Result -> ${dates}"
    }

    def distinct() {
        List<Integer> userAges = User.createCriteria().list() {
            projections {
                distinct("age")
            }
            ilike("firstName", "Test%")
            le("age", 50)
            between("age", 18, 60)
        }
        render "Result -> ${userAges}"
    }

    def projections() {
        Integer ageSum = User.createCriteria().get() {
            projections {
                sum("age")
            }
            ilike("firstName", "Test%")
            le("age", 50)
            between("age", 18, 60)
        }
        render "Result -> ${ageSum}"
    }

    def projectProperties() {
        User user = User.first()
        AccountInfoVO accountInfoVO = user.accountInfo
        render "${accountInfoVO}"
    }

    def groupProperty() {
        List result = Account.createCriteria().list() {
            projections {
                groupProperty("branch")
                sum("balance")
            }
        }
        render "Result -> ${result}"
    }

    def alias() {
        List result = Account.createCriteria().list() {
            projections {
                groupProperty("branch")
                sum("balance", 'totalBalance')
            }
            order("totalBalance", "desc")
        }
        render "Result -> ${result}"
    }

    def executeQuery() {
        Integer age = 19
        List usersInfo = User.executeQuery("select u.firstName, u.lastName from User as u where u.age >:age", [age: age])
        render "User Info -: ${usersInfo}"
    }

    def executeUpdate() {
        User user = User.get(1)
        String firstName = user.firstName
        User.executeUpdate("update User as u set u.firstName=:firstName where u.id=:id", [firstName: "Uday", id: 1.toLong()])
//        user.refresh()
        render "firstName before ${firstName} -: After updation ${user.firstName}"
//        User.executeUpdate("delete User where id=:id", [id: 1.toLong()])
//        render "Success"
    }


    def namedQuery = {
        Date date = new Date()
//        List<Account> accounts = Account.maxBalance(30000).list()
//        List<Account> accounts = Account.maxBalance(30000).list(max: 10, offset: 0)
        List<Account> accounts = Account.maxBalance(30000).findAllByBalanceLessThan(40000)
        render "Success -> ${accounts.balance}"
    }
}
