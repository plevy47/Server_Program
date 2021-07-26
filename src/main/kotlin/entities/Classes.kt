package entities

import io.ebean.Model
import io.ebean.annotation.Length
import javax.persistence.*

@Entity
@Table(name = "class", schema = "public", catalog = "")
class Classes : Model(){

    @Id
    @Column(name = "id")
    var id : Int = 0

    @Basic
    @Column(name = "class_name")
    @Length(25)
    var className: String = "default"

    @Basic
    @Column(name = "class_code")
    @Length(9)
    var classCode: String = "default"

    @Basic
    @Column(name = "teacher_name")
    @Length(35)
    var teacherName: String = "default"

    @Basic
    @Column(name = "teacher_email")
    @Length(50)
    var teacherEmail: String = "default"

    @Basic
    @Column(name = "semester")
    @Length(3)
    var semester: String = "default"

    override fun toString(): String{
        return "\nId: $id\nClass Name: $className\nClass Code: $classCode\nTeacher Name: $teacherName\nTeacher Email: $teacherEmail\nSemester: $semester\n\n"
    }
}