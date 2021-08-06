package showdown.web.model

import react.Component
import react.RProps
import kotlin.reflect.KClass

data class Route(val path: String, val kClass: KClass<out Component<RProps, *>>, val exact: Boolean)