package org.metacubed.jsonapi.model

import java.net.URI

interface SingleResourceDocument<T : Any> : Document<Resource<T>>

interface MultiResourceDocument<T : Any> : Document<List<Resource<T>>>

interface ErrorDocument : Document<Unit?>

interface Document<TData> {
    val data: TData
    val errors: List<Error>?
    val links: Links
}

interface ResourceReference {
    val id: String
    val type: String
}

interface Resource<T : Any> : ResourceReference {
    val attributes: T
    val links: Links?
    val meta: Map<String, Any>?
}

interface Error {
    val title: String
}

interface Links {
    val self: URI
    val related: URI?
    val first: URI?
    val prev: URI?
    val next: URI?
    val last: URI?
}
