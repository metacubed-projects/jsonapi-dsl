package org.metacubed.jsonapi.builder

import org.metacubed.jsonapi.model.Document
import org.metacubed.jsonapi.model.DocumentLinks
import org.metacubed.jsonapi.model.Error
import org.metacubed.jsonapi.model.ErrorDocument
import org.metacubed.jsonapi.model.ErrorLinks
import org.metacubed.jsonapi.model.ErrorSource
import org.metacubed.jsonapi.model.Meta
import org.metacubed.jsonapi.model.MultiResourceDocument
import org.metacubed.jsonapi.model.Resource
import org.metacubed.jsonapi.model.ResourceLinks
import org.metacubed.jsonapi.model.SingleResourceDocument
import java.net.URI
import kotlin.reflect.KClass

@JsonApiDsl
val jsonApi = DocumentRoot()

@JsonApiDsl
class DocumentRoot internal constructor() {

    inline operator fun <TDocument : Document> invoke(populator: DocumentRoot.() -> TDocument) = populator()

    inline fun <reified T : Any> singleResourceDocument(noinline populator: SingleResourceDocumentPopulator<T>):
        SingleResourceDocument<T> = singleResourceDocument(T::class, populator)

    inline fun <reified T : Any> multiResourceDocument(noinline populator: MultiResourceDocumentPopulator<T>):
        MultiResourceDocument<T> = multiResourceDocument(T::class, populator)

    fun <T : Any> singleResourceDocument(type: KClass<T>, populator: SingleResourceDocumentPopulator<T>):
        SingleResourceDocument<T> = MutableSingleResourceDocument(type).apply(populator)

    fun <T : Any> multiResourceDocument(type: KClass<T>, populator: MultiResourceDocumentPopulator<T>):
        MultiResourceDocument<T> = MutableMultiResourceDocument(type).apply(populator)

    fun errorDocument(populator: ErrorDocumentPopulator):
        ErrorDocument = MutableErrorDocument().apply(populator)

    fun metaDocument(populator: DocumentPopulator):
        Document = MutableDocument().apply(populator)
}

@JsonApiDsl
class MutableSingleResourceDocument<T : Any> internal constructor(
    private val type: KClass<T>
) : MutableDocument(), SingleResourceDocument<T> {

    override var data: MutableResource<T>? = null
        private set

    fun data(populator: ResourcePopulator<T>) {
        data = MutableResource(type).apply(populator)
    }
}

private typealias SingleResourceDocumentPopulator<T> = MutableSingleResourceDocument<T>.() -> Unit

@JsonApiDsl
class MutableMultiResourceDocument<T : Any> internal constructor(
    private val type: KClass<T>
) : MutableDocument(), MultiResourceDocument<T> {

    override val data = mutableListOf<MutableResource<T>>()

    fun data(populator: ResourcePopulator<T>) {
        data.add(MutableResource(type).apply(populator))
    }
}

private typealias MultiResourceDocumentPopulator<T> = MutableMultiResourceDocument<T>.() -> Unit

@JsonApiDsl
class MutableErrorDocument internal constructor() : MutableDocument(), ErrorDocument {

    override val errors = mutableListOf<MutableError>()

    fun error(populator: ErrorPopulator) {
        errors.add(MutableError().apply(populator))
    }
}

private typealias ErrorDocumentPopulator = MutableErrorDocument.() -> Unit

@JsonApiDsl
open class MutableDocument internal constructor() : Document {

    final override var meta: MutableMeta? = null
        private set
    final override var links: MutableDocumentLinks? = null
        private set

    fun meta(populator: MetaPopulator) {
        meta = (meta ?: MutableMeta()).apply(populator)
    }

    fun links(populator: DocumentLinksPopulator) {
        links = (links ?: MutableDocumentLinks()).apply(populator)
    }
}

private typealias DocumentPopulator = MutableDocument.() -> Unit

@JsonApiDsl
class MutableDocumentLinks internal constructor() : DocumentLinks {

    override lateinit var self: URI
    override var related: URI? = null
    override var first: URI? = null
    override var prev: URI? = null
    override var next: URI? = null
    override var last: URI? = null
}

private typealias DocumentLinksPopulator = MutableDocumentLinks.() -> Unit

@JsonApiDsl
class MutableResource<T : Any> internal constructor(type: KClass<T>) : Resource<T> {

    override val type: String = type.java.simpleName.decapitalize()
    override lateinit var id: String
    override var attributes: T? = null
    override var meta: MutableMeta? = null
        private set
    override var links: MutableResourceLinks? = null
        private set

    fun meta(populator: MetaPopulator) {
        meta = (meta ?: MutableMeta()).apply(populator)
    }

    fun links(populator: ResourceLinksPopulator) {
        links = (links ?: MutableResourceLinks()).apply(populator)
    }
}

private typealias ResourcePopulator<T> = MutableResource<T>.() -> Unit

@JsonApiDsl
class MutableResourceLinks internal constructor() : ResourceLinks {

    override lateinit var self: URI
}

private typealias ResourceLinksPopulator = MutableResourceLinks.() -> Unit

@JsonApiDsl
class MutableError internal constructor() : Error {

    override var id: String? = null
    override var status: Int? = null
    override var code: String? = null
    override var title: String? = null
    override var detail: String? = null
    override var source: MutableErrorSource? = null
        private set
    override var meta: MutableMeta? = null
        private set
    override var links: MutableErrorLinks? = null
        private set

    fun source(populator: ErrorSourcePopulator) {
        source = (source ?: MutableErrorSource()).apply(populator)
    }

    fun meta(populator: MetaPopulator) {
        meta = (meta ?: MutableMeta()).apply(populator)
    }

    fun links(populator: ErrorLinksPopulator) {
        links = (links ?: MutableErrorLinks()).apply(populator)
    }
}

private typealias ErrorPopulator = MutableError.() -> Unit

@JsonApiDsl
class MutableErrorSource internal constructor() : ErrorSource {

    override var pointer: String? = null
    override var parameter: String? = null
}

private typealias ErrorSourcePopulator = MutableErrorSource.() -> Unit

@JsonApiDsl
class MutableErrorLinks internal constructor() : ErrorLinks {

    override lateinit var about: URI
}

private typealias ErrorLinksPopulator = MutableErrorLinks.() -> Unit

@JsonApiDsl
class MutableMeta internal constructor() : MutableMap<String, Any> by mutableMapOf(), Meta {

    operator fun String.timesAssign(value: Any?) {
        when (value) {
            null -> remove(this)
            else -> put(this, value)
        }
    }
}

private typealias MetaPopulator = MutableMeta.() -> Unit

@DslMarker
private annotation class JsonApiDsl
