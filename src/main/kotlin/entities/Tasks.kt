package entities

import io.ebean.Model
import io.ebean.annotation.Length
import javax.persistence.*

@Entity
@Table(name = "tasks", schema = "public", catalog = "")
class Tasks : Model(){

    @Id
    @Column(name = "id")
    var id : Int = 0

    @Basic
    @Column(name = "task_name")
    @Length(100)
    var taskName: String = "default"

    override fun toString(): String{
        return "\nId: $id\nTask Name: $taskName\n"
    }
}