package data

class CustomerInfo(val id: Int,
                   private val firstName: String,
                   private val lastName: String,
                   private val age: Int,
                   private val email: String,
                 ) {
    override fun toString(): String {
       return "$id,\n $firstName,\n $lastName,\n $age,\n $email,\n\n "
    }
}