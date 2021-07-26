package entities

import io.ebean.Model
import io.ebean.annotation.Length
import javax.persistence.*


@Entity
@Table(name = "assignment",schema = "public",catalog = "")
class Assignments : Model() {

    @Id
    @Column(name = "assignment_id")
    var id : Int = 0

    @Basic
    @Column(name = "assignment_description")
    @Length(40)
    var assignmentDescription: String = "default"

    @Basic
    @Column(name = "assignment_type")
    @Length(20)
    var assignmentType: String = "default"

    @Basic
    @Column(name = "due_date")
    @Length(10)
    var dueDate: String = "default"

    @Basic
    @Column(name = "grade_value")
    @Length(3)
    var gradeValue: String = "default"

    @Basic
    @Column(name = "grade_result")
    @Length(3)
    var gradeResult: String = "default"

    override fun toString(): String{
        return "\nId: $id\n Description: $assignmentDescription\nType: $assignmentType\nDue Date: $dueDate\nGrade Value: $gradeValue\nGrade Result: $gradeResult\n\n"
    }
}