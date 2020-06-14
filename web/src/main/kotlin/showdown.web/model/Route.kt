package showdown.web.model

import react.Component
import kotlin.reflect.KClass

data class Route(val path: String, val kClass: KClass<out Component<*, *>>, val exact: Boolean)