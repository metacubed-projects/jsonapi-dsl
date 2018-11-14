package org.metacubed.jsonapi.model

import java.net.URI

interface SingleResourceDocument<T : Any> : Document {
    val data: Resource<T>?
}

interface MultiResourceDocument<T : Any> : Document {
    val data: List<Resource<T>>
}

interface ErrorDocument : Document {
    val errors: List<Error>?
}

interface Document {
    val links: Links?
}

interface Resource<T : Any> {
    val type: String
    val id: String
    val attributes: T?
    val meta: Map<String, Any>?
    val links: Links?
}

interface Error {
    val title: String?
}

interface Links {
    val self: URI
    val related: URI?
    val first: URI?
    val prev: URI?
    val next: URI?
    val last: URI?
}
