package org.metacubed.jsonapi.model

import java.net.URI

private const val JSON_API_VERSION = "1.0"

interface SingleResourceDocument<T : Any> : Document {
    val data: Resource<T>?
}

interface MultiResourceDocument<T : Any> : Document {
    val data: List<Resource<T>>
}

interface ErrorDocument : Document {
    val errors: List<Error>
}

interface Document {
    val meta: Meta?
    val links: DocumentLinks?
    val jsonapi get() = mapOf("version" to JSON_API_VERSION)
}

interface DocumentLinks {
    val self: URI
    val related: URI?
    val first: URI?
    val prev: URI?
    val next: URI?
    val last: URI?
}

interface Resource<T : Any> {
    val type: String
    val id: String
    val attributes: T?
    val meta: Meta?
    val links: ResourceLinks?
}

interface ResourceLinks {
    val self: URI
}

interface Error {
    val id: String?
    val status: Int?
    val code: String?
    val title: String?
    val detail: String?
    val source: ErrorSource?
    val meta: Meta?
    val links: ErrorLinks?
}

interface ErrorSource {
    val pointer: String?
    val parameter: String?
}

interface ErrorLinks {
    val about: URI
}

typealias Meta = Map<String, Any>
