package entities

import io.ebean.annotation.Length
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table
class Orders() : BaseModel() {

    @Length(30)
    var productId: Int = 0

    @Length(30)
    var productName: String = "default"

    @Length(2)
    var orderDate: Date? = null //01/01/1999

    @Length(50)
    var orderPrice: Long = 0

    override fun toString(): String{
        return "Id: $productId\nFirst Product: $productName\nDate of Order: $orderDate\nOrder Price: $orderPrice\n\n"
    }

}