package org.metacubed.jsonapi.builder

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import java.net.URI
import java.time.Instant
import java.util.UUID

private val OBJECT_MAPPER: ObjectMapper = jacksonObjectMapper()
    .registerModule(JavaTimeModule())
    .apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        setSerializationInclusion(Include.NON_ABSENT)
    }

class DslTest {

    @Test
    fun testSingleResourceDocument() {

        val document = jsonApi {
            singleResourceDocument<Payload> {
                data {
                    id = "id1"
                    attributes = Payload(
                        property1 = "test1",
                        property2 = 42
                    )
                    meta {
                        "createdOn" *= Instant.EPOCH
                        "updatedOn" *= Instant.now()
                    }
                    links {
                        self = URI("https://example.com/payloads/id1")
                    }
                }
                meta {
                    "traceId" *= UUID.randomUUID()
                }
                links {
                    self = URI("https://example.com/payloads/id1")
                }
            }
        }

        println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(document))
    }

    @Test
    fun testMultiResourceDocument() {

        val document = jsonApi {
            multiResourceDocument<Payload> {
                data {
                    id = "id1"
                    attributes = Payload(
                        property1 = "test1",
                        property2 = 42
                    )
                    meta {
                        "createdOn" *= Instant.EPOCH
                        "updatedOn" *= Instant.now()
                    }
                    links {
                        self = URI("https://example.com/payloads/id1")
                    }
                }
                data {
                    id = "id2"
                    attributes = Payload(
                        property1 = "test2",
                        property2 = 42
                    )
                    meta {
                        "createdOn" *= Instant.EPOCH
                        "updatedOn" *= Instant.now()
                    }
                    links {
                        self = URI("https://example.com/payloads/id2")
                    }
                }
                meta {
                    "traceId" *= UUID.randomUUID()
                }
                links {
                    self = URI("https://example.com/payloads?page[limit]=2&page[offset]=4")
                    first = URI("https://example.com/payloads?page[limit]=2&page[offset]=0")
                    prev = URI("https://example.com/payloads?page[limit]=2&page[offset]=2")
                    next = URI("https://example.com/payloads?page[limit]=2&page[offset]=6")
                    last = URI("https://example.com/payloads?page[limit]=2&page[offset]=20")
                }
            }
        }

        println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(document))
    }

    @Test
    fun testErrorDocument() {

        val document = jsonApi {
            errorDocument {
                error {
                    status = 404
                    code = "MISSING_ELEMENT"
                    title = "Missing element"
                    detail = "The requested element was not found"
                    source {
                        parameter = "id"
                    }
                    meta {
                        "transient" *= false
                    }
                    links {
                        about = URI("https://example.com/errors/404")
                    }
                }
                error {
                    status = 400
                    code = "FORMAT_ERROR"
                    title = "Invalid input"
                    detail = "The specified payload contains an invalid property"
                    source {
                        pointer = "/data/attributes/property2"
                    }
                    meta {
                        "transient" *= false
                    }
                    links {
                        about = URI("https://example.com/errors/400")
                    }
                }
                error {
                    status = 429
                    code = "RATE_LIMITING"
                    title = "Rate limit reached"
                    detail = "Try again after some time"
                    meta {
                        "transient" *= true
                    }
                    links {
                        about = URI("https://example.com/errors/429")
                    }
                }
                meta {
                    "traceId" *= UUID.randomUUID()
                }
                links {
                    self = URI("https://example.com/payloads/missing")
                }
            }
        }

        println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(document))
    }

    @Test
    fun testMetaDocument() {

        val document = jsonApi {
            metaDocument {
                meta {
                    "traceId" *= UUID.randomUUID()
                }
                links {
                    self = URI("https://example.com")
                }
            }
        }

        println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(document))
    }
}

private data class Payload(
    val property1: String,
    val property2: Int
)
