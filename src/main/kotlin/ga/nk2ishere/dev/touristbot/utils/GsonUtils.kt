package ga.nk2ishere.dev.touristbot.utils

import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun JsonElement.getOrNull(key: String): JsonElement? = obj.getNull(key)
fun JsonObject.getNull(key: String): JsonElement? = get(key) ?: null
