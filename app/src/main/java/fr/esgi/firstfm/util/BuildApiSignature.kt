package fr.esgi.firstfm.util

import android.util.Log
import fr.esgi.firstfm.data.API_KEY
import fr.esgi.firstfm.data.API_SECRET
import java.io.UnsupportedEncodingException
import java.security.MessageDigest

fun md5(s: String): String? {
    try {
        val bytes: ByteArray =
            MessageDigest.getInstance("MD5").digest(s.toByteArray(charset("UTF-8")))
        val b = StringBuilder(32)
        for (aByte in bytes) {
            val hex = Integer.toHexString(aByte.toInt() and 0xFF)
            if (hex.length == 1) b.append('0')
            b.append(hex)
        }
        return b.toString()
    } catch (e: UnsupportedEncodingException) {
        // utf-8 always available
    }
    return null
}

fun BuildApiSignature(vararg params: String): String? {

    // Api key is always part of the params
    val paramsMap = mutableMapOf("api_key" to API_KEY)

    // transform vararg to map
    for (i in params.indices step 2) {
        paramsMap[params[i]] = params[i + 1]
    }

    // sort map by keys
    // this is because the signature has to be made with params in alphabetical order
    // for the last.fm API
    val sortedParams = paramsMap.toSortedMap()

    // init our to-be-signed string
    var toSign = ""

    // add params
    for (key in sortedParams.keys) {
        toSign += key
        toSign += sortedParams[key]
    }

    // end with secret
    toSign += API_SECRET

    Log.v("wesh", toSign)

    // return signature :)
    return md5(toSign)

}