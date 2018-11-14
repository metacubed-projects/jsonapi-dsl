package org.metacubed.jsonapi.builder

import org.junit.jupiter.api.Test
import java.net.URI
import java.time.Instant

class DslTest {

    @Test
    fun testSingleResourceDocument() {

        jsonApi {
            singleResourceDocument<Payload> {
                data {
                    id = "id1"
                    attributes = Payload(
                        property1 = "test1",
                        property2 = 42
                    )
                    meta {
                        "createdOn" *= Instant.now()
                        "updatedOn" *= Instant.now()
                    }
                    links {
                        self = URI("https://example.com/payloads/id1")
                    }
                }
                links {
                    self = URI("https://example.com/payloads/id1")
                }
            }
        }
    }

    @Test
    fun testMultiResourceDocument() {

        jsonApi {
            multiResourceDocument<Payload> {
                data {
                    id = "id1"
                    attributes = Payload(
                        property1 = "test1",
                        property2 = 42
                    )
                    meta {
                        "createdOn" *= Instant.now()
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
                        "createdOn" *= Instant.now()
                        "updatedOn" *= Instant.now()
                    }
                    links {
                        self = URI("https://example.com/payloads/id2")
                    }
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
    }

    @Test
    fun testErrorDocument() {

        jsonApi {
            errorDocument {
                error {
                    title = "Missing element"
                }
                error {
                    title = "Invalid input"
                }
                error {
                    title = "Rate limit reached"
                }
                links {
                    self = URI("https://example.com/payloads/missing")
                }
            }
        }
    }
}

private data class Payload(
    val property1: String,
    val property2: Int
)
