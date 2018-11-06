package org.metacubed.jsonapi.builder

import org.junit.jupiter.api.Test
import java.net.URI

class DslTest {

    @Test
    fun testSingleResourceDocument() {

        jsonApi {
            single<Map<String, Int>> {
                data {
                    id = "id1"
                    type = "type1"
                    attributes = mapOf(
                        "the_answer" to 42
                    )
                    links {
                        self = URI("")
                    }
                }
                links {
                    self = URI("")
                }
            }
        }
    }

    @Test
    fun testMultiResourceDocument() {

        jsonApi {
            multi<Int> {
                data += {
                    id = "id1"
                    type = "type1"
                    attributes = 42
                }
                data += {
                    id = "id2"
                    type = "type1"
                    attributes = 42
                }
                links {
                    self = URI("")
                }
            }
        }
    }

    @Test
    fun testErrorDocument() {

        jsonApi {
            error {
                errors += {
                    title = "o"
                }
                links {
                    self = URI("")
                }
            }
        }
    }
}
