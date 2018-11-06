package org.metacubed.jsonapi.builder

import org.metacubed.jsonapi.model.Document
import org.metacubed.jsonapi.model.Error
import org.metacubed.jsonapi.model.ErrorDocument
import org.metacubed.jsonapi.model.Links
import org.metacubed.jsonapi.model.MultiResourceDocument
import org.metacubed.jsonapi.model.Resource
import org.metacubed.jsonapi.model.SingleResourceDocument
import java.net.URI

@DslMarker
annotation class JsonApiDsl

@JsonApiDsl
val jsonApi = DocumentBuilder()

@JsonApiDsl
class DocumentBuilder internal constructor() {

    fun <T : Any> single(populator: SingleResourceDocumentPopulator<T>): SingleResourceDocument<T> =
        SingleResourceDocumentBuilder<T>().apply(populator)

    fun <T : Any> multi(populator: MultiResourceDocumentPopulator<T>): MultiResourceDocument<T> =
        MultiResourceDocumentBuilder<T>().apply(populator)

    fun error(populator: ErrorDocumentPopulator): ErrorDocument =
        ErrorDocumentBuilder().apply(populator)

    inline operator fun <TDocument : Document<*>> invoke(populator: DocumentBuilder.() -> TDocument) = populator()
}

@JsonApiDsl
class SingleResourceDocumentBuilder<T : Any> internal constructor() :
    SingleResourceDocument<T> {

    override val data = ResourceBuilder<T>()
    override val errors: ErrorListBuilder? = null
    override val links = LinksBuilder()
}

typealias SingleResourceDocumentPopulator<T> = SingleResourceDocumentBuilder<T>.() -> Unit

@JsonApiDsl
class MultiResourceDocumentBuilder<T : Any> internal constructor() :
        MultiResourceDocument<T> {

    override val data = ResourceListBuilder<T>()
    override val errors: ErrorListBuilder? = null
    override val links = LinksBuilder()
}

typealias MultiResourceDocumentPopulator<T> = MultiResourceDocumentBuilder<T>.() -> Unit

@JsonApiDsl
class ErrorDocumentBuilder internal constructor() : ErrorDocument {

    override val data: Unit? = null
    override val errors = ErrorListBuilder()
    override val links = LinksBuilder()
}

typealias ErrorDocumentPopulator = ErrorDocumentBuilder.() -> Unit

@JsonApiDsl
class ResourceListBuilder<T : Any> internal constructor(
    private val data: MutableList<ResourceBuilder<T>> = mutableListOf()
) : MutableList<ResourceBuilder<T>> by data {

    operator fun plusAssign(populator: ResourcePopulator<T>) {
        add(ResourceBuilder<T>().apply(populator))
    }
}

@JsonApiDsl
class ResourceBuilder<T : Any> internal constructor() : Resource<T> {

    override lateinit var id: String
    override lateinit var type: String
    override lateinit var attributes: T
    override var links: LinksBuilder? = null
        private set
    override var meta: Map<String, Any>? = null

    fun links(populator: LinksPopulator) {
        links = (links ?: LinksBuilder()).apply(populator)
    }

    operator fun invoke(populator: ResourcePopulator<T>) = populator()
}

typealias ResourcePopulator<T> = ResourceBuilder<T>.() -> Unit

@JsonApiDsl
class ErrorListBuilder internal constructor(
    private val errors: MutableList<ErrorBuilder> = mutableListOf()
) : MutableList<ErrorBuilder> by errors {

    operator fun plusAssign(populator: ErrorPopulator) {
        add(ErrorBuilder().apply(populator))
    }
}

@JsonApiDsl
class ErrorBuilder internal constructor() : Error {

    override lateinit var title: String

    operator fun invoke(populator: ErrorPopulator) = populator()
}

typealias ErrorPopulator = ErrorBuilder.() -> Unit

@JsonApiDsl
class LinksBuilder internal constructor() : Links {

    override lateinit var self: URI
    override var related: URI? = null
    override var first: URI? = null
    override var prev: URI? = null
    override var next: URI? = null
    override var last: URI? = null

    operator fun invoke(populator: LinksPopulator) = populator()
}

typealias LinksPopulator = LinksBuilder.() -> Unit
