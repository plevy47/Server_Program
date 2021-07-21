package entities

import io.ebean.Model
import io.ebean.annotation.Length
import javax.persistence.*

@Entity
@Table(name = "customer_info",schema = "public",catalog = "")
class CustomerInfo() : Model() {

    @Id
    @Column(name = "id")
    var id : Int = 0

    @Basic
    @Column(name = "first_name")
    @Length(30)
    var firstName: String = "default"

    @Basic
    @Column(name = "last_name")
    @Length(30)
    var lastName: String = "default"

    @Basic
    @Column(name = "age")
    @Length(2)
    var age: Int? = 0

    @Basic
    @Column(name = "email")
    @Length(50)
    var email: String? = "default"

    override fun toString(): String{
        return "\nId: $id\nFirst Name: $firstName\nLast Name: $lastName\nAge: $age\nEmail: $email\n\n"
    }
}
