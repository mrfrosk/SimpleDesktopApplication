package service2

import java.math.BigInteger
import java.security.MessageDigest

object Hash {
    fun encrypt(text: String, algorithmName: String): String {
        val md = MessageDigest.getInstance(algorithmName)
        val messageDigest = md.digest(text.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        return hashtext
    }
}