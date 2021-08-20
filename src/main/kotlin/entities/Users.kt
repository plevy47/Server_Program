package entities

import io.ebean.Model
import io.ebean.annotation.Length
import javax.persistence.*

@Entity
@Table(name = "users", schema = "public", catalog = "")
class Users : Model(){

    @Basic
    @Column(name = "username")
    @Length(30)
    var username: String = "default"

    @Basic
    @Column(name = "password")
    @Length(100)
    var password: String = "default"

    @Basic
    @Column(name = "email")
    @Length(100)
    var email: String = "default"

//    override fun toString(): String{
//        return "\nId: $id\nTask Name: $taskName\n"
//    }
}