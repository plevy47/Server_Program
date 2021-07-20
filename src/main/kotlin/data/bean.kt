package data

import io.ebean.Model
import io.ebean.annotation.Length
import io.ebean.annotation.WhenCreated
import io.ebean.annotation.WhenModified
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "customer_info")
class CustomerInformation (firstName : String, lastName: String, age : Int, email : String) : BaseModel() {

    @Length(100)
    var first_name: String = firstName

    @Length(100)
    var last_name: String = lastName

    @Length(100)
    var age: Int = age

    @Length(100)
    var email: String = email

}

@MappedSuperclass
abstract class BaseModel : Model() {

    @Id
    var id: Long = 0

    @Version
    var version: Long = 0

    @WhenCreated
    lateinit var whenCreated: Instant

    @WhenModified
    lateinit var whenModified: Instant
}