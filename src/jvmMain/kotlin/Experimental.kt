fun main(){
    val x = TransferAuthForm(RegDTO("", "", ""))
    x.value.hello()
}

interface IBasicDTO

open class AuthDTO(open val username: String, open val password: String): IBasicDTO
class RegDTO(val email: String, override val username: String, override val password: String): AuthDTO(username, password){
    fun hello(){

    }
}
class TransferAuthForm<T>(t: T){
    var value = t
}