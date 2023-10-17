package com.sr.compose.util

import com.google.gson.*
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalDateConverter : JsonSerializer<LocalDate?>,
    JsonDeserializer<LocalDate> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        return JsonPrimitive(
            src?.format(DateTimeFormatter.ISO_DATE)
        )
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): LocalDate {
        return LocalDate.parse(json.asString)
    }
}